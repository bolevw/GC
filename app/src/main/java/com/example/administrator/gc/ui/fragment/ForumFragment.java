package com.example.administrator.gc.ui.fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.gc.R;
import com.example.administrator.gc.base.BaseFragment;
import com.example.administrator.gc.model.ForumModel;
import com.example.administrator.gc.presenter.fragment.ForumPresenter;
import com.example.administrator.gc.ui.activity.ForumLabelListActivity;
import com.example.administrator.gc.utils.PicassoUtils;
import com.example.administrator.gc.widget.RecyclerViewCutLine;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/22.
 */
public class ForumFragment extends BaseFragment {

    private RecyclerView forumRecyclerView;
    private ForumPresenter presenter;
    private FloatingActionButton quickReturnButton;
    private List<ForumModel> recycleViewData = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_forum, container, false);
        return v;
    }

    @Override
    protected void initView(View v) {
        forumRecyclerView = (RecyclerView) v.findViewById(R.id.forumRecyclerView);
        forumRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        quickReturnButton = (FloatingActionButton) v.findViewById(R.id.quickReturnButton);
    }

    @Override
    protected void bind() {
        this.presenter = new ForumPresenter();
        this.presenter.bind(this);
    }

    public void notifyChange(List<ForumModel> list) {
        recycleViewData.clear();
        recycleViewData.addAll(list);
        forumRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.presenter.getData();

    }

    @Override
    protected void setListener() {
        forumRecyclerView.setAdapter(new RVAdapter());
        forumRecyclerView.addItemDecoration(new RecyclerViewCutLine(getResources().getDimensionPixelSize(R.dimen.cut_line), 0));
        forumRecyclerView.addOnScrollListener(onScrollListener);
        quickReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forumRecyclerView.smoothScrollToPosition(0);
            }
        });
    }

    RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (dy > 0) {
                // appear
                quickReturnButton.show();
            } else {
                quickReturnButton.hide();
                //disappear
            }
        }
    };

    @Override
    protected void unbind() {
        this.presenter.unBind();
    }

    private class RVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private int currentP;

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(getActivity()).inflate(R.layout.item_forum_recyclerview, parent, false);
            return new VH(v);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            final ForumModel model = recycleViewData.get(position);
            VH vh = (VH) holder;
            vh.name.setText(model.getForumName());
            PicassoUtils.normalShowImage(getActivity(), model.getImageSrc(), vh.forumImageView);
            vh.count.setText(String.format(getString(R.string.forum_count), model.getForumCount()));
            vh.content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ForumLabelListActivity.newInstance(getActivity(), model.getUrls());
                }
            });

            if (position > currentP) {
                vh.set.start();
            }
            currentP = position;
        }

        int height = 0;

        @Override
        public int getItemCount() {
            return recycleViewData.size();
        }

        class VH extends RecyclerView.ViewHolder {

            private ImageView forumImageView;
            private TextView name;
            private TextView count;
            private LinearLayout content;

            private AnimatorSet set;

            public VH(View itemView) {
                super(itemView);
                content = (LinearLayout) itemView.findViewById(R.id.content);
                forumImageView = (ImageView) itemView.findViewById(R.id.itemForumImageView);

                name = (TextView) itemView.findViewById(R.id.itemForumNameTextView);
                count = (TextView) itemView.findViewById(R.id.itemForumCountTextView);

                content.post(new Runnable() {
                    @Override
                    public void run() {
                        height = content.getMeasuredHeight();
                    }
                });
                set = new AnimatorSet();
                set.playTogether(ObjectAnimator.ofFloat(content, View.TRANSLATION_Y, height, 0),
                        ObjectAnimator.ofFloat(content, View.ALPHA, 0f, 1f));
                set.setDuration(300);

            }
        }
    }
}
