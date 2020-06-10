package com.apptutti.tuttistore.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.apptutti.tuttistore.R;
import com.apptutti.tuttistore.adapter.ProjectRecyclerAdapter;
import com.apptutti.tuttistore.base.BaseLazyFragment;
import com.apptutti.tuttistore.bean.ProjectResult;
import com.apptutti.tuttistore.constants.Constants;
import com.apptutti.tuttistore.mactivity.WebViewActivity;
import com.apptutti.tuttistore.mvp.contract.ProjectPageContract;
import com.apptutti.tuttistore.mvp.presenter.ProjectPagePresenter;
import com.apptutti.tuttistore.widge.LinearItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

public class ProjectPageFragment extends BaseLazyFragment<ProjectPagePresenter> implements ProjectPageContract.View {

    private RecyclerView recyclerView;
    private int id;
    private int page = 0;
    private ProjectRecyclerAdapter recyclerAdapter;
    private List<ProjectResult.DatasBean> mDataList = new ArrayList<>();
    private RefreshLayout refreshLayout;

    public static ProjectPageFragment newInstance(int id) {
        ProjectPageFragment homePageFragment = new ProjectPageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        Log.d("ProjectPageFragment", "id:" + id);
        homePageFragment.setArguments(bundle);
        return homePageFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getInt("id");
        }
    }

    @Override
    protected void loadData() {
        Log.d("ProjectPageFragment", "id===:" + id);
        Log.d("ProjectPageFragment", "page===:" + page);
        presenter.getProjects(id, page);
    }

    @Override
    protected ProjectPagePresenter createPresenter() {
        return new ProjectPagePresenter();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_project_page;
    }

    @Override
    protected void initView(View rootView) {
        refreshLayout = rootView.findViewById(R.id.srl_project);
        recyclerView = rootView.findViewById(R.id.rv_home_page);
    }

    @Override
    protected void initData() {
        // 设置 ItemDecoration 作为分割线
        LinearItemDecoration itemDecoration = new LinearItemDecoration(mContext)
                .height(0.8f)    // 0.5dp
                .color(Color.parseColor("#aacccccc"))  // color 的 int 值，不是 R.color 中的值
                .margin(12, 12);  // 12dp
        recyclerView.addItemDecoration(itemDecoration);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext,
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                presenter.getProjects(id, page);
            }
        });

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        refreshLayout.finishLoadMore();
    }

    @Override
    public void onProjectList(ProjectResult projectResult) {
        Log.d("ProjectPageFragment", "projectResult:" + projectResult);
        page++;
        if (projectResult != null) {
            List<ProjectResult.DatasBean> datas = projectResult.getDatas();
            if (datas != null) {
                mDataList.addAll(datas);
                if (recyclerAdapter == null) {
                    recyclerAdapter = new ProjectRecyclerAdapter(R.layout.item_recycler_project, mDataList);
                    recyclerView.setAdapter(recyclerAdapter);
                    recyclerAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            gotoWebViewActivity(mDataList.get(position));
                        }
                    });
                } else {
                    recyclerAdapter.setNewData(mDataList);
                }
            } else {
                refreshLayout.setNoMoreData(true);
            }
        }
    }




    private void gotoWebViewActivity(ProjectResult.DatasBean datasBean) {
        Intent intent=new Intent(getActivity(), WebViewActivity.class);


        intent.putExtra(Constants.URL, datasBean.getLink());
        intent.putExtra(Constants.ID, datasBean.getId());
        intent.putExtra(Constants.AUTHOR, datasBean.getAuthor());
        intent.putExtra(Constants.TITLE, datasBean.getTitle());
        getActivity().overridePendingTransition(R.anim.anim_web_enter, R.anim.anim_alpha);

        getActivity().startActivity(intent);
    }


}
