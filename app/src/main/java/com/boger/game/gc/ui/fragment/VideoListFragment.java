package com.boger.game.gc.ui.fragment;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.boger.game.gc.R;
import com.boger.game.gc.base.BaseFragment;
import com.boger.game.gc.model.VideoBannerModel;
import com.boger.game.gc.model.VideoChannelModel;
import com.boger.game.gc.model.VideoChannelTitleModel;
import com.boger.game.gc.model.VideoIndexModel;
import com.boger.game.gc.presenter.fragment.VideoListPresenter;
import com.boger.game.gc.ui.activity.VideoPlayerActivity;
import com.boger.game.gc.utils.ImageLoaderUtils;
import com.boger.game.gc.widget.AutoViewPager;
import com.boger.game.gc.widget.VPIndicator;
import com.boger.game.gc.widget.loadmoreRecyclerview.BaseAdapter;
import com.boger.game.gc.widget.loadmoreRecyclerview.LoadingMoreRv;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by liubo on 2017/4/24.
 */

public class VideoListFragment extends BaseFragment {
    private static final int TYPE_ITEM = 0x0001;
    private static final int TYPE_HEADER = 0x0002;
    private static final int TYPE_TITLE = 0x0003;

    VideoListPresenter presenter;

    private AutoViewPager autoViewPager;
    private VPIndicator indicator;
    @BindView(R.id.videoRv)
    LoadingMoreRv videoRv;
    String url;

    GridLayoutManager manager;

    private List<VideoChannelTitleModel> titleData = new ArrayList<>();

    private List<VideoChannelModel> viewData = new ArrayList<>();

    private List<VideoBannerModel> bannerData = new ArrayList<>();

    public static VideoListFragment newInstance(String url) {
        Bundle args = new Bundle();
        args.putString("url", url);

        VideoListFragment fragment = new VideoListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_video_list;
    }

    @Override
    protected void initViewData() {
        Bundle bundle = getArguments();
        url = bundle.getString("url");

        videoRv.setLayoutManager(manager = new GridLayoutManager(getActivity(), 2));
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 0 || position % 5 == 1 || position == viewData.size() + titleData.size() + 2) {
                    return 2;
                } else {
                    return 1;
                }
            }
        });
        videoRv.setAdapter(new BaseAdapter() {
            @Override
            protected void onBindItemVh(RecyclerView.ViewHolder holder, int position) {
                if (TYPE_ITEM == getItemType(position)) {
                    VideoVH vh = (VideoVH) holder;
                    int fixPosition = position - 1 - (position % 5 == 0 ? position / 5 : position / 5 + 1);
                    final VideoChannelModel model = viewData.get(fixPosition);
                    Log.d(TAG, "onBindItemVh() fix[" + fixPosition + "], position = [" + position + "]" + model.getName());
                    vh.container.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //href;model.getHref();
                            VideoPlayerActivity.newInstance(getActivity(), model.getHref());
                        }
                    });
                    ImageLoaderUtils.load(model.getCover(), vh.imgPic);
                    vh.nums.setText(model.getNum());
                    vh.title.setText(model.getName());
                    vh.time.setText(model.getTime());
                    vh.setText(R.id.userTv, model.getAuthor());
                } else if (TYPE_TITLE == getItemType(position)) {
                    TitleVh vh = (TitleVh) holder;
                    int fixPosition = position / 5;
                    vh.setText(R.id.titleTv, titleData.get(fixPosition).getTitle());
                } else if (TYPE_HEADER == getItemType(position)) {
                    Banner vh = (Banner) holder;
                    autoViewPager = vh.viewPager;
                    indicator = vh.vpIndicator;
                }
            }

            @Override
            protected RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
                if (viewType == TYPE_HEADER) {
                    return new Banner(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video_header, parent, false));
                } else if (viewType == TYPE_TITLE) {
                    return new TitleVh(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video_title, parent, false));
                } else {
                    return new VideoVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false));
                }
            }

            @Override
            protected int getItemType(int position) {
                if (position == 0) {
                    return TYPE_HEADER;
                } else if (position % 5 == 1) {
                    return TYPE_TITLE;
                } else {
                    return TYPE_ITEM;
                }
            }

            @Override
            protected String getEmptyText() {
                return "没有视频了！";
            }

            @Override
            protected int dataSize() {
                return viewData.size() + titleData.size() + 1;
            }

            class VideoVH extends Vh {

                private TextView nums;
                private TextView time;
                private TextView title;
                private ImageView imgPic;
                private RelativeLayout container;


                public VideoVH(View itemView) {
                    super(itemView);
                    container = (RelativeLayout) itemView.findViewById(R.id.container);
                    nums = (TextView) itemView.findViewById(R.id.nums);
                    time = (TextView) itemView.findViewById(R.id.time);
                    title = (TextView) itemView.findViewById(R.id.videoTitleTextView);
                    imgPic = (ImageView) itemView.findViewById(R.id.videoImageView);
                }
            }

            class Banner extends RecyclerView.ViewHolder {
                AutoViewPager viewPager;
                VPIndicator vpIndicator;

                public Banner(View itemView) {
                    super(itemView);
                    viewPager = (AutoViewPager) itemView.findViewById(R.id.bannerVp);
                    vpIndicator = (VPIndicator) itemView.findViewById(R.id.bannerVpIndicator);
                    viewPager.setAdapter(new Adapter());
                }
            }

            class TitleVh extends Vh {
                public TitleVh(View itemView) {
                    super(itemView);
                }
            }
        });
    }

    @Override
    protected void bind() {
        this.presenter = new VideoListPresenter();
        this.presenter.bind(this);
        this.presenter.getData(url);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void unbind() {
        this.presenter.unBind();
    }

    public void getDataSuccess(VideoIndexModel videoIndexModel) {
        viewData.clear();
        bannerData.clear();
        bannerData.addAll(videoIndexModel.getBanners());
        for (int i = 0; i < videoIndexModel.getItems().size(); i++) {
            viewData.addAll(videoIndexModel.getItems().get(i).getList());
            titleData.add(videoIndexModel.getItems().get(i).getTitle());
        }

        if (autoViewPager != null) {
            autoViewPager.getAdapter().notifyDataSetChanged();
            autoViewPager.start();

            String[] title = new String[bannerData.size()];
            for (int i = 0; i < bannerData.size(); i++) {
                title[i] = bannerData.get(i).getTitle();
            }
            indicator.setViewPager(autoViewPager, title);
        }
        videoRv.getAdapter().notifyDataSetChanged();
    }

    class Adapter extends PagerAdapter {

        @Override
        public int getCount() {
            return bannerData.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return object.equals(view);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView view = new ImageView(getActivity());
            view.setScaleType(ImageView.ScaleType.FIT_XY);
            ImageLoaderUtils.load(bannerData.get(position).getCoverUrl(), view);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
