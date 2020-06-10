package com.apptutti.tuttistore;

import android.widget.Button;
import android.widget.EditText;

import com.apptutti.tuttistore.base.BaseActivity;

import butterknife.BindView;

public class LoginActivity extends BaseActivity{

    @BindView(R.id.login_name)
    EditText loginName;
    @BindView(R.id.login_pwd)
    EditText loginPwd;
    @BindView(R.id.login_confirm)
    Button loginConfirm;









    @Override
    protected int getLayoutResId(){
        return R.layout.activity_login;
    }

    @Override
    protected void initView(){
    }
}
