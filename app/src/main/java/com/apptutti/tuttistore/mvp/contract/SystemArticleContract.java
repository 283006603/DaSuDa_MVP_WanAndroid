package com.apptutti.tuttistore.mvp.contract;

import com.apptutti.tuttistore.bean.SystemArticleResult;
import com.apptutti.tuttistore.mvp.IView;

public interface SystemArticleContract {
    interface View extends IView{
        void onSystemArticleList(SystemArticleResult result);
    }

    interface Presenter{
        void getSystemArticleList(int page,int id);
    }
}
