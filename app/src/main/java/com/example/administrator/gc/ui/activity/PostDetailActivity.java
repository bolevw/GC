package com.example.administrator.gc.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.gc.R;
import com.example.administrator.gc.base.BaseActivity;
import com.example.administrator.gc.base.ItemData;
import com.example.administrator.gc.base.RecyclerViewData;
import com.example.administrator.gc.model.PostBodyModel;
import com.example.administrator.gc.model.PostDetailHeaderModel;
import com.example.administrator.gc.model.PostDetailModel;
import com.example.administrator.gc.presenter.activity.PostDetailPresenter;
import com.example.administrator.gc.utils.PicassoUtils;
import com.example.administrator.gc.widget.RecyclerViewCutLine;

/**
 * Created by Administrator on 2016/4/8.
 */
public class PostDetailActivity extends BaseActivity {

    private static final int TYPE_HEADER = 0x0001;
    private static final int TYPE_COMMENT = 0x0002;
    private static final int TYPE_LOADING = 0x0003;


    PostDetailPresenter presenter;
    private String urls;

    private RecyclerView recyclerView;

    private RecyclerViewData<ItemData> viewData = new RecyclerViewData();


    public static void newInstance(Activity activity, String urls) {
        Intent intent = new Intent(activity, PostDetailActivity.class);
        intent.putExtra("urls", urls);
        activity.startActivity(intent);
    }


    @Override
    protected void initView() {
        setContentView(R.layout.activity_post_detail);

        Intent intent = getIntent();
        urls = intent.getStringExtra("urls");

        recyclerView = (RecyclerView) findViewById(R.id.postDetailRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(new RVAdapter());
    }

    @Override
    protected void setListener() {
        recyclerView.addItemDecoration(new RecyclerViewCutLine(getResources().getDimensionPixelSize(R.dimen.cut_line), 0));
        recyclerView.addOnScrollListener(onScrollListener);
    }


    @Override
    protected void bind() {
        presenter = new PostDetailPresenter();
        presenter.bind(this);
        presenter.getData(urls, false);
    }

    @Override
    protected void unBind() {
        presenter.unBind();
    }


    public void notifyChange(PostBodyModel model, boolean getNextPage) {
        if (!getNextPage) {
            ItemData<Integer, PostDetailHeaderModel> headerData = new ItemData<>();
            headerData.setKey(TYPE_HEADER);
            headerData.setValue(model.getHeader());
            viewData.add(headerData);
        }


        for (PostDetailModel detailModel : model.getCommentList()) {
            ItemData<Integer, PostDetailModel> detail = new ItemData<>();
            detail.setKey(TYPE_COMMENT);
            detail.setValue(detailModel);
            viewData.add(detail);
        }

        recyclerView.getAdapter().notifyDataSetChanged();
    }


    public void notifyNoData() {
        recyclerView.getAdapter().notifyItemChanged(viewData.size());
    }

    class RVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == TYPE_HEADER) {
                return new HeaderVh(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post_detail_header_recyclerview, parent, false));
            } else if (viewType == TYPE_LOADING) {
                return new FootVh(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading_more, parent, false));
            } else {
                return new CommentVh(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post_detail_recyclerview, parent, false));
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

            if (getItemViewType(position) == TYPE_HEADER) {
                final ItemData<Integer, PostDetailHeaderModel> data = (ItemData<Integer, PostDetailHeaderModel>) viewData.get(position);
                final HeaderVh vh = (HeaderVh) holder;
                vh.type.setText("楼主");
                vh.name.setText(data.getValue().getHeader().getUserName());
                PicassoUtils.normalShowImage(PostDetailActivity.this, data.getValue().getHeader().getUserPhotoSrc(), vh.imageSrc);
                vh.date.setText(data.getValue().getHeader().getDate());
                vh.itemPostTitleTextView.setText(data.getValue().getTitle());
                vh.itemBodyContentTextView.setText(Html.fromHtml(data.getValue().getContent()));
            } else if (getItemViewType(position) == TYPE_COMMENT) {
                final ItemData<Integer, PostDetailModel> data = (ItemData<Integer, PostDetailModel>) viewData.get(position);
                final CommentVh vh = (CommentVh) holder;
                vh.date.setText(data.getValue().getUserMessageModel().getDate());
                vh.name.setText(data.getValue().getUserMessageModel().getUserName());
                if (position == 1) {
                    vh.type.setText("沙发");
                } else if (position == 2) {
                    vh.type.setText("板凳");
                } else if (position == 3) {
                    vh.type.setText("地板");
                } else {
                    vh.type.setText(position + "楼");
                }

                vh.itemBodyContentTextView.setText(Html.fromHtml(data.getValue().getContent()));
                PicassoUtils.normalShowImage(PostDetailActivity.this, data.getValue().getUserMessageModel().getUserPhotoSrc(), vh.imageSrc);

            } else if (getItemViewType(position) == TYPE_LOADING) {
                FootVh vh = (FootVh) holder;
                if (!presenter.isHasData()) {
                    vh.loadingContent.setVisibility(View.GONE);
                    vh.noDataTextView.setVisibility(View.VISIBLE);
                } else {
                    vh.loadingContent.setVisibility(View.VISIBLE);
                    vh.noDataTextView.setVisibility(View.GONE);
                }
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0 && viewData.size() != 0) {
                return TYPE_HEADER;
            } else if (position == viewData.size()) {
                return TYPE_LOADING;
            } else {
                return TYPE_COMMENT;
            }
        }

        @Override
        public int getItemCount() {
            return viewData.size() == 0 ? 0 : viewData.size() + 1;
        }

        private class HeaderVh extends RecyclerView.ViewHolder {
            private TextView name;
            private TextView date;
            private ImageView imageSrc;
            private TextView type;
            private TextView itemPostTitleTextView;
            private TextView itemBodyContentTextView;
            private LinearLayout content;

            public HeaderVh(View itemView) {
                super(itemView);

                name = (TextView) itemView.findViewById(R.id.itemUserNameTextView);
                date = (TextView) itemView.findViewById(R.id.itemPostDateTextView);
                imageSrc = (ImageView) itemView.findViewById(R.id.itemUserImageView);
                type = (TextView) itemView.findViewById(R.id.itemUserTypeTextView);
                itemPostTitleTextView = (TextView) itemView.findViewById(R.id.itemPostTitleTextView);
                itemBodyContentTextView = (TextView) itemView.findViewById(R.id.itemBodyContentTextView);
                content = (LinearLayout) itemView.findViewById(R.id.userMessageContent);
            }
        }

        private class CommentVh extends RecyclerView.ViewHolder {
            private TextView name;
            private TextView date;
            private ImageView imageSrc;
            private TextView type;
            private TextView itemBodyContentTextView;
            private LinearLayout commentContent;

            public CommentVh(View itemView) {
                super(itemView);
                commentContent = (LinearLayout) findViewById(R.id.userMessageContent);
                name = (TextView) itemView.findViewById(R.id.itemUserNameTextView);
                date = (TextView) itemView.findViewById(R.id.itemPostDateTextView);
                imageSrc = (ImageView) itemView.findViewById(R.id.itemUserImageView);
                type = (TextView) itemView.findViewById(R.id.itemUserTypeTextView);
                itemBodyContentTextView = (TextView) itemView.findViewById(R.id.itemBodyContentTextView);
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

    boolean isLoading = false;

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        int position;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
            position = manager.findLastVisibleItemPosition();
            if (position == viewData.size() && newState == RecyclerView.SCROLL_STATE_IDLE && !isLoading) {
                presenter.getNextPage();
            }

        }
    };
}
