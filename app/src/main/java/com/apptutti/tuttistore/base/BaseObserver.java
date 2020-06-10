package com.apptutti.tuttistore.base;

import android.util.Log;

import com.apptutti.tuttistore.http.ApiException;
import com.apptutti.tuttistore.http.ExceptionHandler;
import com.apptutti.tuttistore.mvp.IView;

import io.reactivex.observers.DisposableObserver;

public abstract class BaseObserver<T> extends DisposableObserver<BaseResponse<T>>{
    private IView baseView;

    public BaseObserver() {
    }

    public BaseObserver(IView baseView) {
        this.baseView = baseView;
    }

    @Override
    protected void onStart() {
        super.onStart();
//        if (!NetworkUtils.isConnected(AppUtils.getAppContext())) {
            // Logger.d("没有网络");
//            ToastUtils.showSingleLongToast("请检查网络连接，然后重试");
            Log.d("BaseObserver","请检查网络连接，然后重试");
//        } else {
            if (baseView != null) {
                baseView.showLoading();
            }
//        }
    }

    @Override
    public void onNext(BaseResponse<T> baseResponse) {
        if (baseView != null) {
            baseView.hideLoading();
        }
        int errcode = baseResponse.getErrorCode();
        Log.d("BaseObserver", "errcode:" + errcode);
        String errmsg = baseResponse.getErrorMsg();
        // 兼容 gank api
        boolean isOk = !baseResponse.isError();
        if (errcode == 0 || errcode == 200) {   // wanandroid api
            T data = baseResponse.getData();
            Log.d("BaseObserver", baseResponse.getData().toString());
            // 将服务端获取到的正常数据传递给上层调用方
            onSuccess(data);
        } else if (isOk) {   // gank api
            Log.d("BaseObserver", "isok");
            T data = baseResponse.getResults();
            onSuccess(data);
        } else {
            Log.d("BaseObserver","errcode:"+errcode+"errmsg=="+errmsg);
            onError(new ApiException(errcode, errmsg));
        }
    }

    /**
     * 回调正常数据
     *
     * @param data
     */
    protected abstract void onSuccess(T data);

    /**
     * 异常处理，包括两方面数据：
     * (1) 服务端没有没有返回数据，HttpException，如网络异常，连接超时;
     * (2) 服务端返回了数据，但 errcode!=0,即服务端返回的data为空，如 密码错误,App登陆超时,token失效
     */
    @Override
    public void onError(Throwable e) {
        Log.d("BaseObserver","BaseSubscriber:"+e.toString());
     /*   if (mView == null) return;
        mView.complete();//完成操作
        if (mMsg != null && !TextUtils.isEmpty(mMsg)) {
            mView.showError(mMsg);
        } else if (e instanceof com.apptutti.tuttistore.network.exception.ApiException) {
            mView.showError(e.toString());
        } else if (e instanceof SocketTimeoutException) {
            mView.showError("服务器响应超时ヽ(≧Д≦)ノ");
        } else if (e instanceof HttpException) {
            mView.showError("数据加载失败ヽ(≧Д≦)ノ");
        } else if(!NetworkUtils.isConnected(AppUtils.getAppContext())){
            mView.showError("请检查网络连接，然后重试");
        } else {
            mView.showError("未知错误ヽ(≧Д≦)ノ");
        }*/
        ExceptionHandler.handleException(e);
    }

    @Override
    public void onComplete() {
        if (baseView != null) {
            baseView.hideLoading();
        }
    }
}
