package com.example.administrator.gc.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.gc.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/26.
 */
public class LoadingView extends ViewGroup {

    private static final String TAG = "LoadingView";

    private static final int DEFAULT_COUNT = 4;

    private int moveSpeed = 100;

    private ObjectAnimator moveAnim;
    private ObjectAnimator alphaAnim;
    private AnimatorSet set;

    private List<Integer> roadList = new ArrayList<>();
    private int i = 1;
    private int pointCount;

    private ImageView moveView;
    private WeakReference<Context> weakContext;

    private String loadingText = "正在加载...";

    private boolean isAnim = false;

    private static Handler handler = new Handler();

    public LoadingView(Context context) {
        super(context);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        weakContext = new WeakReference<Context>(context);
        setWillNotDraw(false);
        init();
    }

    private void init() {
        setPointCount(DEFAULT_COUNT);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
            childView.measure(MeasureSpec.makeMeasureSpec(childView.getMeasuredWidth(), MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(childView.getMeasuredHeight(), MeasureSpec.EXACTLY));
        }

        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    private ImageView makeImg() {
        ImageView imageView = new ImageView(weakContext.get());
        Drawable drawable = getResources().getDrawable(R.drawable.ic_icon_bg);
        imageView.setImageDrawable(drawable);
        return imageView;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int width = getWidth();
        int itemMargin = width / pointCount;
        roadList.clear();
        Log.d("LoadingView", "contain view size：" + getChildCount());

        for (int i = 0; i < getChildCount() - 1; i++) {
            View view = getChildAt(i);
            view.layout(itemMargin * i + 40, getHeight() / 2 - view.getMeasuredHeight() / 2, itemMargin * i + view.getMeasuredWidth() + 40, view.getMeasuredHeight() / 2 + getHeight() / 2);
            roadList.add(itemMargin * i + view.getMeasuredWidth() / 2 + 40);
        }
        View view = getChildAt(getChildCount() - 1);
        view.layout(getWidth() / 2 - view.getMeasuredWidth() / 2, getHeight() / 2 + getChildAt(0).getMeasuredHeight(), getWidth() / 2 + view.getMeasuredWidth() / 2, getHeight() / 2 + getChildAt(0).getMeasuredHeight() + view.getMeasuredHeight());
    }

    public void setPointCount(int count) {
        this.pointCount = count;
        moveView = new ImageView(this.weakContext.get());
        moveView.setImageResource(R.mipmap.ic_icon);
        moveView.setAlpha(1.0f / (pointCount + 1));
        ViewGroup.LayoutParams lp = new LayoutParams(dip2px(40), dip2px(40));
        this.addView(moveView, lp);
        createPoint(count);
    }

    private void createPoint(int count) {
        for (int i = 0; i < count; i++) {
            View view = new View(this.weakContext.get());
            view.setBackgroundResource(R.drawable.orange_circle);
            ViewGroup.LayoutParams lp = new LayoutParams(dip2px(8), dip2px(8));
            this.addView(view, lp);
        }

        TextView textView = new TextView(this.weakContext.get());
        textView.setText(loadingText);
        ViewGroup.LayoutParams lp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        this.addView(textView, lp);
    }


    Runnable ru = new Runnable() {
        @Override
        public void run() {
            start();
            removeCallbacks(this);
            handler.postDelayed(ru, moveSpeed * (getChildCount() - 2));
        }
    };

    public void start() {
        if (isAnim) {
            return;
        }

        if (i < roadList.size()) {
            isAnim = true;
        } else {
            isAnim = false;
        }
        startLoading();
        handler.removeCallbacks(ru);
        handler.postDelayed(ru, moveSpeed * (getChildCount() - 2));
    }

    @Override
    public void setVisibility(int visibility) {
        if (visibility == GONE) {
            handler.removeCallbacks(ru);
        }
        super.setVisibility(visibility);

    }

    private void startLoading() {
        final View moveView = getChildAt(0);
        if (i < roadList.size()) {
            moveAnim = ObjectAnimator.ofFloat(
                    moveView,
                    View.TRANSLATION_X,
                    i == 1 ? moveView.getLeft() : roadList.get(i - 1) - moveView.getMeasuredWidth() / 2,
                    i == roadList.size() - 1 ? roadList.get(i) : roadList.get(i) - moveView.getMeasuredWidth() / 2);

            moveAnim.setDuration(moveSpeed);
            moveAnim.start();

            moveAnim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    if (i < getChildCount() - 1) {
                        View vi = getChildAt(i);

                        alphaAnim = ObjectAnimator.ofFloat(vi, "alpha", 1f, 0f);
                        alphaAnim.setDuration(0);
                        alphaAnim.start();
                        ObjectAnimator.ofFloat(moveView, View.ALPHA, moveView.getAlpha(), moveView.getAlpha() + (float) (1f / (getChildCount() - 1) * i))
                                .setDuration(0)
                                .start();
                        i++;
                        startLoading();
                    }
                }
            });
        } else {
            i = 1;
            isAnim = false;
            set = new AnimatorSet();
            set.playSequentially(
                    ObjectAnimator.ofFloat(moveView, View.ALPHA, 1f, 0f)
                    , ObjectAnimator.ofFloat(moveView, View.TRANSLATION_X, 0, moveView.getLeft())
                    , ObjectAnimator.ofFloat(moveView, View.ALPHA, 0f, 1f / (getChildCount() - 1)));
            set.setDuration(0);
            set.start();

            for (int i = 1; i < getChildCount() - 1; i++) {
                getChildAt(i).setAlpha(1f);
            }
        }
    }

    public String getLoadingText() {
        return loadingText;
    }

    public void setLoadingText(String loadingText) {
        this.loadingText = loadingText;
        invalidate();
    }

    public int getMoveSpeed() {
        return moveSpeed;
    }

    public void setMoveSpeed(int moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    private int dip2px(int value) {
        return (int) (value * getResources().getDisplayMetrics().density + 0.5f);
    }

    public void stopAnim() {
        isAnim = false;
        if (moveAnim != null && moveAnim.isRunning()) {
            moveAnim.cancel();
        }
        if (alphaAnim != null && alphaAnim.isRunning()) {
            alphaAnim.cancel();
        }

        if (set != null && set.isRunning()) {
            set.cancel();
        }
    }
}
