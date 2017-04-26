package com.boger.game.gc.ui.fragment.childfragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boger.game.gc.R;
import com.boger.game.gc.base.BaseFragment;
import com.boger.game.gc.model.FollowPostModel;
import com.boger.game.gc.presenter.fragment.AttentionPostPresenter;
import com.boger.game.gc.ui.activity.PostDetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by liubo on 2016/5/19.
 */
public class AttentionPostFragment extends BaseFragment {

    private static final int TYPE_ITEM = 0x0001;
    private static final int TYPE_LOADING = 0x0002;

    private AttentionPostPresenter presenter;

    private List<FollowPostModel> viewData = new ArrayList();
    private String userId;

    @BindView(R.id.attentionRecyclerView)
    RecyclerView attentionRecyclerView;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_attention_child;
    }

    @Override
    protected void initViewData() {
        userId = cache.readStringValue("userId", "");

        attentionRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        attentionRecyclerView.setAdapter(new RVAdapter());
    }

    @Override
    protected void bind() {
        this.presenter = new AttentionPostPresenter();
        this.presenter.bind(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getData(userId);
    }

    @Override
    protected void setListener() {

    }

    public void setViewData(List<FollowPostModel> viewData) {
        this.viewData = viewData;
        attentionRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    protected void unbind() {
        presenter.unBind();
    }

    private class RVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == TYPE_ITEM) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_attention_post_recyclerview, parent, false);
                return new VH(view);
            } else {
                View v = LayoutInflater.from(getActivity()).inflate(R.layout.item_loading_more, parent, false);
                return new FootVh(v);
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            position = holder.getAdapterPosition();
            if (position < viewData.size()) {
                VH vh = (VH) holder;
                vh.titleTextView.setText(viewData.get(position).getPostTitle());
                vh.authorTextView.setText(viewData.get(position).getLzName());
                final int finalPosition = position;
                vh.rootView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PostDetailActivity.newInstance(getBaseActivity(), viewData.get(finalPosition).getPostUrl());
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return viewData.size();
        }

        @Override
        public int getItemViewType(int position) {
            if (position == viewData.size()) {
                return TYPE_LOADING;
            } else {
                return TYPE_ITEM;
            }
        }

        private class VH extends RecyclerView.ViewHolder {
            private TextView titleTextView;
            private TextView authorTextView;
            private LinearLayout rootView;

            public VH(View itemView) {
                super(itemView);
                rootView = (LinearLayout) itemView.findViewById(R.id.rootView);
                titleTextView = (TextView) itemView.findViewById(R.id.itemTitleTextView);
                authorTextView = (TextView) itemView.findViewById(R.id.itemAuthorTextView);
            }
        }

        private class FootVh extends RecyclerView.ViewHolder {
            private LinearLayout loadingContent;
            private TextView noDataTextView;

            public FootVh(View itemView) {
                super(itemView);
                loadingContent = (LinearLayout) itemView.findViewById(R.id.loadingContent);
                noDataTextView = (TextView) itemView.findViewById(R.id.noDataTextView);
            }
        }
    }
}
