package com.example.administrator.gc.presenter.fragment;

import com.example.administrator.gc.base.BasePresenter;
import com.example.administrator.gc.base.BaseSub;
import com.example.administrator.gc.model.FollowPostModel;
import com.example.administrator.gc.restApi.ForumAndPostApi;
import com.example.administrator.gc.ui.fragment.childfragment.AttentionPostFragment;

import java.util.List;

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
        ForumAndPostApi.getFollowPost(userId, new BaseSub<List<FollowPostModel>, AttentionPostFragment>(view) {
            @Override
            protected void error(String e) {

            }

            @Override
            protected void next(List<FollowPostModel> followPostModels) {

            }
        });
    }

    @Override
    public void unBind() {
        this.view = null;
    }
}
