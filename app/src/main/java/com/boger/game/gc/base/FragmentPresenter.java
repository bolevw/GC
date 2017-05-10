package com.boger.game.gc.base;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by liubo on 2017/5/9.
 */

public abstract class FragmentPresenter<T extends BaseFragment> {
    protected T view;
    protected CompositeDisposable composite;

    public FragmentPresenter(T view) {
        this.view = view;
        composite = new CompositeDisposable();

        this.view.setOnDestroyListener(new OnDestroyListener() {
            @Override
            public void onDestroy() {
                destroy();
            }
        });
    }

    protected void destroy() {
        view = null;
        if (composite != null && !composite.isDisposed()) {
            composite.dispose();
        }
    }
}
