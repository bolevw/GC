package com.boger.game.gc.presenter.fragment;

import com.boger.game.gc.base.BasePresenter;
import com.boger.game.gc.base.BaseSub;
import com.boger.game.gc.model.GetFollowPostResponse;
import com.boger.game.gc.restApi.ForumAndPostApi;
import com.boger.game.gc.ui.fragment.childfragment.AttentionPostFragment;

/**
 * Created by liubo on 2016/5/19.
 */
public class AttentionPostPresenter implements BasePresenter<AttentionPostFragment> {

    private AttentionPostFragment view;

    @Override
    public void bind(AttentionPostFragment view) {
        this.view = view;
    }

    public void getData(String userId) {
        ForumAndPostApi.getFollowPost(userId, new BaseSub<GetFollowPostResponse, AttentionPostFragment>(view) {
            @Override
            protected void error(String e) {

            }

            @Override
            protected void next(GetFollowPostResponse followPostModels) {
                view.setViewData(followPostModels.getResults());
            }
        });
    }

    @Override
    public void unBind() {
        this.view = null;
    }
}
