package com.apptutti.tuttistore.adapter;

import android.support.annotation.Nullable;

import com.apptutti.tuttistore.R;
import com.apptutti.tuttistore.bean.WeChatArticleResult;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class WeChatArticleAdapter extends BaseQuickAdapter<WeChatArticleResult.DatasBean, BaseViewHolder>{

    public WeChatArticleAdapter(int layoutResId) {
        super(layoutResId);
    }

    public WeChatArticleAdapter(int layoutResId, @Nullable List<WeChatArticleResult.DatasBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WeChatArticleResult.DatasBean item) {
        helper.setText(R.id.tv_article_title, item.getTitle())
                .setText(R.id.tv_article_author, item.getAuthor())
                .setText(R.id.tv_article_time, item.getNiceDate());
    }
}
