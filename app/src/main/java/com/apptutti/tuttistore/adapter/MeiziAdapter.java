package com.apptutti.tuttistore.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.apptutti.tuttistore.R;
import com.apptutti.tuttistore.bean.MeiziResult;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class MeiziAdapter extends BaseQuickAdapter<MeiziResult, BaseViewHolder>{
    public MeiziAdapter(int layoutResId, @Nullable List<MeiziResult> data){
        super(layoutResId, data);
    }

    public MeiziAdapter(@Nullable List<MeiziResult> data){
        super(data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MeiziResult item){
        ImageView imageView=helper.getView(R.id.iv_meizi_image);
        Glide.with(mContext)
                .load(item.getUrl())
                .into(imageView);
    }
}
