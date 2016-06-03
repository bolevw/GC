package com.example.administrator.gc.presenter.fragment;

import com.example.administrator.gc.base.BasePresenter;
import com.example.administrator.gc.base.BaseSub;
import com.example.administrator.gc.model.GetFollowPostResponse;
import com.example.administrator.gc.restApi.ForumAndPostApi;
import com.example.administrator.gc.ui.fragment.childfragment.AttentionPostFragment;

/**
 * Created by liubo on 2016/5/19.
 */
public class AttentionPostPresenter implements BasePresenter<AttentionPostFragment> {

    AttentionPostFragment view;

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
