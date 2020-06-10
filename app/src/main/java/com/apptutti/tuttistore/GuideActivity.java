package com.apptutti.tuttistore;

import android.content.Intent;

import com.apptutti.tuttistore.base.BaseActivity;

public class GuideActivity extends BaseActivity{



    @Override
    protected int getLayoutResId(){
        return R.layout.activity_guide;
    }

    @Override
    protected void initView(){
    }

    @Override
    protected void onResume(){
        super.onResume();
        gotoMainActivity();
    }

    private void gotoMainActivity(){
        Intent intent=new Intent(GuideActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}


