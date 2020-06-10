package com.apptutti.tuttistore.adapter;

import android.support.annotation.Nullable;

import com.apptutti.tuttistore.R;
import com.apptutti.tuttistore.bean.SystemArticleResult;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class SystemArticleAdapter extends BaseQuickAdapter<SystemArticleResult.DatasBean, BaseViewHolder>{

    public SystemArticleAdapter(int layoutResId, @Nullable List<SystemArticleResult.DatasBean> data) {
        super(layoutResId, data);
    }

    public SystemArticleAdapter(@Nullable List<SystemArticleResult.DatasBean> data) {
        super(data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SystemArticleResult.DatasBean item) {
        helper.setText(R.id.tv_article_title, item.getTitle())
                .setText(R.id.tv_article_author, item.getAuthor())
                .setText(R.id.tv_article_time, item.getNiceDate());

    }
}
