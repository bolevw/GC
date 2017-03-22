package com.boger.game.gc.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boger.game.gc.R;

/**
 * Created by Administrator on 2016/4/26.
 */
public class LoadingFailView extends LinearLayout {

    private Button reloadButton;
    private ImageView loadFailImageView;
    private TextView loadFailTextView;

    private ReloadClickListener reloadClickListener;


    public ReloadClickListener getReloadClickListener() {
        return reloadClickListener;
    }

    public void setReloadClickListener(ReloadClickListener reloadClickListener) {
        this.reloadClickListener = reloadClickListener;
    }

    public LoadingFailView(Context context) {
        super(context);
    }

    public LoadingFailView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    private void initView(Context c, AttributeSet attrs) {
        View view = LayoutInflater.from(c).inflate(R.layout.layout_loading_fail, this, true);

        reloadButton = (Button) view.findViewById(R.id.reLoadButton);
        loadFailTextView = (TextView) view.findViewById(R.id.loadFailTextView);

        reloadButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (reloadClickListener != null) {
                    reloadClickListener.onReload();
                }
            }
        });
    }

    public void setLoadFailText(String text) {
        loadFailTextView.setText(text);
        invalidate();
    }

    public interface ReloadClickListener {
        void onReload();
    }
}
