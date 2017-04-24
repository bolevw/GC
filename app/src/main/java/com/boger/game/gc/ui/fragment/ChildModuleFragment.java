package com.boger.game.gc.ui.fragment;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.boger.game.gc.R;
import com.boger.game.gc.base.BaseFragment;
import com.boger.game.gc.model.ChildrenModuleCoverModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by liubo on 2017/4/24.
 */
public class ChildModuleFragment extends BaseFragment {

    List<ChildrenModuleCoverModel> childrenModuleCoverModels = new ArrayList<>();
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

    private OnItemClickListener onItemClickListener;

    public static ChildModuleFragment newInstance(List<ChildrenModuleCoverModel> childrenModuleCoverModels) {

        Bundle args = new Bundle();
        args.putSerializable("child", (Serializable) childrenModuleCoverModels);
        ChildModuleFragment fragment = new ChildModuleFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_area_list;
    }

    @Override
    protected void initViewData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            childrenModuleCoverModels = (List<ChildrenModuleCoverModel>) bundle.getSerializable("child");
        }

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
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

            ((ItemVh) holder).areaTextView.setText(childrenModuleCoverModels.get(position).getName());

            ((ItemVh) holder).rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(childrenModuleCoverModels.get(position));
                        close();
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return childrenModuleCoverModels.size();
        }

        private class ItemVh extends RecyclerView.ViewHolder {
            private TextView areaTextView;
            private LinearLayout rootView;

            public ItemVh(View itemView) {
                super(itemView);
                areaTextView = (TextView) itemView.findViewById(R.id.areaTextView);
                rootView = (LinearLayout) itemView.findViewById(R.id.rootView);
            }
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(ChildrenModuleCoverModel moduleCoverModel);
    }
}
