package com.boger.game.gc.presenter.activity;

import com.boger.game.gc.api.AccountApi;
import com.boger.game.gc.base.ActivityPresenter;
import com.boger.game.gc.base.ApiCallBack;
import com.boger.game.gc.model.PersonalHomePageModel;
import com.boger.game.gc.ui.activity.PersonalHomePageActivity;

/**
 * Created by Administrator on 2016/4/14.
 */
public class PersonalHomePagePresenter extends ActivityPresenter<PersonalHomePageActivity> {
    public PersonalHomePagePresenter(PersonalHomePageActivity view) {
        super(view);
    }

    public void getData(String url) {
        AccountApi.getHomePage(url, new ApiCallBack<PersonalHomePageModel>(composite) {
            @Override
            protected void onSuccess(PersonalHomePageModel s) {
                view.viewBindData(s);

            }

            @Override
            protected void onFail(Throwable e) {

            }
        });

    }
}
