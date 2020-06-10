package com.apptutti.tuttistore.mvp.presenter;

import android.util.Log;

import com.apptutti.tuttistore.apiservice.MainApiService;
import com.apptutti.tuttistore.base.BaseObserver;
import com.apptutti.tuttistore.bean.BannerResult;
import com.apptutti.tuttistore.bean.HomeArticleResult;
import com.apptutti.tuttistore.bean.WeChatAuthorResult;
import com.apptutti.tuttistore.mvp.BasePresenter;
import com.apptutti.tuttistore.mvp.contract.HomeContract;

import java.util.List;

public class HomePresenter extends BasePresenter<HomeContract.View> implements HomeContract.Presenter{
    /**
     * 获取 Banner 数据
     */
    @Override
    public void getBanner(){

        addSubscribe(create(MainApiService.class).getBanner(), new BaseObserver<List<BannerResult>>(getView()) {
            @Override
            protected void onSuccess(List<BannerResult> data) {
                if (isViewAttached()) {
                    getView().onBanner(data);
                }
            }
        });

    }

     @Override
    public void getWeChatAuthors() {
        addSubscribe(create(MainApiService.class).getWeChatAuthors(), new BaseObserver<List<WeChatAuthorResult>>() {
            @Override
            protected void onSuccess(List<WeChatAuthorResult> data) {
                if (isViewAttached()) {
                    getView().onWeChatAuthors(data);
                }
            }
        });
    }

    /**
     * 获取首页文章数据
     *
     * @param page
     */
    @Override
    public void getHomeArticles(int page) {
        addSubscribe(create(MainApiService.class).getHomeArticles(page), new BaseObserver<HomeArticleResult>(getView()) {
            @Override
            protected void onSuccess(HomeArticleResult data) {
                Log.d("HomePresenter", data.toString());
                if (isViewAttached()) {
                    getView().onHomeArticles(data);
                }
            }
        });
    }
}
