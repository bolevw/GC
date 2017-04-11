package com.boger.game.gc.ui.fragment.childfragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boger.game.gc.R;
import com.boger.game.gc.base.BaseFragment;
import com.boger.game.gc.model.PictureListModel;
import com.boger.game.gc.model.PictureModel;
import com.boger.game.gc.presenter.fragment.PicturePresenter;
import com.boger.game.gc.ui.activity.PictureCommentActivity;
import com.boger.game.gc.utils.ImageLoaderUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/3/22.
 */
public class PictureCFragment extends BaseFragment {

    private static final int TYPE_NORMAL = 0x0001;
    private static final int TYPE_LOADING = 0x0002;
    private static final int TYPE_NO_DATA = 0x0003;

    private PicturePresenter presenter;
    private List<PictureModel> viewData = new ArrayList<>();
    private int index = 1;
    private int imageHeight = -1;
    private boolean isLoading = false;


    @BindView(R.id.pictureSwipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.pictureRecyclerView)
    RecyclerView recyclerView;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_picture;
    }

    @Override
    protected void initViewData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseActivity()));
        recyclerView.setAdapter(new RVAdapter());
    }

    @Override
    protected void bind() {
        presenter = new PicturePresenter();
        presenter.bind(this);
        presenter.getPicture(index);
    }

    public void notify(PictureListModel model) {
        if (index == 1) {
            viewData.clear();
        }
        viewData.addAll(model.getResults());
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    protected void setListener() {
        recyclerView.addOnScrollListener(onScrollListener);
    }

    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
            int position = manager.findLastVisibleItemPosition();
            if (position == viewData.size() && newState == RecyclerView.SCROLL_STATE_IDLE && !isLoading) {
                index++;
                presenter.getPicture(index);
            }
        }
    };

    @Override
    protected void unbind() {
        presenter.unBind();
    }

    private class RVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            if (viewType == TYPE_NORMAL) {
                return new VH(LayoutInflater.from(getBaseActivity()).inflate(R.layout.item_picture, parent, false));
            } else if (viewType == TYPE_LOADING) {
                return new FootVh(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading_more, parent, false));
            } else {
                return null;
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            if (position < viewData.size()) {
                final VH vh = (VH) holder;
                ImageLoaderUtils.load(viewData.get(position).url, vh.picture);

                vh.picture.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PictureCommentActivity.enter(getBaseActivity(), viewData.get(position).url, vh.picture);
                    }
                });

                vh.favourite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
            } else if (position == viewData.size()) {
                FootVh vh = (FootVh) holder;
            }
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

        @Override
        public int getItemCount() {
            return viewData.size() == 0 ? 0 : viewData.size() + 1;
        }

        private class VH extends RecyclerView.ViewHolder {
            private ImageView picture;
            private ImageView favourite;

            public VH(View itemView) {
                super(itemView);
                picture = (ImageView) itemView.findViewById(R.id.picture);
                favourite = (ImageView) itemView.findViewById(R.id.favourite);
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

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }
}
