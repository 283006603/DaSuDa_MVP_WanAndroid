package com.apptutti.tuttistore.mvp.presenter;

import com.apptutti.tuttistore.apiservice.MainApiService;
import com.apptutti.tuttistore.base.BaseObserver;
import com.apptutti.tuttistore.bean.WeChatArticleResult;
import com.apptutti.tuttistore.mvp.BasePresenter;
import com.apptutti.tuttistore.mvp.contract.WeChatArticleListContract;

public class WeChatArticlePresenter extends BasePresenter<WeChatArticleListContract.View>
        implements WeChatArticleListContract.Presenter {

    @Override
    public void getWeChatArticle(int id, int page) {
        addSubscribe(create(MainApiService.class).getWeChatArticles(id, page),
                new BaseObserver<WeChatArticleResult>(getView()) {

                    @Override
                    protected void onSuccess(WeChatArticleResult data) {
                        if (isViewAttached()) {
                            getView().onWeChatArticleList(data);
                        }
                    }
                });
    }
}
