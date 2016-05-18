package com.example.administrator.gc.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.gc.R;
import com.example.administrator.gc.base.BaseActivity;
import com.example.administrator.gc.model.PersonalHomePageModel;
import com.example.administrator.gc.presenter.activity.PersonalHomePagePresenter;
import com.example.administrator.gc.utils.PicassoUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * Created by Administrator on 2016/4/14.
 */
public class PersonalHomePageActivity extends BaseActivity {

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
    protected void initView() {
        setContentView(R.layout.activity_personal_home_page);
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

    View.OnClickListener listener = new View.OnClickListener() {
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
        PicassoUtils.normalShowImage(this, model.getAvatarSrc(), userPhotoImageView);
        final ImageView imageView = new ImageView(this);
        Picasso.with(this).load(model.getBgSrc()).into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                wrapper.setBackground(imageView.getDrawable());
            }

            @Override
            public void onError() {

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
