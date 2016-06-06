package com.example.administrator.gc.ui.fragment.childfragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.gc.R;
import com.example.administrator.gc.base.BaseFragment;
import com.example.administrator.gc.base.ItemData;
import com.example.administrator.gc.base.RecyclerViewData;
import com.example.administrator.gc.model.BannerModel;
import com.example.administrator.gc.model.GroupItemModel;
import com.example.administrator.gc.model.HotRankingModel;
import com.example.administrator.gc.model.IndexModel;
import com.example.administrator.gc.model.PreviewGroup;
import com.example.administrator.gc.presenter.fragment.RecommendPresenter;
import com.example.administrator.gc.ui.activity.ForumLabelListActivity;
import com.example.administrator.gc.ui.activity.LoLActivity;
import com.example.administrator.gc.utils.PicassoUtils;
import com.example.administrator.gc.widget.VPIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/22.
 */
public class RecommendCFragment extends BaseFragment {

    public static final int LOL_SEARCH = 0;
    public static final int LOL_RECORD = 1;
    public static final int LOL_COMMON_HERO = 2;
    public static final int LOL_HERO_MESSAGE = 3;
    public static final int LOL_LEVEL = 4;
    public static final int LOL_FORECAST = 5;

    private static final int TYPE_BANNER = 1;
    private static final int TYPE_HOT = 2;
    private static final int TYPE_NORMAL = 3;

    private Activity context;

    private RecommendPresenter presenter;

    private SwipeRefreshLayout swipeRefreshLayout;
    private ClickGetMoreListener getMoreListener;


    private int[] colors = new int[]{R.mipmap.ic_banner_01, R.mipmap.ic_banner_02, R.mipmap.ic_banner_03,
            R.mipmap.ic_banner_04, R.mipmap.ic_banner_05};
    private int currentPosition = 0;

    private String[] lolTitles = new String[]{"玩家信息查询", "战绩查询", "常用英雄", "英雄数据", "玩家段位", "段位预测"};

    private RecyclerView recyclerView;
    private RecyclerViewData data = new RecyclerViewData();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.child_fragment_recommond, container, false);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        currentPosition = 0;
        presenter.getData(false);
    }

    public void notifyHotDataChange(IndexModel model) {
        data.clear();
        ItemData<Integer, List<BannerModel>> itemData = new ItemData();
        itemData.setKey(TYPE_BANNER);
        itemData.setValue(model.getBannerList());
        data.add(itemData);

        ItemData<Integer, List<HotRankingModel>> hotItemData = new ItemData<>();
        hotItemData.setKey(TYPE_HOT);
        hotItemData.setValue(model.getHotRankingList());
        data.add(hotItemData);

        for (PreviewGroup group : model.getPreviewGroupList()) {
            ItemData<Integer, PreviewGroup> preItemData = new ItemData<>();
            preItemData.setKey(TYPE_NORMAL);
            preItemData.setValue(group);
            data.add(preItemData);
        }
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = (Activity) context;
    }

    @Override
    protected void initView(View v) {
        recyclerView = (RecyclerView) v.findViewById(R.id.recommendRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new RecyclerViewAdapter());
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin);
        recyclerView.addItemDecoration(new SpaceItemDecoration(spacingInPixels));
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.recommendSwipeRefreshLayout);
    }

    @Override
    protected void bind() {
        this.presenter = new RecommendPresenter();
        presenter.bind(this);
    }

    @Override
    protected void setListener() {
        swipeRefreshLayout.setOnRefreshListener(onRefreshListener);
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
    protected void unbind() {
        presenter.unBind();
    }

    private class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            switch (viewType) {
                case TYPE_BANNER:
                    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_banner_recyclerview, parent, false);
                    return new BannerVH(v);
                case TYPE_HOT:
                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hot_container_recyclerview, parent, false);
                    return new HotVh(v);
                case TYPE_NORMAL:
                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group_detail_recyclerview, parent, false);
                    return new NormalVh(v);
                default:
                    return null;
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            final ItemData itemData = (ItemData) data.get(position);
            switch (getItemViewType(position)) {
                case TYPE_BANNER:
                    BannerVH vh = (BannerVH) holder;
                    List<BannerModel> bannerModels = (List<BannerModel>) itemData.getValue();
                    vh.setList(bannerModels);
                    break;
                case TYPE_HOT:
                    HotVh hotVh = (HotVh) holder;
                    List<HotRankingModel> hotRankingModels = (List<HotRankingModel>) itemData.getValue();
                    hotVh.setHotList(hotRankingModels);
                    break;
                case TYPE_NORMAL:
                    NormalVh normalVh = (NormalVh) holder;
                    PreviewGroup group = (PreviewGroup) itemData.getValue();
                    normalVh.groupName.setText(group.getName());
                    final List<GroupItemModel> itemList = group.getGroupItemList();
                    normalVh.name_1.setText(itemList.get(0).getName());
                    normalVh.name_2.setText(itemList.get(1).getName());
                    normalVh.name_3.setText(itemList.get(2).getName());
                    normalVh.name_4.setText(itemList.get(3).getName());
                    PicassoUtils.normalShowImage(context, itemList.get(0).getImageSrc(), normalVh.image_1);
                    PicassoUtils.normalShowImage(context, itemList.get(1).getImageSrc(), normalVh.image_2);
                    PicassoUtils.normalShowImage(context, itemList.get(2).getImageSrc(), normalVh.image_3);
                    PicassoUtils.normalShowImage(context, itemList.get(3).getImageSrc(), normalVh.image_4);
                    for (int i = 0; i < normalVh.layouts.size(); i++) {
                        LinearLayout layout = normalVh.layouts.get(i);
                        final int finalI = i;
                        layout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ForumLabelListActivity.newInstance(context, itemList.get(finalI).getUrl());
                            }
                        });
                    }
                    break;
                default:
                    break;
            }
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0) {
                return TYPE_BANNER;
            } else if (position == 1) {
                return TYPE_HOT;
            } else {
                return TYPE_NORMAL;
            }
        }

        private class BannerVH extends RecyclerView.ViewHolder {
            VPIndicator vpIndicator;
            private List<BannerModel> list = new ArrayList<>();

            public BannerVH(View itemView) {
                super(itemView);
                bannerViewPager = (ViewPager) itemView.findViewById(R.id.bannerViewPager);
                vpIndicator = (VPIndicator) itemView.findViewById(R.id.bannerVpIndicator);
                vpIndicator.setCount(colors.length);
                bannerViewPager.setAdapter(new ViewPagerAdapter());
                bannerViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        vpIndicator.selection(position);
                        currentPosition = position;
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        handler.removeMessages(1);
                        handler.removeCallbacks(this);

                        Message message = handler.obtainMessage();
                        message.what = 1;
                        handler.sendMessage(message);
                    }
                }, 2000);
            }

            public List<BannerModel> getList() {
                return list;
            }

            public void setList(List<BannerModel> list) {
                this.list = list;
            }
        }

        private class HotVh extends RecyclerView.ViewHolder {
            private RecyclerView hotRecyclerView;
            private List<HotRankingModel> hotList = new ArrayList<>();

            public HotVh(View itemView) {
                super(itemView);

                hotRecyclerView = (RecyclerView) itemView.findViewById(R.id.hotRecyclerView);
                hotRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
                hotRecyclerView.setAdapter(new HotItemRecyclerViewAdapter());
            }

            public List<HotRankingModel> getHotList() {
                return hotList;
            }

            public void setHotList(List<HotRankingModel> hotList) {
                this.hotList = hotList;
                hotRecyclerView.getAdapter().notifyDataSetChanged();
            }

            private class HotItemRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

                @Override
                public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hot_recyclerview, parent, false);
                    return new Vh(v);
                }

                @Override
                public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                    Vh vh = (Vh) holder;
                    if (position < hotList.size()) {
                        final HotRankingModel model = hotList.get(position);
                        Log.d("urls", model.getUrls());
                        vh.textView.setText(model.getName());
                        PicassoUtils.normalShowImage(context, model.getImageSrc(), vh.imageView);
                        vh.content.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ForumLabelListActivity.newInstance(context, model.getUrls());
                            }
                        });
                    } else if (position == hotList.size()) {
                        vh.textView.setText("get more");
                        vh.imageView.setImageResource(android.R.drawable.ic_menu_more);
                        vh.content.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (getMoreListener != null) {
                                    getMoreListener.click();
                                }
                            }
                        });
                    }
                }

                @Override
                public int getItemCount() {
                    return hotList.size() == 0 ? 0 : hotList.size() + 1;
                }

                private class Vh extends RecyclerView.ViewHolder {
                    ImageView imageView;
                    TextView textView;
                    private LinearLayout content;

                    public Vh(View itemView) {
                        super(itemView);
                        content = (LinearLayout) itemView.findViewById(R.id.hotContainer);
                        imageView = (ImageView) itemView.findViewById(R.id.itemHotImageView);
                        textView = (TextView) itemView.findViewById(R.id.itemHotTextView);
                    }
                }
            }
        }

        private class NormalVh extends RecyclerView.ViewHolder {
            private TextView groupName;
            private ImageView image_1;
            private ImageView image_2;
            private ImageView image_3;
            private ImageView image_4;

            private TextView name_1;
            private TextView name_2;
            private TextView name_3;
            private TextView name_4;

            private LinearLayout groupTitle, group_1, group_2, group_3, group_4;
            List<LinearLayout> layouts = new ArrayList<>();

            public NormalVh(View itemView) {
                super(itemView);
                groupName = (TextView) itemView.findViewById(R.id.groupName);

                groupTitle = (LinearLayout) itemView.findViewById(R.id.groupTitle);
                group_1 = (LinearLayout) itemView.findViewById(R.id.group_1);
                group_2 = (LinearLayout) itemView.findViewById(R.id.group_2);
                group_3 = (LinearLayout) itemView.findViewById(R.id.group_3);
                group_4 = (LinearLayout) itemView.findViewById(R.id.group_4);

                name_1 = (TextView) itemView.findViewById(R.id.name_1);
                name_2 = (TextView) itemView.findViewById(R.id.name_2);
                name_3 = (TextView) itemView.findViewById(R.id.name_3);
                name_4 = (TextView) itemView.findViewById(R.id.name_4);

                image_1 = (ImageView) itemView.findViewById(R.id.image_1);
                image_2 = (ImageView) itemView.findViewById(R.id.image_2);
                image_3 = (ImageView) itemView.findViewById(R.id.image_3);
                image_4 = (ImageView) itemView.findViewById(R.id.image_4);

                layouts.add(group_1);
                layouts.add(group_2);
                layouts.add(group_3);
                layouts.add(group_4);
            }
        }
    }

    private class ViewPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return colors.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            TextView view = new TextView(context);
            view.setBackgroundResource(colors[position]);
            view.setText(lolTitles[position]);
            view.setTextColor(ContextCompat.getColor(getBaseActivity(), R.color.white));
            view.setTextSize(getResources().getDimensionPixelSize(R.dimen.text_title));
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getBaseActivity(), LoLActivity.class);
                    intent.putExtra("lol_action", position);
                    startActivity(intent);
                }
            });
            view.setGravity(Gravity.CENTER);
            container.addView(view);
            return view;
        }
    }

    private ViewPager bannerViewPager;

    private class SpaceItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpaceItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            if (parent.getChildLayoutPosition(view) == 0 || parent.getChildLayoutPosition(view) == 1) {
                return;
            }
            outRect.top = space;
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                if (currentPosition > colors.length) {
                    currentPosition = 0;
                }
                bannerViewPager.setCurrentItem(currentPosition);
                currentPosition++;

                handler.removeMessages(1);

                Message message = handler.obtainMessage();
                message.what = 1;
                handler.sendMessageDelayed(message, 2000);
            }
        }
    };

    public ClickGetMoreListener getGetMoreListener() {
        return getMoreListener;
    }

    public void setGetMoreListener(ClickGetMoreListener getMoreListener) {
        this.getMoreListener = getMoreListener;
    }

    public interface ClickGetMoreListener {
        void click();
    }

}
