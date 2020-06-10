package com.apptutti.tuttistore.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.apptutti.tuttistore.MainActivity;
import com.apptutti.tuttistore.R;
import com.apptutti.tuttistore.adapter.HomeArticleAdapter;
import com.apptutti.tuttistore.base.BaseMVPFragment;
import com.apptutti.tuttistore.bean.BannerResult;
import com.apptutti.tuttistore.bean.HomeArticleResult;
import com.apptutti.tuttistore.bean.WeChatAuthorResult;
import com.apptutti.tuttistore.constants.Constants;
import com.apptutti.tuttistore.mactivity.WeChatArticleListActivity;
import com.apptutti.tuttistore.mactivity.WebViewActivity;
import com.apptutti.tuttistore.mvp.contract.HomeContract;
import com.apptutti.tuttistore.mvp.presenter.HomePresenter;
import com.apptutti.tuttistore.widge.LinearItemDecoration;
import com.apptutti.tuttistore.widge.StatusBarUtil;
import com.apptutti.tuttistore.widge.gridviewpager.GridRecyclerAdapter;
import com.apptutti.tuttistore.widge.gridviewpager.GridViewPager;
import com.apptutti.tuttistore.widge.gridviewpager.GridViewPagerAdapter;
import com.apptutti.tuttistore.widge.imageloader.GlideImageLoader;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseMVPFragment<HomePresenter> implements HomeContract.View,View.OnClickListener{

    private static final String TAG = "HomeFragment";
    private int[] colors = {0xffec407a, 0xffab47bc, 0xff29b6f6,
            0xff7e57c2, 0xffe24073, 0xffee8360, 0xff26a69a,
            0xffef5350, 0xff2baf2b, 0xffffa726};
    private Banner banner;
    private RecyclerView recyclerView;
    private int page = 0;
    private HomeArticleAdapter homeArticleAdapter;
    private List<HomeArticleResult.DatasBean> dataList = new ArrayList<>();
    private View headerView;
    private View searchLayoutView;
    private TextView loginTxtView;
    private TextView searchTxtView;
    private ImageView logoImgView;
    private int bannerHeight;
    private LinearLayoutManager linearLayoutManager;
    private int searchLayoutHeight;
    private GridViewPager gridViewPager;
    private RefreshLayout refreshLayout;


    public HomeFragment(){
    }



    public static HomeFragment newInstance(/*String param1, String param2*/){
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_home;
    }


    @Override
    public void initView(View rootView) {
        refreshLayout = rootView.findViewById(R.id.srl_home);
        logoImgView = rootView.findViewById(R.id.iv_home_logo);
        recyclerView = rootView.findViewById(R.id.rv_home);
        searchLayoutView = rootView.findViewById(R.id.rl_search_header);
        loginTxtView = rootView.findViewById(R.id.tv_home_login);
        searchTxtView = rootView.findViewById(R.id.tv_home_search);

        headerView = LayoutInflater.from(mContext).inflate(R.layout.layout_home_header, null);
        banner = headerView.findViewById(R.id.banner_home);
        gridViewPager = headerView.findViewById(R.id.gvp_viewpager);
    }

    @Override
    protected void initData(){
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) searchLayoutView.getLayoutParams();
        lp.topMargin = StatusBarUtil.getStatusBarHeight(mContext);
        linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        // 设置 ItemDecoration 作为分割线
        LinearItemDecoration itemDecoration = new LinearItemDecoration(mContext).height(8f)    // dp
                .color(Color.parseColor("#66dddddd"));  // color 的 int 值，不是 R.color 中的值
        recyclerView.addItemDecoration(itemDecoration);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());

        // 请求 banner 数据
        presenter.getBanner();

        // 请求微信公众号列表
        presenter.getWeChatAuthors();

        // 请求首页文章列表
        presenter.getHomeArticles(page);

        setListener();
    }

    private void setListener(){
        loginTxtView.setOnClickListener(this);
        searchTxtView.setOnClickListener(this);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState){
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy){
                super.onScrolled(recyclerView, dx, dy);
                int scrollOffset = recyclerView.computeVerticalScrollOffset();
                setSearchLayoutAlpha(scrollOffset);
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener(){
            @Override
            public void onRefresh(RefreshLayout refreshlayout){
                Log.d(TAG, "刷新");
                page=0;
                dataList.clear();
                presenter.getHomeArticles(page);
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener(){
            @Override
            public void onLoadMore(RefreshLayout refreshlayout){
                Log.d(TAG, "加载更多");
                Log.d(TAG, "page:" + page);
                presenter.getHomeArticles(page);
//                 refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });
    }



    @Override
    public void onAttach(Context context){
        super.onAttach(context);
    }

    @Override
    protected HomePresenter createPresenter(){
        return new HomePresenter();
    }

    @Override
    public void onDetach(){
        super.onDetach();
    }

    @Override
    public void showLoading(){
        Log.d(TAG, "showLoading");
    }

    @Override
    public void hideLoading(){
        refreshLayout.finishLoadMore();
        Log.d(TAG, "hideLoading");
    }


    /**
     * Banner 数据成功回调
     *
     * @param bannerResults
     */
    @Override
    public void onBanner(final List<BannerResult> bannerResults){
        if(bannerResults == null || bannerResults.size() == 0){
            return;
        }
        List<String> images = getImages(bannerResults);
        //设置图片集合
        banner.setImages(images);
        banner.setOnBannerListener(new OnBannerListener(){
            @Override
            public void OnBannerClick(int position){
                BannerResult bannerResult = bannerResults.get(position);
                gotoWebViewActivityFromBanner(bannerResult);
            }
        });
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    @Override
    public void onWeChatAuthors(final List<WeChatAuthorResult> weChatAuthorResults){
        if(weChatAuthorResults == null){
            return;
        }
        gridViewPager.setOnGridItemClickListener(new GridViewPager.OnGridItemClickListener(){
            @Override
            public void onGridItemClick(int position, View view){
                gotoWeChatArticleListActivity(weChatAuthorResults.get(position));
            }
        });
        gridViewPager.setAdapter(new GridViewPagerAdapter<WeChatAuthorResult>(weChatAuthorResults){
            @Override
            public void bindData(GridRecyclerAdapter.ViewHolder viewHolder, WeChatAuthorResult weChatAuthorResult, int position){
                ShapeDrawable shapeDrawable = new ShapeDrawable();
                shapeDrawable.setShape(new OvalShape());
                shapeDrawable.getPaint().setColor(colors[position % colors.length]);
                viewHolder.setText(R.id.tv_home_author_icon, String.valueOf(weChatAuthorResult.getName().charAt(0))).setText(R.id.tv_home_author_name, weChatAuthorResult.getName()).setBackground(R.id.tv_home_author_icon, shapeDrawable);
            }
        });
    }


    //顶部banner点击进入Webview
    private void gotoWebViewActivityFromBanner(BannerResult bannerResult){
        if(bannerResult == null){
            return;
        }
        Intent intent=new Intent(getActivity(), WebViewActivity.class);

        intent.putExtra(Constants.URL, bannerResult.getUrl());
        intent.putExtra(Constants.ID, bannerResult.getId());
        intent.putExtra(Constants.AUTHOR, "");
        intent.putExtra(Constants.TITLE, bannerResult.getTitle());
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.anim_web_enter, R.anim.anim_alpha);


    }


    /**
     * 跳转至微信公众号文章列表页面
     *
     * @param weChatAuthorResult
     */
    //点击公众号进入文章列表
    private void gotoWeChatArticleListActivity(WeChatAuthorResult weChatAuthorResult){

        Intent intent=new Intent(getActivity(), WeChatArticleListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("WeChatAuthorResult", weChatAuthorResult);
        intent.putExtra("bundle",bundle);
        getContext().startActivity(intent);
    }



    //点击普通的RecycleView直接进去WebView详情界面
    private void gotoWebViewActivity(HomeArticleResult.DatasBean datasBean){
        Intent intent=new Intent(getActivity(),WebViewActivity.class);
        intent.putExtra(Constants.URL, datasBean.getLink());
        intent.putExtra(Constants.ID, datasBean.getId());
        intent.putExtra(Constants.AUTHOR, datasBean.getAuthor());
        intent.putExtra(Constants.TITLE, datasBean.getTitle());
        getContext().startActivity(intent);
        getActivity().overridePendingTransition(R.anim.anim_web_enter, R.anim.anim_alpha);
    }

    /**
     * 首页文章列表数据回调
     *
     * @param result
     */
    @Override
    public void onHomeArticles(HomeArticleResult result){
        page++;
        if(result != null){
            List<HomeArticleResult.DatasBean> datas = result.getDatas();
            if(datas != null){
                Log.d(TAG, "result.getDatas():" + result.getDatas());
                dataList.addAll(result.getDatas());
                if(homeArticleAdapter == null){
                    homeArticleAdapter = new HomeArticleAdapter(R.layout.item_home_article, dataList);
                    homeArticleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener(){
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position){
                            gotoWebViewActivity(dataList.get(position));
                        }
                    });
                    homeArticleAdapter.addHeaderView(headerView);
                    recyclerView.setAdapter(homeArticleAdapter);
                }else{
                    //                    homeArticleAdapter.notifyDataSetChanged();
                    homeArticleAdapter.setNewData(dataList);
                }
            }else{
                refreshLayout.setNoMoreData(true);
            }
        }
    }



    private List<String> getImages(List<BannerResult> bannerResults){
        List<String> list = new ArrayList<>();
        if(bannerResults != null){
            for(BannerResult bannerResult : bannerResults){
                list.add(bannerResult.getImagePath());
            }
        }
        return list;
    }

    @Override
    public void onClick(View v){
        if(v.getId() == R.id.tv_home_login){
            gotoLoginActivity();
        }else if(v.getId() == R.id.tv_home_search){
            gotoSearchActivity();
        }
    }

    private void gotoSearchActivity(){
      /*  ARouter.getInstance()
                .build("/search/SearchActivity")
                .navigation();*/
    }

    private void gotoLoginActivity(){
       /* ARouter.getInstance()
                .build("/user/LoginActivity")
                .navigation();*/
    }



    @Override
    public void onDestroyView(){
        super.onDestroyView();
    }


    private void setSearchLayoutAlpha(int offset){

        if(searchLayoutHeight == 0){
            searchLayoutHeight = searchLayoutView.getMeasuredHeight();
        }
        if(bannerHeight == 0){
            bannerHeight = banner.getMeasuredHeight();
        }
        int maxOffset = bannerHeight - StatusBarUtil.getStatusBarHeight(mContext) - searchLayoutHeight;
        Log.e(TAG, "offset: " + offset + ", maxOffset = " + maxOffset);
        if(offset <= maxOffset){
            float percent = offset * 1.0f / maxOffset;
            searchLayoutView.getBackground().mutate().setAlpha((int) (255 * percent));
            loginTxtView.setTextColor(getResources().getColor(android.R.color.white));
            logoImgView.setImageResource(R.drawable.ic_home_logo_white);
            searchTxtView.setBackground(getResources().getDrawable(R.drawable.shape_home_input));
            ((MainActivity) getActivity()).setStatusBarTranslucent((int) (255 * percent));
//                        ((MainActivity) getActivity()).setStatusBarTextColorBlack();
        }else{
            loginTxtView.setTextColor(getResources().getColor(R.color.colorAccent));
            logoImgView.setImageResource(R.drawable.ic_home_logo_black);
            searchTxtView.setBackground(getResources().getDrawable(R.drawable.shape_home_input_dark));
            //            ((MainActivity) getActivity()).setStatusBarWhite();
        }
    }
}
