package com.apptutti.tuttistore.mvp.presenter;

import com.apptutti.tuttistore.apiservice.MainApiService;
import com.apptutti.tuttistore.base.BaseObserver;
import com.apptutti.tuttistore.bean.FavoriteAddResult;
import com.apptutti.tuttistore.mvp.BasePresenter;
import com.apptutti.tuttistore.mvp.contract.WebContract;

import io.reactivex.Observable;

public class WebPresenter extends BasePresenter<WebContract.View> implements WebContract.Presenter {

    @Override
    public void addArticleFavorite(int id, String title, String author, String link) {
        Observable observable;
        if (id == -1) {   // 站外文章
            observable = create(MainApiService.class).addFavorite(title, author, link);
        } else {     // 站内文章
            observable = create(MainApiService.class).addFavorite(id);
        }
        addSubscribe(observable, new BaseObserver<FavoriteAddResult>() {

            @Override
            protected void onSuccess(FavoriteAddResult data) {
                if (isViewAttached()) {
                    getView().onFavoriteAdded();
                }
            }
        });
    }
}
