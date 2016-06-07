package com.example.administrator.gc.ui.fragment.childfragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.gc.R;
import com.example.administrator.gc.base.BaseFragment;
import com.example.administrator.gc.model.JokeModel;
import com.example.administrator.gc.model.JokeResponse;
import com.example.administrator.gc.presenter.fragment.JokePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/3/22.
 */
public class JokeCFragment extends BaseFragment {
    private static final int TYPE_NORMAL = 0x0001;
    private static final int TYPE_LOADING = 0x0002;
    private static final int TYPE_NO_DATA = 0x0003;

    private JokePresenter presenter;
    private int index = 1;

    @BindView(R.id.jokeRecyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.jokeSwipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;

    private List<JokeModel> viewData = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.child_joke_fragment, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    protected void initView(View v) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseActivity()));
        recyclerView.setAdapter(new RVAdapter());
    }

    @Override
    protected void bind() {
        presenter = new JokePresenter();
        presenter.bind(this);
        presenter.getData(index);
    }

    public void setViewData(JokeResponse response) {
        viewData.addAll(response.getResult());
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    protected void setListener() {
        refreshLayout.setOnRefreshListener(onRefreshListener);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getData(index);
    }

    @Override
    protected void unbind() {
        presenter.unBind();
    }

    private SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            presenter.getData(1);
        }
    };

    public void stopRefresh() {
        if (refreshLayout != null && refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
    }

    private class RVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new VH(LayoutInflater.from(getBaseActivity()).inflate(R.layout.item_joke, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (position < viewData.size()) {
                final JokeModel model = viewData.get(position);
                VH vh = (VH) holder;
                vh.jokeTextView.setText(model.getContent());
                vh.dateTextView.setText(model.getUpdatetime());
                vh.rootView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.putExtra(Intent.EXTRA_TEXT, model.getContent());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setType("text/plain"); // 纯文本
                        intent.putExtra(Intent.EXTRA_SUBJECT, "ss");
                        startActivity(Intent.createChooser(intent, "分享"));
                        return true;
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return viewData.size() == 0 ? 0 : viewData.size() + 1;
        }

        @Override
        public int getItemViewType(int position) {
            if (position < viewData.size()) {
                return TYPE_NORMAL;
            } else if (position == viewData.size()) {
                return TYPE_LOADING;
            } else {
                return TYPE_NO_DATA;
            }
        }

        private class VH extends RecyclerView.ViewHolder {
            private TextView jokeTextView;
            private TextView dateTextView;
            private LinearLayout rootView;

            public VH(View itemView) {
                super(itemView);
                rootView = (LinearLayout) itemView.findViewById(R.id.rootView);
                jokeTextView = (TextView) itemView.findViewById(R.id.itemJokeTextView);
                dateTextView = (TextView) itemView.findViewById(R.id.itemTimeTextView);
            }
        }
    }
}
