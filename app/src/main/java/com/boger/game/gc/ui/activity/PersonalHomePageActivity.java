package com.boger.game.gc.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boger.game.gc.R;
import com.boger.game.gc.base.BaseSwipeBackActivity;
import com.boger.game.gc.model.PersonalHomePageModel;
import com.boger.game.gc.presenter.activity.PersonalHomePagePresenter;
import com.boger.game.gc.utils.ImageLoad;
import com.boger.game.gc.utils.ImageLoaderUtils;

/**
 * Created by Administrator on 2016/4/14.
 */
public class PersonalHomePageActivity extends BaseSwipeBackActivity {

    private PersonalHomePagePresenter presenter;

    private String url;

    private TextView usernameTextView;
    private TextView userLevelTextView;
    private TextView moneyTextView;
    private TextView prestigeTextView;
    private TextView grassTextView;

    private ImageView userPhotoImageView;

    private LinearLayout hisThemeLinearLayout;
    private LinearLayout hisFriendLinearLayout;
    private LinearLayout wrapper;

    public static void newInstance(Activity activity, String url) {
        Intent intent = new Intent(activity, PersonalHomePageActivity.class);
        intent.putExtra("url", url);
        activity.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_personal_home_page;
    }

    @Override
    protected void initViewData() {
        url = getIntent().getStringExtra("url");

        usernameTextView = (TextView) findViewById(R.id.usernameTextView);
        userLevelTextView = (TextView) findViewById(R.id.userLevelTextView);
        moneyTextView = (TextView) findViewById(R.id.moneyTextView);
        prestigeTextView = (TextView) findViewById(R.id.prestigeTextView);
        grassTextView = (TextView) findViewById(R.id.grassTextView);

        userPhotoImageView = (ImageView) findViewById(R.id.userPhotoImageView);

        hisThemeLinearLayout = (LinearLayout) findViewById(R.id.hisThemeLinearLayout);
        hisFriendLinearLayout = (LinearLayout) findViewById(R.id.hisFriendLinearLayout);
        wrapper = (LinearLayout) findViewById(R.id.wrapper);

    }

    @Override
    protected void setListener() {
        hisFriendLinearLayout.setOnClickListener(listener);
        hisThemeLinearLayout.setOnClickListener(listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            String url = (String) v.getTag();
            if (id == hisFriendLinearLayout.getId()) {
            }

            if (id == hisThemeLinearLayout.getId()) {

            }
        }
    };

    @Override
    protected void bind() {
        this.presenter = new PersonalHomePagePresenter();
        this.presenter.bind(this);
        this.presenter.getData(url);
    }

    public void viewBindData(PersonalHomePageModel model) {
        usernameTextView.setText(model.getUsername());
        userLevelTextView.setText(model.getUserLevel());
        moneyTextView.setText(model.getMoney());
        prestigeTextView.setText(model.getPrestige());
        grassTextView.setText(model.getGrass());
        ImageLoaderUtils.load(model.getAvatarSrc(), userPhotoImageView);
        final ImageView imageView = new ImageView(this);
        ImageLoaderUtils.load(model.getBgSrc(), new ImageLoad.ImageLoadCallback() {
            @Override
            public void loadSuccess(Drawable resource) {
                wrapper.setBackground(resource);
            }

            @Override
            public void loadFail(Exception e) {
                Log.d(TAG, "loadFail() called with: e = [" + e + "]");
            }
        });
        hisFriendLinearLayout.setTag(model.getFriendUrl());
        hisThemeLinearLayout.setTag(model.getThemeUrl());
    }

    @Override
    protected void unBind() {
        this.presenter.unBind();
    }
}
