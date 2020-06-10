package com.apptutti.tuttistore.mvp.presenter;

import com.apptutti.tuttistore.apiservice.MainApiService;
import com.apptutti.tuttistore.base.BaseObserver;
import com.apptutti.tuttistore.bean.ProjectPageItem;
import com.apptutti.tuttistore.bean.ProjectTabItem;
import com.apptutti.tuttistore.fragment.ProjectPageFragment;
import com.apptutti.tuttistore.mvp.BasePresenter;
import com.apptutti.tuttistore.mvp.contract.ProjectContract;

import java.util.ArrayList;
import java.util.List;

public class ProjectPresenter extends BasePresenter<ProjectContract.View> implements ProjectContract.Presenter {


    /**
     * project 栏目
     */
    @Override
    public void getProjectTabs() {
        addSubscribe(create(MainApiService.class).getProjectTabs(), new BaseObserver<List<ProjectTabItem>>() {
            @Override
            protected void onSuccess(List<ProjectTabItem> data) {
                List<ProjectPageItem> projectPageItemList = createProjectPages(data);
                if (isViewAttached()) {
                    getView().onProjectTabs(projectPageItemList);
                }

            }
        });
    }

    private List<ProjectPageItem> createProjectPages(List<ProjectTabItem> projectItems) {
        if (projectItems == null || projectItems.size() == 0) {
            return new ArrayList<>();
        }
        List<ProjectPageItem> projectPageItemList = new ArrayList<>();
        for (ProjectTabItem projectItem : projectItems) {
            ProjectPageItem projectPageItem = new ProjectPageItem(projectItem.getId(),
                    projectItem.getName(), ProjectPageFragment.newInstance(projectItem.getId()));
            projectPageItemList.add(projectPageItem);
        }
        return projectPageItemList;
    }
}
