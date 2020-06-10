package com.apptutti.tuttistore.mvp.contract;

import com.apptutti.tuttistore.bean.MeiziResult;
import com.apptutti.tuttistore.mvp.IView;

import java.util.List;

public interface MeiziContract{

    interface View extends IView{
        void onMeiziList(List<MeiziResult>meiziResults);
    }


    interface Presenter{
        void getMeiziList(int pageSize ,int page);
    }
}
