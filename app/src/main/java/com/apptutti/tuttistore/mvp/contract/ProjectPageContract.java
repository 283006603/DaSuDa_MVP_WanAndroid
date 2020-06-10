package com.apptutti.tuttistore.mvp.contract;

import com.apptutti.tuttistore.bean.ProjectResult;
import com.apptutti.tuttistore.mvp.IView;

public interface ProjectPageContract{

    interface View extends IView{
        void onProjectList(ProjectResult projectResult);
    }

    interface Presenter {
        void getProjects(int id, int page);
    }
}
