package com.boger.game.gc.widget.bottomnav;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boger.game.gc.R;

/**
 * Created by liubo on 2016/3/21.
 */
public class BottomNavChild extends LinearLayout {

    private ImageView itemNavBottomImageView;
    private TextView itemNavBottomTextView;

    public static class Builder {
        private Context context;
        private String title;
        private Drawable imageDrawable;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setImageRes(@DrawableRes int resId) {
            imageDrawable = ContextCompat.getDrawable(context, resId);
            return this;
        }

        public Builder setImageRes(@Nullable Drawable drawable) {
            this.imageDrawable = drawable;
            return this;
        }

        public Builder setTitle(@StringRes int resId) {
            title = context.getResources().getString(resId);
            return this;
        }

        public Builder setTitle(CharSequence charSequence) {
            title = charSequence.toString();
            return this;
        }

        public BottomNavChild build() {
            BottomNavChild v2 = new BottomNavChild(context, title, imageDrawable);
            return v2;
        }
    }

    private BottomNavChild(Context context, String text, Drawable drawable) {
        this(context, null, text, drawable);
    }

    private BottomNavChild(Context context, AttributeSet attrs, String text, Drawable drawable) {
        super(context, attrs);

        View v = LayoutInflater.from(context).inflate(R.layout.item_bottom_nav, this, true);
        itemNavBottomTextView = (TextView) v.findViewById(R.id.itemNavBottomTextView);
        itemNavBottomImageView = (ImageView) v.findViewById(R.id.itemNavBottomImageView);
        itemNavBottomImageView.setImageDrawable(drawable);
        itemNavBottomTextView.setText(text);
    }

    public void setSelect(boolean select) {
        itemNavBottomImageView.setEnabled(!select);
    }
}
