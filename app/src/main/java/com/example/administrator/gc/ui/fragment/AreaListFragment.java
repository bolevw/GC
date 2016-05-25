package com.example.administrator.gc.ui.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.gc.R;
import com.example.administrator.gc.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by liubo on 2016/5/24.
 */
public class AreaListFragment extends BaseFragment {

    @BindView(R.id.rootView)
    RelativeLayout rootView;
    @BindView(R.id.panelView)
    LinearLayout panelView;
    @BindView(R.id.areaRecyclerView)
    RecyclerView areaRecyclerView;

    @OnClick(R.id.rootView)
    void dismiss() {
        close();
    }

    private String[] areas = new String[]{
            "艾欧尼亚", "祖安", "诺克萨斯", "班德尔城", "皮尔特沃夫", "战争学院", "巨神峰", "雷瑟守备", "裁决之地", "黑色玫瑰",
            "暗影岛", "钢铁烈阳", "均衡教派", "水晶之痕", "影流", "守望之海", "征服之海", "卡拉曼达", "皮城警备", "比尔吉沃特",
            "德玛西亚", "弗雷尔卓德", "无畏先锋", "恕瑞玛", "扭曲丛林", "巨龙之巢", "教育专区"
    };

    public static AreaListFragment newInstance() {

        Bundle args = new Bundle();

        AreaListFragment fragment = new AreaListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_area_list, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    protected void initView(View v) {
        panelView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                panelView.getViewTreeObserver().removeOnPreDrawListener(this);
                int middle = (panelView.getBottom() - panelView.getTop()) / 2;
                int top = panelView.getTop();
                int bottom = panelView.getBottom();
                AnimatorSet set = new AnimatorSet();
                set.playTogether(ObjectAnimator.ofInt(panelView, "top", middle, top),
                        ObjectAnimator.ofInt(panelView, "bottom", middle, bottom)
                );
                set.setDuration(300);
                set.start();
                return false;
            }
        });
    }

    @Override
    protected void bind() {

    }

    private void close() {
        int top = panelView.getTop();
        int bottom = panelView.getBottom();
        int middle = (bottom - top) / 2;
        AnimatorSet set = new AnimatorSet();
        set.playTogether(ObjectAnimator.ofInt(panelView, "top", top, middle),
                ObjectAnimator.ofInt(panelView, "bottom", bottom, middle),
                ObjectAnimator.ofFloat(panelView, View.ALPHA, 1f, 0f));
        set.setDuration(300);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                getFragmentManager().popBackStack();
            }
        });
        set.start();
    }

    @Override
    protected void setListener() {
        areaRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseActivity()));
        areaRecyclerView.setAdapter(new RVAdapter());
    }

    @Override
    protected void unbind() {

    }

    private class RVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(getBaseActivity()).inflate(R.layout.item_area, parent, false);
            return new ItemVh(v);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((ItemVh) holder).areaTextView.setText(areas[position]);
        }

        @Override
        public int getItemCount() {
            return areas.length;
        }

        private class ItemVh extends RecyclerView.ViewHolder {
            private TextView areaTextView;

            public ItemVh(View itemView) {
                super(itemView);
                areaTextView = (TextView) itemView.findViewById(R.id.areaTextView);
            }
        }
    }
}
