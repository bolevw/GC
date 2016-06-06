package com.example.administrator.gc.presenter.activity;

import com.example.administrator.gc.api.AccountApi;
import com.example.administrator.gc.base.BasePresenter;
import com.example.administrator.gc.base.BaseSub;
import com.example.administrator.gc.model.PersonalHomePageModel;
import com.example.administrator.gc.ui.activity.PersonalHomePageActivity;

/**
 * Created by Administrator on 2016/4/14.
 */
public class PersonalHomePagePresenter implements BasePresenter<PersonalHomePageActivity> {

    private PersonalHomePageActivity view;

    @Override
    public void bind(PersonalHomePageActivity view) {
        this.view = view;
    }


    public void getData(String url) {
        AccountApi.getHomePage(url, new BaseSub<PersonalHomePageModel, PersonalHomePageActivity>(view) {
            @Override
            protected void error(String  e) {

            }

            @Override
            protected void next(PersonalHomePageModel s) {
                view.viewBindData(s);
            }
        });

    }

    @Override
    public void unBind() {
        this.view = null;
    }
}
