package com.apptutti.tuttistore.mvp.contract;

import com.apptutti.tuttistore.bean.SystemResult;
import com.apptutti.tuttistore.mvp.IView;

import java.util.List;

public interface SystemContract{
    interface View extends IView{
        void onSystemList(List<SystemResult>systemResults);
    }

    interface Presenter{
        void getSystemList();
    }
}
