package com.example.administrator.gc.ui.activity;

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

import com.example.administrator.gc.R;
import com.example.administrator.gc.base.BaseActivity;
import com.example.administrator.gc.restApi.DownLoad;
import com.example.administrator.gc.utils.PicassoUtils;
import com.example.administrator.gc.utils.ToastUtils;
import com.squareup.picasso.Callback;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

/**
 * Created by liubo on 2016/5/20.
 */
public class PhotoActivity extends BaseActivity {

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
        activity.startActivity(intent);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_photo);
        ButterKnife.bind(this);

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
        PicassoUtils.normalShowImage(this, urls.get(startPosition), imageView, new Callback() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                ToastUtils.showNormalToast("参数错误!");
                PhotoActivity.this.finish();
            }
        });

        imageView.setOnTouchListener(onTouchListener);
    }

    @OnClick(R.id.saveButton)
    void saveImage() {
        DownLoad.downLoadImage(urls.get(startPosition), new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                ToastUtils.showNormalToast("图片保存成功" + s);
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
                case MotionEvent.ACTION_MOVE:
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
                case MotionEvent.ACTION_UP:
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
        PicassoUtils.normalShowImage(urls.get(startPosition), imageView, new Callback() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                ToastUtils.showNormalToast("参数错误!");
                PhotoActivity.this.finish();
            }
        });

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
        PicassoUtils.normalShowImage(this, urls.get(startPosition), imageView, new Callback() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                ToastUtils.showNormalToast("参数错误!");
                PhotoActivity.this.finish();
            }
        });
    }

    @Override
    protected void bind() {

    }

    @Override
    protected void unBind() {

    }

    @Override
    protected boolean isSupportSwipeBack() {
        return finish;
    }
}
