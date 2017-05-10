package com.boger.game.gc.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;

import com.boger.game.gc.R;

/**
 * Created by liubo on 16/8/2.
 */

public class CommentDialog extends Dialog {

    public CommentDialog(Context context, final OnItemClickListener listener) {
        super(context, R.style.Fullscreen_Dialog);
        setContentView(R.layout.dialog_comment);


        final EditText commentEdt = (EditText) findViewById(R.id.commentEdt);
        commentEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        final AppCompatButton p = (AppCompatButton) findViewById(R.id.postBtn);

        p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(commentEdt.getText().toString().trim());
            }
        });


        getWindow().getAttributes().gravity = Gravity.BOTTOM;

    }


    public interface OnItemClickListener {
        void onClick(String comment);
    }
}
