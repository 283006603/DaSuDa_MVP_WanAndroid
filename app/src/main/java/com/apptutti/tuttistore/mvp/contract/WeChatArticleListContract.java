package com.apptutti.tuttistore.mvp.contract;

import com.apptutti.tuttistore.bean.WeChatArticleResult;
import com.apptutti.tuttistore.mvp.IView;

public interface WeChatArticleListContract{

    interface View extends IView{
        void onWeChatArticleList(WeChatArticleResult result);
    }

    interface Presenter {
        void getWeChatArticle(int id, int page);
    }
}
