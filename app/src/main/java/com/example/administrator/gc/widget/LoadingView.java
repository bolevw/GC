package com.example.administrator.gc.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.administrator.gc.R;
import com.example.administrator.gc.base.BaseApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Administrator on 2016/3/26.
 */
public class LoadingView extends ViewGroup {

    private static final String TAG = "LoadingView";

    private static final int DEFAULT_COUNT = 4;

    private int pointCount;

    private ImageView moveView;

    private Context mContext;
    AnimatorSet set;

    private List<Integer> roadList = new ArrayList<>();

    public LoadingView(Context context) {
        super(context);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = BaseApplication.getContext();
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

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int width = getWidth();
        int itemMargin = width / pointCount;

        Log.d("LoadingView", "contain view size：" + getChildCount());

        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            view.layout(itemMargin * i, getHeight() / 2 - view.getMeasuredHeight() / 2, itemMargin * i + view.getMeasuredWidth(), view.getMeasuredHeight() / 2 + getHeight() / 2);
            roadList.add(itemMargin * i + view.getMeasuredWidth() / 2);
            Log.d(TAG, "view left:" +
                    view.getLeft() + "right:" +
                    view.getRight() + " top:" +
                    view.getTop() + "bottom:" +
                    view.getBottom());
        }
    }

    public void setPointCount(int count) {
        this.pointCount = count;
        moveView = new ImageView(this.mContext);
        moveView.setImageResource(R.mipmap.ic_icon);
        moveView.setAlpha(1.0f / (pointCount + 1));
        ViewGroup.LayoutParams lp = new LayoutParams(dip2px(40), dip2px(40));
        this.addView(moveView, lp);
        createPoint(count);
    }

    private void createPoint(int count) {
        for (int i = 0; i < count; i++) {
            View view = new View(this.mContext);
            view.setBackgroundResource(R.drawable.orange_circle);
            ViewGroup.LayoutParams lp = new LayoutParams(dip2px(8), dip2px(8));
            this.addView(view, lp);
        }
    }

    public void startLoading() {
        set = new AnimatorSet();
        for (int i = 0; i < roadList.size(); i++) {
            final int finalI = i;
            Observable<Integer> observable = Observable.create(new Observable.OnSubscribe<Integer>() {
                @Override
                public void call(Subscriber<? super Integer> subscriber) {
                    subscriber.onNext(finalI);
                    subscriber.onCompleted();
                }
            });

            observable.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .debounce(400, TimeUnit.MILLISECONDS)
                    .subscribe(new Subscriber<Integer>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(Integer integer) {
                            Log.d(TAG, "i " + integer);
                            final int i = integer;
                            final ObjectAnimator moveAnim = ObjectAnimator.ofFloat(getChildAt(0), View.TRANSLATION_X, getChildAt(0).getLeft(), roadList.get(1));
                            moveAnim.addListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);

                                    ObjectAnimator.ofFloat(getChildAt(0), View.TRANSLATION_X, getChildAt(0).getLeft(), roadList.get(i)).setDuration(300).start();
                                }
                            });
                            View vi = null;
                            if (i > 0 && i < getChildCount()) {
                                vi = getChildAt(i);

                                ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(vi, "alpha", 1f, 0f);
                                set.playSequentially(moveAnim, alphaAnim);
                                set.setDuration(3000);
                                set.start();
                            } else {
                                set.play(moveAnim);
                                set.setDuration(3000);
                                set.start();
                            }

                        }
                    });
        }

    }

    private int dip2px(int value) {
        return (int) (value * getResources().getDisplayMetrics().density + 0.5f);
    }
}
