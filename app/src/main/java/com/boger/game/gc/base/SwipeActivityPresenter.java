package com.boger.game.gc.base;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Administrator on 2016/3/21.
 */
public abstract class ActivityPresenter<T extends BaseActivity> {
    protected T view;
    protected CompositeDisposable composite;

    public ActivityPresenter(T view) {
        this.view = view;
        composite = new CompositeDisposable();

        this.view.setOnDestroyListener(new OnDestroyListener() {
            @Override
            public void onDestroy() {
                destroy();
            }
        });
    }

    private void destroy() {
        view = null;
        if (composite != null && !composite.isDisposed()) {
            composite.dispose();
        }
    }
}
