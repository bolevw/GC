package com.boger.game.gc.presenter.activity;

import com.boger.game.gc.api.ForumApi;
import com.boger.game.gc.base.ActivityPresenter;
import com.boger.game.gc.base.ApiCallBack;
import com.boger.game.gc.model.FollowPostModel;
import com.boger.game.gc.model.FollowResponse;
import com.boger.game.gc.model.IsFollowModel;
import com.boger.game.gc.model.IsFollowResponse;
import com.boger.game.gc.model.PostBodyModel;
import com.boger.game.gc.restApi.ForumAndPostApi;
import com.boger.game.gc.ui.activity.PostDetailActivity;

/**
 * Created by Administrator on 2016/4/8.
 */
public class PostDetailPresenter extends ActivityPresenter<PostDetailActivity> {
    private String nextPage;
    private boolean hasData = true;

    public PostDetailPresenter(PostDetailActivity view) {
        super(view);
    }

    public void getData(String urls, final boolean getNextPage) {
        if (view != null) {
            view.setLoading(true);
            view.startLoading();
        }

        ForumApi.getPostDetail(urls, getNextPage, new ApiCallBack<PostBodyModel>(composite) {
            @Override
            protected void onSuccess(PostBodyModel s) {
                view.stopLoading();
                view.setLoading(false);
                view.notifyChange(s, getNextPage);
                nextPage = s.getNextPageUrl();
            }

            @Override
            protected void onFail(Throwable e) {
                view.stopLoading();
                view.setLoading(false);
            }

        });
    }

    public void getNextPage() {
        if (nextPage.contains("javascript:void")) {
            view.notifyNoData();
            setHasData(false);
        } else {
            getData(nextPage, true);
            setHasData(true);
        }
    }

    public void followPost(FollowPostModel model) {
        ForumAndPostApi.followPost(model, new ApiCallBack<FollowResponse>(composite) {
            @Override
            protected void onSuccess(FollowResponse response) {
                view.followSuccess();
                view.setObjectId(response.getObjectId());
            }

            @Override
            protected void onFail(Throwable e) {
                view.showWarning("关注失败！");

            }
        });
    }


    public void isFollow(IsFollowModel model) {
        if (!view.isLogin()) {
        } else {

            ForumAndPostApi.isFollow(model, new ApiCallBack<IsFollowResponse>(composite) {
                @Override
                protected void onSuccess(IsFollowResponse list) {
                    if (list.getResults().size() > 0) {
                        view.setObjectId(list.getResults().get(0).getObjectId());
                        view.setFollow(true);
                    }
                }

                @Override
                protected void onFail(Throwable e) {

                }
            });
        }
    }

    public void cancelFollow(String objectId) {
        ForumAndPostApi.cancelFollow(objectId, new ApiCallBack<Void>(composite) {
            @Override
            protected void onSuccess(Void data) {
                view.setFollow(false);
            }

            @Override
            protected void onFail(Throwable e) {

            }

        });
    }

    public boolean isHasData() {
        return hasData;
    }

    public void setHasData(boolean hasData) {
        this.hasData = hasData;
    }
}
