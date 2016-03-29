package com.example.administrator.gc.ui.fragment.childfragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.gc.R;
import com.example.administrator.gc.base.BaseFragment;
import com.example.administrator.gc.model.GameItemModel;
import com.example.administrator.gc.presenter.fragment.GamePresenter;
import com.example.administrator.gc.utils.PicassoUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/22.
 */
public class GameCFragment extends BaseFragment {

    private static final String TAG = "GameCFragment";

    private GamePresenter presener;

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
        recyclerView.setAdapter(new RecyclerViewAdapter());

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

    SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            presener.getData(true);
        }
    };


    public void stopRefresh() {
        if (null != swipeRefreshLayout && swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    protected void bind() {
        this.presener = new GamePresenter();
        this.presener.bind(this);
        presener.getData(false);
    }

    public void notifyChange(List<GameItemModel> list) {
        this.recyclerData.clear();
        this.recyclerData.addAll(list);
        this.recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    protected void setListener() {
        recyclerView.addItemDecoration(new RecyclerViewItemDirection(getResources().getDimensionPixelSize(R.dimen.cut_line)));
        swipeRefreshLayout.setOnRefreshListener(onRefreshListener);
        recyclerView.setMotionEventSplittingEnabled(false);
    }

    class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(getActivity()).inflate(R.layout.item_game_recyclerview, parent, false);
            return new VH(v);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            VH vh = (VH) holder;
            GameItemModel model = recyclerData.get(position);
            vh.name1.setText(model.getName());
            PicassoUtils.normalShowImage(getActivity(), model.getImageSrc(), vh.image1);

        }

        @Override
        public int getItemCount() {
            return recyclerData.size();
        }

        class VH extends RecyclerView.ViewHolder {
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
        this.presener.unBind();
    }
}
