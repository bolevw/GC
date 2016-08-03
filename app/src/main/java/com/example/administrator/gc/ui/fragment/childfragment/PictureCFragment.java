package com.example.administrator.gc.ui.fragment.childfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.administrator.gc.R;
import com.example.administrator.gc.base.BaseApplication;
import com.example.administrator.gc.base.BaseFragment;
import com.example.administrator.gc.model.PictureListModel;
import com.example.administrator.gc.model.PictureModel;
import com.example.administrator.gc.presenter.fragment.PicturePresenter;
import com.example.administrator.gc.ui.activity.PictureCommentActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/3/22.
 */
public class PictureCFragment extends BaseFragment {

    private PicturePresenter presenter;
    private List<PictureModel> viewData = new ArrayList<>();
    private int index = 1;
    private int imageHeight = -1;

    @BindView(R.id.pictureSwipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.pictureRecyclerView)
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_picture, container, false);
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
        presenter = new PicturePresenter();
        presenter.bind(this);
        presenter.getPicture(index);
    }

    public void notify(PictureListModel model) {
        viewData = model.getResults();
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    protected void setListener() {
        recyclerView.addOnScrollListener(onScrollListener);
    }

    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            int position = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

            Log.d("dy", String.valueOf(dy));

        }
    };

    @Override
    protected void unbind() {
        presenter.unBind();
    }

    private class RVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new VH(LayoutInflater.from(getBaseActivity()).inflate(R.layout.item_picture, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            final VH vh = (VH) holder;
            Picasso.with(BaseApplication.getContext().getApplicationContext())
                    .load(viewData.get(position).url)
                    .into(vh.picture);

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
        }

        @Override
        public int getItemCount() {
            return viewData.size();
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
    }
}
