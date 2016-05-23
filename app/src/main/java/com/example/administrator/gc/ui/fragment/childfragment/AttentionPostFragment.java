package com.example.administrator.gc.ui.fragment.childfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.gc.R;
import com.example.administrator.gc.base.BaseFragment;
import com.example.administrator.gc.model.FollowPostModel;
import com.example.administrator.gc.presenter.fragment.AttentionPostPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_attention_child, container, false);
        ButterKnife.bind(this, v);

        userId = cache.readStringValue("userId", "");
        return v;
    }

    @Override
    protected void initView(View v) {
        attentionRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        attentionRecyclerView.setAdapter(new RVAdapter());
    }

    @Override
    protected void bind() {
        this.presenter = new AttentionPostPresenter();
        this.presenter.bind(this);
        presenter.getData(userId);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void unbind() {

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
            if (position < viewData.size()) {
                VH vh = (VH) holder;
                vh.titleTextView.setText(viewData.get(position).getPostTitle());
                vh.authorTextView.setText(viewData.get(position).getLzName());
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

        class VH extends RecyclerView.ViewHolder {
            TextView titleTextView;
            TextView authorTextView;

            public VH(View itemView) {
                super(itemView);
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
