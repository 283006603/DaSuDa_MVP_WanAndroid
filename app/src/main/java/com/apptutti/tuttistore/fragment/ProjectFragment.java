package com.apptutti.tuttistore.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.apptutti.tuttistore.R;
import com.apptutti.tuttistore.adapter.ProjectPagerAdapter;
import com.apptutti.tuttistore.base.BaseFragment;
import com.apptutti.tuttistore.base.BaseMVPFragment;
import com.apptutti.tuttistore.bean.ProjectPageItem;
import com.apptutti.tuttistore.mvp.contract.ProjectContract;
import com.apptutti.tuttistore.mvp.presenter.ProjectPresenter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProjectFragment extends BaseMVPFragment<ProjectPresenter> implements ProjectContract.View{

    private View mFakeStatusBar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public static BaseFragment newInstance(/*String uid*/){
        ProjectFragment fragment = new ProjectFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
            return fragment;
}

    @Override
    protected ProjectPresenter createPresenter(){
        return new ProjectPresenter();
    }

    @Override
    protected int getLayoutResId(){
        return R.layout.fragment_project;
    }

    @Override
    protected void initView(View rootView){
        mFakeStatusBar = rootView.findViewById(R.id.fake_status_bar);
        tabLayout = rootView.findViewById(R.id.tl_project);
        viewPager = rootView.findViewById(R.id.vp_project_page);



        mFakeStatusBar.post(new Runnable() {
            @Override
            public void run() {
                int height = mFakeStatusBar.getHeight();
                Log.d("ProjectFragment", "run: " + height);
            }
        });



    }

    @Override
    protected void initData(){
        presenter.getProjectTabs();
    }
    @Override
    public void onProjectTabs(List<ProjectPageItem> projectPageItemList){

        ProjectPagerAdapter pageAdapter=new ProjectPagerAdapter(getChildFragmentManager());
        pageAdapter.setPages(projectPageItemList);
        viewPager.setAdapter(pageAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }


    @Override
    public void showLoading(){
    }

    @Override
    public void hideLoading(){
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
    }

    @Override
    public void onDetach(){
        super.onDetach();
    }



    public void setStatusBarColor(int color) {
        mFakeStatusBar.setBackgroundColor(color);
    }


}
