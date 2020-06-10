package com.apptutti.tuttistore.mvp.contract;

import com.apptutti.tuttistore.bean.ProjectPageItem;
import com.apptutti.tuttistore.mvp.IView;

import java.util.List;

public interface ProjectContract{

    interface View extends IView{
        void onProjectTabs(List<ProjectPageItem> projectPageItemList);
    }

    interface Presenter {
        void getProjectTabs();
    }
}
