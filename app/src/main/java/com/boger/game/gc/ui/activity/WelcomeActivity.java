package com.boger.game.gc.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.boger.game.gc.R;
import com.boger.game.gc.base.BaseActivity;

import butterknife.BindView;


public class WelcomeActivity extends BaseActivity {
    @BindView(R.id.iconIv)
    AppCompatImageView iconIv;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initViewData() {

        ObjectAnimator oba = ObjectAnimator.ofFloat(iconIv, View.ALPHA, 0f, 1f);
        oba.setStartDelay(300);
        oba.setDuration(1000);
        oba.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                finish();
            }
        });
        oba.start();
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void bind() {

    }
}
