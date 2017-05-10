package com.boger.game.gc.presenter.fragment;

import com.boger.game.gc.base.ApiCallBack;
import com.boger.game.gc.base.FragmentPresenter;
import com.boger.game.gc.model.GetFollowPostResponse;
import com.boger.game.gc.restApi.ForumAndPostApi;
import com.boger.game.gc.ui.fragment.childfragment.AttentionPostFragment;

/**
 * Created by liubo on 2016/5/19.
 */
public class AttentionPostPresenter extends FragmentPresenter<AttentionPostFragment> {

    public AttentionPostPresenter(AttentionPostFragment view) {
        super(view);
    }

    public void getData(String userId) {
        ForumAndPostApi.getFollowPost(userId, new ApiCallBack<GetFollowPostResponse>(composite) {
            @Override
            protected void onSuccess(GetFollowPostResponse data) {
                view.setViewData(data.getResults());

            }

            @Override
            protected void onFail(Throwable e) {

            }
        });
    }
}
