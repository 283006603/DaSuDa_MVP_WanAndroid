package com.apptutti.tuttistore.mactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.apptutti.tuttistore.R;
import com.apptutti.tuttistore.adapter.MeiziAdapter;
import com.apptutti.tuttistore.base.BaseMVPActivity;
import com.apptutti.tuttistore.bean.MeiziResult;
import com.apptutti.tuttistore.mvp.contract.MeiziContract;
import com.apptutti.tuttistore.mvp.presenter.MeiziPresenter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

public class ImageMeiziActivity extends BaseMVPActivity<MeiziPresenter> implements MeiziContract.View{

    private RefreshLayout refreshLayout;
    private RecyclerView recycleView;
    private int pagesize=16;
    private int page=1;
    private ArrayList<MeiziResult>datalist=new ArrayList<>();
    private MeiziAdapter adapter;

    @Override
    protected int getLayoutResId(){
        
        return R.layout.activity_image_meizi;
    }

    @Override
    protected void initView(){
        Log.d("ImageMeiziActivity", "initView");
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("妹子");
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });

        refreshLayout = findViewById(R.id.srl_meizi);
        recycleView=findViewById(R.id.rc_meizi);

    }

    @Override
    protected void initData(){
        super.initData();
        Log.d("ImageMeiziActivity", "initData");
        recycleView.setLayoutManager(new GridLayoutManager(mContext,2));
        presenter.getMeiziList(pagesize,page);
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener(){
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout){
                Log.d("ImageMeiziActivity", "page:" + page);
                presenter.getMeiziList(pagesize,page);
            }
        });

    }


    @Override
    protected MeiziPresenter createPresenter(){
        return new MeiziPresenter();
    }

    @Override
    public void onMeiziList( List<MeiziResult> meiziResults){
        Log.d("ImageMeiziActivity", "page:" + page);
        Log.d("ImageMeiziActivity", meiziResults.toString());
        page++;
        datalist.addAll(meiziResults);
        if(adapter==null){
            adapter=new MeiziAdapter(R.layout.item_meizi,datalist);
            adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener(){
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position){
                    Bundle bundle=new Bundle();
                    bundle.putParcelableArrayList("meizis",datalist);
                    bundle.putInt("position",position);

                    Intent intent=new Intent(mContext,ImagePreviewActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);

                }
            });
            recycleView.setAdapter(adapter);
        }else{
            adapter.setNewData(datalist);
        }

    }

    @Override
    public void showLoading(){
    }

    @Override
    public void hideLoading(){
        Log.d("ImageMeiziActivity", "hideLoading");
        refreshLayout.finishLoadMore();
    }
}
