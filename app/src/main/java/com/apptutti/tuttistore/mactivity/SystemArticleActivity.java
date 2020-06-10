package com.apptutti.tuttistore.mactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.apptutti.tuttistore.R;
import com.apptutti.tuttistore.adapter.SystemArticleAdapter;
import com.apptutti.tuttistore.base.BaseMVPActivity;
import com.apptutti.tuttistore.bean.SystemArticleResult;
import com.apptutti.tuttistore.bean.SystemResult;
import com.apptutti.tuttistore.constants.Constants;
import com.apptutti.tuttistore.mvp.contract.SystemArticleContract;
import com.apptutti.tuttistore.mvp.presenter.SystemArticlePresenter;
import com.apptutti.tuttistore.widge.LinearItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

public class SystemArticleActivity extends BaseMVPActivity<SystemArticlePresenter>implements SystemArticleContract.View{

    private RecyclerView recyclerView;
    private List<SystemArticleResult.DatasBean>datalist=new ArrayList<>();
    private SystemArticleAdapter adapter;
    int page=0;
    int id=0;
    private RefreshLayout refreshLayout;


    @Override
    protected int getLayoutResId(){
        return R.layout.activity_system_article;
    }

    @Override
    protected void initData(){
        super.initData();
        Bundle bundle=getIntent().getBundleExtra("bundle");
        if(bundle!=null){
            SystemResult.ChildrenBean childrenBean= (SystemResult.ChildrenBean) bundle.getSerializable("SystemResult");
            if(childrenBean!=null){
                id=childrenBean.getId();
                String name=childrenBean.getName();
                getSupportActionBar().setTitle(name);
            }
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
        //设置分割线
        LinearItemDecoration itemDecoration=new LinearItemDecoration(mContext)
                .height(1f)
                .color(0xaa999999);
        recyclerView.addItemDecoration(itemDecoration);

        presenter.getSystemArticleList(page,id);
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener(){
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout){
                presenter.getSystemArticleList(page,id);
            }
        });



    }

    @Override
    protected void initView(){
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }
        });
        refreshLayout=findViewById(R.id.srf_system_article);
        recyclerView=findViewById(R.id.rv_system_article);

    }

    @Override
    protected SystemArticlePresenter createPresenter(){
        return new SystemArticlePresenter();
    }

    @Override
    public void onSystemArticleList(SystemArticleResult result){
        page++;
        if(result!=null){
            datalist.addAll(result.getDatas());
            if(adapter==null){
                adapter=new SystemArticleAdapter(R.layout.item_home_article,datalist);
                adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener(){
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position){
                        gotoWebViewActivity(datalist.get(position));
                    }
                });
                recyclerView.setAdapter(adapter);
            }else{
                adapter.setNewData(datalist);
            }
        }
    }

    @Override
    public void showLoading(){
    }

    @Override
    public void hideLoading(){
        refreshLayout.finishLoadMore();
    }



    /**
     * 跳转到 webviewactivity
     *
     * @param datasBean
     */
    private void gotoWebViewActivity(SystemArticleResult.DatasBean datasBean) {
        if (datasBean == null) {
            return;
        }
        Intent intent=new Intent(this,WebViewActivity.class);
        intent.putExtra(Constants.URL, datasBean.getLink());
        intent.putExtra(Constants.ID, datasBean.getId());
        intent.putExtra(Constants.AUTHOR, "");
        intent.putExtra(Constants.TITLE, datasBean.getTitle());
        this.startActivity(intent);
        overridePendingTransition(R.anim.anim_web_enter, R.anim.anim_alpha);
    }


}
