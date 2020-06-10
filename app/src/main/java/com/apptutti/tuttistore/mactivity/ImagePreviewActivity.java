package com.apptutti.tuttistore.mactivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.apptutti.tuttistore.R;
import com.apptutti.tuttistore.adapter.ImagePreviewAdapter;
import com.apptutti.tuttistore.apiservice.MainApiService;
import com.apptutti.tuttistore.base.BaseActivity;
import com.apptutti.tuttistore.bean.MeiziResult;
import com.apptutti.tuttistore.http.RetrofitClient;
import com.apptutti.tuttistore.utils.AppUtil;
import com.apptutti.tuttistore.utils.ToastUtil;
import com.apptutti.tuttistore.widge.StatusBarUtil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class ImagePreviewActivity extends BaseActivity{


    private RecyclerView recyclerView;
    private ImagePreviewAdapter adapter;
    private TextView countTxtView;
    private ArrayList<MeiziResult>dataList=new ArrayList<>();
    private LinearLayoutManager layoutManager;
    private TextView saveTxtview;
    private int curPosition;
    private Disposable disposable;
    private BufferedInputStream bis;


    @Override
    protected int getLayoutResId(){
        return R.layout.activity_image_preview;
    }

    @Override
    protected void initView(){
        recyclerView=findViewById(R.id.rv_image_preview);
        countTxtView=findViewById(R.id.tv_image_preview_count);
        saveTxtview=findViewById(R.id.tv_image_save);
    }

    @Override
    protected void initData(){
        super.initData();
        layoutManager=new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        PagerSnapHelper pagerSnapHelper=new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(recyclerView);

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            curPosition=bundle.getInt("position");
            dataList=bundle.getParcelableArrayList("meizis");
            adapter=new ImagePreviewAdapter(R.layout.item_image_preview,dataList);
            recyclerView.setAdapter(adapter);
            recyclerView.scrollToPosition(curPosition);
            String s=String.format(getResources().getString(R.string.position_and_count),curPosition+1,dataList.size());
            countTxtView.setText(s);

            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState){
                    super.onScrollStateChanged(recyclerView, newState);
                    if(newState==RecyclerView.SCROLL_STATE_IDLE){
                        curPosition= layoutManager.findLastVisibleItemPosition();
                        countTxtView.setText((curPosition+1)+"/"+dataList.size());
                    }
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy){
                    super.onScrolled(recyclerView, dx, dy);
                }
            });
            
            
            saveTxtview.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    MeiziResult meiziResult=dataList.get(curPosition);
                    if(meiziResult!=null){
                        String url=meiziResult.getUrl();
                        savaImage(url);
                    }
                }
            });
        }

    }



    //可以参考https://www.cnblogs.com/zhaoyanjun/archive/2017/08/21/7402945.html

//    Map操作符的作用就是将Observable所发送送的信息进行格式转换或者处理，
//    然后转变为另外一个类型，发送给Observer

    private void savaImage(String url){
        MainApiService mainApiService= RetrofitClient.getInstance().getRetrofit().create(MainApiService.class);

        disposable=mainApiService.downloadImage(url)
                .subscribeOn(Schedulers.newThread())//在子线程发射
                .map(new Function<ResponseBody, Boolean>(){// map 操作符，就是转换输入、输出 的类型
                    @Override
                    public Boolean apply(ResponseBody responseBody) throws Exception{
                        return writeToSdcard(mContext, responseBody.byteStream());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())//在主线程接收
                .subscribe(new Consumer<Boolean>(){
                    @Override
                    public void accept(Boolean result) throws Exception{
                        ToastUtil.show(mContext, result ? "保存成功" : "保存失败");
                    }
                });
    }

    private boolean writeToSdcard(Context context, InputStream inputStream) {
        if (inputStream == null) {
            return false;
        }
        File dirFile = new File(Environment.getExternalStorageDirectory(), AppUtil.getAppName(mContext));
        String fileName = AppUtil.getAppName(mContext) + "_" + System.currentTimeMillis() + ".jpg";
        File file = new File(dirFile, fileName);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        byte[] buffer = new byte[1024 * 5];
        int len = 0;
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        try {
            bis = new BufferedInputStream(inputStream);
            fos = new FileOutputStream(file);
            while ((len = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse(file.getAbsolutePath())));
        return true;
    }


    @Override
    public void setStatusBarColor() {
        StatusBarUtil.setColor(this, getResources().getColor(android.R.color.black), 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
