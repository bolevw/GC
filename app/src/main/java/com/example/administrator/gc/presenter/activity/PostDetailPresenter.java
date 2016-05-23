package com.example.administrator.gc.presenter.activity;

import com.example.administrator.gc.api.ForumApi;
import com.example.administrator.gc.base.BasePresenter;
import com.example.administrator.gc.base.BaseSub;
import com.example.administrator.gc.model.FollowPostModel;
import com.example.administrator.gc.model.FollowResponse;
import com.example.administrator.gc.model.IsFollowModel;
import com.example.administrator.gc.model.IsFollowResponse;
import com.example.administrator.gc.model.PostBodyModel;
import com.example.administrator.gc.restApi.ForumAndPostApi;
import com.example.administrator.gc.ui.activity.PostDetailActivity;

/**
 * Created by Administrator on 2016/4/8.
 */
public class PostDetailPresenter implements BasePresenter<PostDetailActivity> {
    private PostDetailActivity view;
    private String nextPage;
    private boolean hasData = true;

    @Override
    public void bind(PostDetailActivity view) {
        this.view = view;
    }

    public void getData(String urls, final boolean getNextpage) {
        if (view != null) {
            view.setLoading(true);
            view.startLoading();
        }

        ForumApi.getPostDetail(urls, getNextpage, new BaseSub<PostBodyModel, PostDetailActivity>(view) {
            @Override
            protected void error(String e) {
                view.stopLoading();
                view.setLoading(false);
            }

            @Override
            protected void next(PostBodyModel s) {
                view.stopLoading();
                view.setLoading(false);
                view.notifyChange(s, getNextpage);
                nextPage = s.getNextPageUrl();
            }
        });
    }

    public void getNextPage() {
        if (nextPage.contains("javascript:void")) {
            view.notifyNoData();
            setHasData(false);
            return;
        } else {
            getData(nextPage, true);
            setHasData(true);
        }
    }

    public void followPost(FollowPostModel model) {
        ForumAndPostApi.followPost(model, new BaseSub<FollowResponse, PostDetailActivity>(view) {
            @Override
            protected void error(String e) {
                view.showWarning("关注失败！");
            }

            @Override
            protected void next(FollowResponse response) {
                view.followSuccess();
                view.setObjectId(response.getObjectId());
            }
        });
    }

    @Override
    public void unBind() {
        this.view = null;
    }

    public void isFollow(IsFollowModel model) {
        if (!view.isLogin()) {
            return;
        } else {

            ForumAndPostApi.isFollow(model, new BaseSub<IsFollowResponse, PostDetailActivity>(view) {
                @Override
                protected void error(String e) {
                }

                @Override
                protected void next(IsFollowResponse list) {
                    if (list.getResults().size() > 0) {
                        view.setObjectId(list.getResults().get(0).getObjectId());
                        view.setFollow(true);
                    }

                }
            });
        }
    }

    public void cancelFollow(String objectId) {
        ForumAndPostApi.cancelFollow(objectId, new BaseSub<Void, PostDetailActivity>(view) {
            @Override
            protected void error(String e) {

            }

            @Override
            protected void next(Void aVoid) {
                view.setFollow(false);
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
