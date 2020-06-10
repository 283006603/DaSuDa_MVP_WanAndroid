package com.apptutti.tuttistore.mvp.presenter;

import android.util.Log;

import com.apptutti.tuttistore.apiservice.MainApiService;
import com.apptutti.tuttistore.base.BaseObserver;
import com.apptutti.tuttistore.bean.MeiziResult;
import com.apptutti.tuttistore.mvp.BasePresenter;
import com.apptutti.tuttistore.mvp.contract.MeiziContract;

import java.util.List;

public class MeiziPresenter extends BasePresenter<MeiziContract.View> implements MeiziContract.Presenter{
    @Override
    public void getMeiziList(int pageSize, int page){
        Log.d("MeiziPresenter", "aaa");
        addSubscribe(create(MainApiService.class).getMeiziList(pageSize, page),
                new BaseObserver<List<MeiziResult>>(getView()){

            @Override
            protected void onSuccess(List<MeiziResult> data){
                Log.d("MeiziPresenter", data.toString());
                if(isViewAttached()){
                    getView().onMeiziList(data);
                }
            }


        });
    }
}
