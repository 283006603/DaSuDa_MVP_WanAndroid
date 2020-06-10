package com.apptutti.tuttistore.adapter;

import android.support.annotation.Nullable;

import com.apptutti.tuttistore.R;
import com.apptutti.tuttistore.bean.SystemResult;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class SystemLeftAdapter extends BaseQuickAdapter<SystemResult, BaseViewHolder>{

    public SystemLeftAdapter(int layoutResId, @Nullable List<SystemResult> data){
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SystemResult item){
        helper.setText(R.id.tv_system_left_title,item.getName())
                .setBackgroundColor(R.id.tv_system_left_title,item.isSelected()?0xffffffff : 0xffeeeeee);
    }
}
