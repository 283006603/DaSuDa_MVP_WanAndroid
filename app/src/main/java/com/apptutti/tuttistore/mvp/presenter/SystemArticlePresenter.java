package com.apptutti.tuttistore.mvp.presenter;

import android.util.Log;

import com.apptutti.tuttistore.apiservice.MainApiService;
import com.apptutti.tuttistore.base.BaseObserver;
import com.apptutti.tuttistore.bean.SystemArticleResult;
import com.apptutti.tuttistore.mvp.BasePresenter;
import com.apptutti.tuttistore.mvp.contract.SystemArticleContract;

public class SystemArticlePresenter extends BasePresenter<SystemArticleContract.View> implements SystemArticleContract.Presenter{
    @Override
    public void getSystemArticleList(int page, int id){
        addSubscribe(create(MainApiService.class).getSystemArticles(page,id), new BaseObserver<SystemArticleResult>(getView()){
            @Override
            protected void onSuccess(SystemArticleResult data){
                if(isViewAttached()){
                    Log.d("SystemArticlePresenter", data.toString());
                    getView().onSystemArticleList(data);
                }
            }
        });
    }
}
