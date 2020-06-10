package com.apptutti.tuttistore.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.apptutti.tuttistore.R;
import com.apptutti.tuttistore.adapter.SystemLeftAdapter;
import com.apptutti.tuttistore.adapter.SystemRightAdapter;
import com.apptutti.tuttistore.base.BaseFragment;
import com.apptutti.tuttistore.base.BaseMVPFragment;
import com.apptutti.tuttistore.bean.SystemResult;
import com.apptutti.tuttistore.mactivity.SystemArticleActivity;
import com.apptutti.tuttistore.mvp.contract.SystemContract;
import com.apptutti.tuttistore.mvp.presenter.SystemPresenter;
import com.apptutti.tuttistore.widge.LinearItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SystemFragment extends BaseMVPFragment<SystemPresenter> implements SystemContract.View{

    private RecyclerView leftRecyclerView;
    private RecyclerView rightRecyclerView;
    private SystemLeftAdapter systemLeftAdapter;
    private SystemRightAdapter systemRightAdapter;
    private int leftCurPosition = 0;

    public static BaseFragment newInstance(/*String uid*/){
         SystemFragment fragment = new SystemFragment();
      /* Bundle args = new Bundle();
                args.putString("uid", uid);
        fragment.setArguments(args);*/
            return fragment;
}

    @Override
    protected int getLayoutResId(){
        return R.layout.fragment_system;
    }

    @Override
    protected void initView(View rootView){
        leftRecyclerView = rootView.findViewById(R.id.rv_system_left);
        rightRecyclerView = rootView.findViewById(R.id.rv_system_right);
    }

    @Override
    protected void initData(){
        leftRecyclerView.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
        rightRecyclerView.setLayoutManager(new GridLayoutManager(mContext,2));
        //设置ItemDecoration作为分割线
        LinearItemDecoration itemDecoration=new LinearItemDecoration(mContext)
                .height(0.5f)
                .color(0xaacccccc);
        leftRecyclerView.addItemDecoration(itemDecoration);

        presenter.getSystemList();


    }




    @Override
    public void onSystemList(final List<SystemResult> systemResults){
        if(systemResults==null){
            return;
        }
        if(systemLeftAdapter==null){
            //左侧RecycleView
            systemLeftAdapter=new SystemLeftAdapter(R.layout.item_system_left,systemResults);
            systemResults.get(0).setSelected(true);
            systemLeftAdapter.notifyDataSetChanged();
            systemLeftAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener(){
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position){
                    if(leftCurPosition==position){
                        return;
                    }
                    leftCurPosition = position;

                    for(int i=0 ;i<systemResults.size();i++){
                        SystemResult systemResult=systemResults.get(i);
                        systemResult.setSelected(i==leftCurPosition);
                    }
                    systemLeftAdapter.notifyDataSetChanged();

                    SystemResult systemResult=systemResults.get(position);
                    if(systemResult!=null){
                        List<SystemResult.ChildrenBean>children=systemResult.getChildren();
                        systemRightAdapter.setNewData(children);
                    }

                }

            });
            leftRecyclerView.setAdapter(systemLeftAdapter);
        }


        //右侧RecycleView
        if(systemRightAdapter==null){
            systemRightAdapter=new SystemRightAdapter(R.layout.item_system_right,systemResults.get(0).getChildren());
            systemRightAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener(){
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position){
                    gotoSystemArticleActivity(systemRightAdapter.getData().get(position));
                }
            });
            rightRecyclerView.setAdapter(systemRightAdapter);
        }
    }

    @Override
    public void showLoading(){
    }

    @Override
    public void hideLoading(){
    }

    @Override
    protected SystemPresenter createPresenter(){
        return new SystemPresenter();
    }


    /**
     * 跳转到体系文章列表
     *
     * @param childrenBean
     */
    private void gotoSystemArticleActivity(SystemResult.ChildrenBean childrenBean) {
        Intent intent=new Intent(getActivity(), SystemArticleActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("SystemResult", childrenBean);
        intent.putExtra("bundle",bundle);

        getActivity().startActivity(intent);

    }
}
