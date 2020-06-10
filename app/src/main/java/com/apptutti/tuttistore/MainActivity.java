package com.apptutti.tuttistore;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.apptutti.tuttistore.base.BaseActivity;
import com.apptutti.tuttistore.fragment.HomeFragment;
import com.apptutti.tuttistore.fragment.MineFragment;
import com.apptutti.tuttistore.fragment.ProjectFragment;
import com.apptutti.tuttistore.fragment.SystemFragment;
import com.apptutti.tuttistore.widge.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.apptutti.tuttistore.R.id.main_rb_applist;
import static com.apptutti.tuttistore.R.id.main_rb_stats;

public class MainActivity extends BaseActivity{

    @BindView(R.id.main_RadioGroup)
    RadioGroup mainRadioGroup;

    @BindView(R.id.main_rb_home)
    RadioButton mainRbHome;

    @BindView(main_rb_applist)
    RadioButton mainRbApplist;

    @BindView(main_rb_stats)
    RadioButton mainRbStats;

    @BindView(R.id.main_rb_mine)
    RadioButton mainRbMine;

    private List<Fragment> fragments;
    private long lastBackTime = 0;
    private FragmentManager fragmentManager;
    private int currentSelectedId = R.id.main_rb_home;
    private ProjectFragment projectFragment;

    @Override
    protected void initView(){
//        mainRadioGroup = findViewById(R.id.main_RadioGroup);
//        mainRbHome = findViewById(R.id.main_rb_home);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected int getLayoutResId(){
        return R.layout.activity_main;
    }

    @Override
    protected void initData(){
        super.initData();
        fragmentManager = getSupportFragmentManager();
        createFragment();
        selectFragment(0);
        mainRbHome.setChecked(true);
        mainRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == currentSelectedId) {
                    return;
                }
                currentSelectedId = checkedId;
                if (checkedId == R.id.main_rb_home) {
                    selectFragment(0);
                } else if (checkedId == R.id.main_rb_applist) {
                    selectFragment(1);
                    projectFragment.setStatusBarColor(Color.WHITE);
                    StatusBarUtil.setLightMode(MainActivity.this);
                } else if (checkedId == R.id.main_rb_stats) {
                    selectFragment(2);
                } else if (checkedId == R.id.main_rb_mine) {
                    selectFragment(3);

                }
            }
        });

    }


    private void createFragment() {
        fragments = new ArrayList<>();
        FragmentTransaction ft = fragmentManager.beginTransaction();

        HomeFragment homeFragment = new HomeFragment();
        ft.add(R.id.fl_main_container, homeFragment);
        fragments.add(homeFragment);

        projectFragment = new ProjectFragment();
        ft.add(R.id.fl_main_container, projectFragment);
        fragments.add(projectFragment);

        SystemFragment systemFragment = new SystemFragment();
        ft.add(R.id.fl_main_container, systemFragment);
        fragments.add(systemFragment);

        MineFragment mineFragment = new MineFragment();
        ft.add(R.id.fl_main_container, mineFragment);
        fragments.add(mineFragment);

        // 提交事务
        ft.commit();
    }




    private void selectFragment(int index) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        for (int i = 0; i < fragments.size(); i++) {
            if (i == index) {
                ft.show(fragments.get(i));
            } else {
                ft.hide(fragments.get(i));
            }
        }
        ft.commit();
    }

    @Override
    public void onBackPressed(){
        long currentBackTime = System.currentTimeMillis();
        if(currentBackTime - lastBackTime > 2000){
            Toast.makeText(this, "再按一次返回键退出", Toast.LENGTH_SHORT).show();
            lastBackTime = currentBackTime;
        }else{
            super.onBackPressed();
            System.exit(0);
        }
    }

    @Override
    public void setStatusBarColor(){
        StatusBarUtil.setTransparentForImageViewInFragment(this, null);
    }

    public void setStatusBarTranslucent(int alpha){
        StatusBarUtil.setTranslucentForImageViewInFragment(this, alpha, null);
    }

    public void setStatusBarTextColorBlack(){
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//黑色
    }
}
