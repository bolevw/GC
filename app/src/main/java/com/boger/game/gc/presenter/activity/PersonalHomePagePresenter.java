package com.boger.game.gc.presenter.activity;

import com.boger.game.gc.api.AccountApi;
import com.boger.game.gc.base.BasePresenter;
import com.boger.game.gc.base.BaseSub;
import com.boger.game.gc.model.PersonalHomePageModel;
import com.boger.game.gc.ui.activity.PersonalHomePageActivity;

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
