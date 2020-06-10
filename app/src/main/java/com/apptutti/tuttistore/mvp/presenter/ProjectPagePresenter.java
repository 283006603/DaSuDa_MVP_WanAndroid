package com.apptutti.tuttistore.mvp.presenter;

import android.util.Log;

import com.apptutti.tuttistore.apiservice.MainApiService;
import com.apptutti.tuttistore.base.BaseObserver;
import com.apptutti.tuttistore.bean.ProjectResult;
import com.apptutti.tuttistore.mvp.BasePresenter;
import com.apptutti.tuttistore.mvp.contract.ProjectPageContract;

public class ProjectPagePresenter extends BasePresenter<ProjectPageContract.View>
        implements ProjectPageContract.Presenter {

    @Override
    public void getProjects(int id, int page) {
        Log.d("ProjectPagePresenter", "aaaa");
        addSubscribe(create(MainApiService.class).getProjects(page, id),
                new BaseObserver<ProjectResult>(getView()) {

                    @Override
                    protected void onSuccess(ProjectResult data) {
                        Log.d("ProjectPagePresenter", "data:" + data);
                        if (isViewAttached()) {
                            Log.d("ProjectPagePresenter", "data:" + data);
                            getView().onProjectList(data);
                        }
                    }


                });
    }
}
