package com.apptutti.tuttistore.mactivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.apptutti.tuttistore.R;
import com.apptutti.tuttistore.base.BaseMVPActivity;
import com.apptutti.tuttistore.constants.Constants;
import com.apptutti.tuttistore.mvp.contract.WebContract;
import com.apptutti.tuttistore.mvp.presenter.WebPresenter;
import com.apptutti.tuttistore.utils.ToastUtil;
import com.apptutti.tuttistore.widge.ProgressWebView;

public class WebViewActivity extends BaseMVPActivity<WebPresenter> implements WebContract.View {

    private ProgressWebView webView;
    private String url;
    private Toolbar toolbar;
    private int id;
    private String title;
    private String author;
    private MenuItem favoriteMenuItem;
    private MenuItem shareMenuItem;
    private boolean hadFavorited = false;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_web_view;
    }

    @Override
    protected WebPresenter createPresenter() {
        return new WebPresenter();
    }

    @Override
    protected void initView() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        webView = findViewById(R.id.wv_web);
//        toolbar.setNavigationIcon(R.drawable.ic_close);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleWebViewBack();
            }
        });

        webView.setWebViewCallback(new ProgressWebView.WebViewCallback() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {

            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                toolbar.setTitle(title);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

            }

            @Override
            public void onPageFinished(WebView view, String url) {

            }

            @Override
            public void onLoadResource(WebView view, String url) {

            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

            }

            @Override
            public void onPageLoadComplete() {

            }

            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> valueCallback, WebChromeClient.FileChooserParams fileChooserParams) {
                return false;
            }

            @Override
            public void openFileChooser(ValueCallback<Uri> valueCallback, String acceptType, String capture) {

            }
        });
    }

    private void handleWebViewBack() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            finish();
        }
    }

    @Override
    protected void initData() {
        super.initData();
        Intent intent = getIntent();
        if (intent != null) {
            url = intent.getStringExtra(Constants.URL);
            id = intent.getIntExtra(Constants.ID, -1);
            title = intent.getStringExtra(Constants.TITLE);
            author = intent.getStringExtra(Constants.AUTHOR);
            webView.loadUrl(url);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        favoriteMenuItem = menu.findItem(R.id.item_web_favorite);
        shareMenuItem = menu.findItem(R.id.item_web_share);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_web, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_web_favorite) {
            addArticleFavorite();
        } else if (item.getItemId() == R.id.item_web_share) {
            shareArticle();
        }
        return true;
    }

//    @UserLoginTrace(value = 0)
    private void addArticleFavorite() {
        Log.d("WebViewActivity", "aaa");
        presenter.addArticleFavorite(id, title, author, url);
    }

    /**
     * 分享文章
     */
    private void shareArticle() {
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_alpha, R.anim.anim_web_exit);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        webView.removeAllViews();
        webView = null;
    }

    @Override
    public void onFavoriteAdded() {
        hadFavorited = true;
        ToastUtil.show(mContext, R.string.add_favorite_success);
    }

    @Override
    public void onFavoriteDeleted() {
        hadFavorited = false;
        ToastUtil.show(mContext, R.string.delete_favorite_success);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
