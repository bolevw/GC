package com.boger.game.gc.ui.fragment.childfragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boger.game.gc.R;
import com.boger.game.gc.base.BaseFragment;
import com.boger.game.gc.model.GameItemModel;
import com.boger.game.gc.presenter.fragment.GamePresenter;
import com.boger.game.gc.ui.activity.ForumLabelListActivity;
import com.boger.game.gc.utils.ImageLoaderUtils;
import com.boger.game.gc.widget.ItemTouchHelperAdapter;
import com.boger.game.gc.widget.SimpleItemTouchHelperCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/22.
 */
public class GameCFragment extends BaseFragment {

    private static final String TAG = "GameCFragment";
    private GamePresenter presenter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    private List<GameItemModel> recyclerData = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.child_fragment_game, container, false);
        return v;
    }

    @Override
    protected void initView(View v) {
        recyclerView = (RecyclerView) v.findViewById(R.id.gameRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.gameSwipeRefreshLayout);
    }

    class RecyclerViewItemDirection extends RecyclerView.ItemDecoration {
        int offset;

        public RecyclerViewItemDirection(int offset) {
            super();
            this.offset = offset;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            if (parent.getChildLayoutPosition(view) != 0) {
                outRect.top = offset;
            }
        }
    }

    private SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            presenter.getData(true);
        }
    };

    public void stopRefresh() {
        if (null != swipeRefreshLayout && swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    protected void bind() {
        this.presenter = new GamePresenter();
        this.presenter.bind(this);
        presenter.getData(false);
    }

    public void notifyChange(List<GameItemModel> list) {
        this.recyclerData.clear();
        this.recyclerData.addAll(list);
        this.recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    protected void setListener() {
        RecyclerViewAdapter adapter = new RecyclerViewAdapter();
        recyclerView.addItemDecoration(new RecyclerViewItemDirection(getResources().getDimensionPixelSize(R.dimen.cut_line)));
        swipeRefreshLayout.setOnRefreshListener(onRefreshListener);
        recyclerView.setMotionEventSplittingEnabled(false);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ItemTouchHelperAdapter {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(getActivity()).inflate(R.layout.item_game_recyclerview, parent, false);
            return new VH(v);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            VH vh = (VH) holder;
            final GameItemModel model = recyclerData.get(position);
            vh.name1.setText(model.getName());
            ImageLoaderUtils.load(model.getImageSrc(), vh.image1);
            vh.item1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ForumLabelListActivity.newInstance(getActivity(), model.getUrls());
                }
            });

        }

        @Override
        public int getItemCount() {
            return recyclerData.size();
        }

        @Override
        public void itemMove(int fromP, int endP) {
            notifyItemMoved(fromP, endP);
        }

        @Override
        public void onItemDisMiss(int position) {
//            notifyItemRemoved(position);
        }

        private class VH extends RecyclerView.ViewHolder {
            private LinearLayout item1;
            private ImageView image1;
            private TextView name1;

            public VH(View itemView) {
                super(itemView);
                item1 = (LinearLayout) itemView.findViewById(R.id.itemGameLinearLayout1);
                image1 = (ImageView) itemView.findViewById(R.id.itemGameImageView1);
                name1 = (TextView) itemView.findViewById(R.id.itemGameTextView1);
            }
        }
    }

    @Override
    protected void unbind() {
        this.presenter.unBind();
    }
}
