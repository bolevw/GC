package com.boger.game.gc.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.boger.game.gc.R;
import com.boger.game.gc.base.ApiCallBack;
import com.boger.game.gc.base.BaseSwipeBackActivity;
import com.boger.game.gc.restApi.DownLoad;
import com.boger.game.gc.utils.ImageLoad;
import com.boger.game.gc.utils.ImageLoaderUtils;
import com.boger.game.gc.utils.ToastUtils;
import com.umeng.analytics.MobclickAgent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by liubo on 2016/5/20.
 */
public class PhotoActivity extends BaseSwipeBackActivity {

    private static final String TAG = "PhotoActivity";
    private static final int MOVE_DISTANCE = 50;

    @BindView(R.id.backImageView)
    ImageButton backImageView;
    @BindView(R.id.saveButton)
    Button saveButton;
    @BindView(R.id.picImageView)
    ImageView imageView;
    @BindView(R.id.progressBar)
    ContentLoadingProgressBar progressBar;

    private VelocityTracker velocityTracker;

    private boolean finish = false;

    private int startX;
    private int startY;
    private int startPosition;
    private List<String> urls = new ArrayList<>();

    public static void newInstance(Activity activity, int startPosition, List<String> urls) {
        Intent intent = new Intent(activity, PhotoActivity.class);
        intent.putExtra("startPosition", startPosition);
        intent.putExtra("urls", (Serializable) urls);
        Log.e(TAG, "newInstance: urls " + urls.toString());
        activity.startActivity(intent);
    }

    @Override
    protected void initViewData() {
        velocityTracker = VelocityTracker.obtain();

        Intent intent = getIntent();
        startPosition = intent.getIntExtra("startPosition", 0);
        urls = (List<String>) intent.getSerializableExtra("urls");
        if (urls.size() == 0) {
            ToastUtils.showNormalToast("参数错误!");
            this.finish();
        }
    }

    @Override
    protected void setListener() {
        loadImage();

        imageView.setOnTouchListener(onTouchListener);
    }

    private void loadImage() {
        ImageLoaderUtils.load(urls.get(startPosition), new ImageLoad.ImageLoadCallback() {
            @Override
            public void loadSuccess(Drawable resource) {
                progressBar.setVisibility(View.GONE);
                imageView.setBackground(resource);
            }

            @Override
            public void loadFail(Exception e) {
                Log.d(TAG, "loadFail() called with: e = [" + e + "]");
                ToastUtils.showNormalToast("参数错误!");
                PhotoActivity.this.finish();
            }
        });
    }

    @OnClick(R.id.saveButton)
    void saveImage() {
        DownLoad.downLoadImage(PhotoActivity.this, urls.get(startPosition), new ApiCallBack<String>(new CompositeDisposable()) {
            @Override
            protected void onSuccess(String s) {
                ToastUtils.showNormalToast("图片保存成功" + s);

            }

            @Override
            protected void onFail(Throwable e) {
            }
        });
    }

    @OnClick(R.id.backImageView)
    void back() {
        onBackPressed();
    }

    View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            velocityTracker.addMovement(event);
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    startX = (int) event.getX();
                    startY = (int) event.getY();
                    return true;
                case MotionEvent.ACTION_UP:
                    int moveX = (int) event.getX() - startX;
                    int moveY = (int) event.getY() - startY;

                    velocityTracker.computeCurrentVelocity(1000);
                    float xVelocity = velocityTracker.getXVelocity();
                    Log.d("move", "x: " + xVelocity + " y:" + moveY);
                    if (xVelocity > 50 && moveX > 80) {
                        Log.d("photoMove", "move to left");
                        move2Left();
                    } else if (xVelocity < -50 && moveX < -80) {
                        Log.d("photoMove", "move to right");
                        move2Right();
                    }

                    startX = (int) event.getX();
                    startY = (int) event.getY();
                    break;
            }
            return true;
        }
    };

    private void move2Right() {
        if (startPosition == urls.size() - 1) {
            ToastUtils.showNormalToast("已经是最后一张了！");
            return;
        }

        recycleBitmap();

        startPosition++;
        progressBar.setVisibility(View.VISIBLE);
        loadImage();

    }

    private void recycleBitmap() {
        Drawable drawable = imageView.getDrawable();
        if (drawable != null && drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            Bitmap bitmap = bitmapDrawable.getBitmap();
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }
    }

    private void move2Left() {
        if (startPosition == 0) {
            ToastUtils.showNormalToast("已经是第一张了！");
            return;
        }

        recycleBitmap();
        startPosition--;

        progressBar.setVisibility(View.VISIBLE);
        loadImage();
    }

    @Override
    protected void bind() {

    }

    @Override
    protected boolean isSupportSwipeBack() {
        return finish;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_photo;
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        MobclickAgent.onPageStart(this.getClass().getSimpleName());
    }
}
