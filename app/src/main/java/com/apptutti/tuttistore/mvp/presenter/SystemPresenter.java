package com.apptutti.tuttistore.mvp.presenter;

import com.apptutti.tuttistore.apiservice.MainApiService;
import com.apptutti.tuttistore.base.BaseObserver;
import com.apptutti.tuttistore.bean.SystemResult;
import com.apptutti.tuttistore.mvp.BasePresenter;
import com.apptutti.tuttistore.mvp.contract.SystemContract;

import java.util.List;

public class SystemPresenter extends BasePresenter<SystemContract.View> implements SystemContract.Presenter{
    @Override
    public void getSystemList(){
        addSubscribe(create(MainApiService.class).getSystemList(), new BaseObserver<List<SystemResult>>(getView()){

            @Override
            protected void onSuccess(List<SystemResult> data){
               if(isViewAttached()){
                   getView().onSystemList(data);
               }
            }
        });
    }
}
