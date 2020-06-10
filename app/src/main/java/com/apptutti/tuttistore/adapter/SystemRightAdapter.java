package com.apptutti.tuttistore.adapter;

import android.support.annotation.Nullable;

import com.apptutti.tuttistore.R;
import com.apptutti.tuttistore.bean.SystemResult;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * 体系中二级列表(右侧) recyclerview 适配器
 */
public class SystemRightAdapter extends BaseQuickAdapter<SystemResult.ChildrenBean, BaseViewHolder>{

    public SystemRightAdapter(int layoutResId) {
        super(layoutResId);
    }

    public SystemRightAdapter(int layoutResId, @Nullable List<SystemResult.ChildrenBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SystemResult.ChildrenBean item) {
        helper.setText(R.id.tv_system_right_title, item.getName());
    }
}
