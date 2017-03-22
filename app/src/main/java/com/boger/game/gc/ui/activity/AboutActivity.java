package com.boger.game.gc.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.boger.game.gc.R;
import com.boger.game.gc.base.BaseActivity;
import com.boger.game.gc.widget.NineGridVIewGroup;

/**
 * Created by Administrator on 2016/4/6.
 */
public class AboutActivity extends BaseActivity {
    NineGridVIewGroup nine;

    private RecyclerView list;


    @Override
    protected void initView() {
        setContentView(R.layout.activity_about);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int b = dm.widthPixels;
        int a = dm.heightPixels;
        list = (RecyclerView) findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new Vh(LayoutInflater.from(parent.getContext()).inflate(R.layout.test_b, parent, false));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            }

            @Override
            public int getItemCount() {
                return 40;
            }

            class Vh extends RecyclerView.ViewHolder {
                private Button b;

                public Vh(View itemView) {
                    super(itemView);
                    b = (Button) itemView.findViewById(R.id.b);
                }
            }
        });
       /* nine = (NineGridVIewGroup) findViewById(R.id.nine);
        List<String> urls = new ArrayList<>(9);
        int i = 0;
        while (i < 8) {
            urls.add(new String("https://oixgx79lw.qnssl.com/FuaI7iVBiu5cilAaMuKbqJEYRFEU"));
            i++;
        }
        nine.setViewData(urls);
        nine.setItemClick(new NineGridVIewGroup.onItemClick() {
            @Override
            public void onItemClick(String url) {
                ToastUtils.showNormalToast(url);
            }
        });*/
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void bind() {

    }

    @Override
    protected void unBind() {

    }

    @Override
    protected boolean isSupportSwipeBack() {
        return false;
    }
}
