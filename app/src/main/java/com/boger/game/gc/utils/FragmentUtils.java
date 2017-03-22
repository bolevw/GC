package com.boger.game.gc.utils;

import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;

import com.boger.game.gc.base.BaseFragment;

/**
 * Created by Administrator on 2016/3/22.
 */
public class FragmentUtils {

    public static void replaceFragment(FragmentManager manager, @IdRes int container, BaseFragment fragment, boolean addBackToStack, String tag) {
        if (addBackToStack) {
            manager.beginTransaction().addToBackStack(tag).replace(container, fragment, tag).commit();
        } else {
            manager.beginTransaction().replace(container, fragment, tag).commit();
        }
    }


}
