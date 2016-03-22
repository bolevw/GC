package com.example.administrator.gc.ui.fragment.childfragment;

import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.gc.R;
import com.example.administrator.gc.base.BaseFragment;
import com.example.administrator.gc.presenter.fragment.RecommendPresenter;
import com.example.administrator.gc.widget.VPIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/22.
 */
public class RecommendCFragment extends BaseFragment {

    private static final int TYPE_BANNER = 1;
    private static final int TYPE_HOT = 2;
    private static final int TYPE_NORMAL = 3;

    private RecommendPresenter presenter;


    int[] colors = new int[]{Color.RED, Color.WHITE, Color.BLACK, Color.YELLOW, Color.BLUE};

    int currentPosition = 0;

    private RecyclerView recyclerView;

    private List<String> hotList = new ArrayList<>();

    private List<String> recyclerData = new ArrayList<>();

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
    }


    public void notifyHotDataChange(List<String> list) {
        this.recyclerData.clear();
        this.recyclerData.addAll(list);
        recyclerView.getAdapter().notifyDataSetChanged();
        this.hotList.clear();
        this.hotList.addAll(list);
    }

    @Override
    protected void initView(View v) {
        recyclerView = (RecyclerView) v.findViewById(R.id.recommendRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new RecyclerViewAdapter());

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin);
        recyclerView.addItemDecoration(new SpaceItemDecoration(spacingInPixels));

    }

    @Override
    protected void bind() {
        this.presenter = new RecommendPresenter();
        presenter.bind(this);
    }

    @Override
    protected void setListener() {


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

        }

        @Override
        public int getItemCount() {
            return recyclerData.size();
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

        class BannerVH extends RecyclerView.ViewHolder {
            ViewPager bannerViewPager;
            VPIndicator vpIndicator;

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
        }

        class HotVh extends RecyclerView.ViewHolder {
            RecyclerView hotRecyclerView;

            public HotVh(View itemView) {
                super(itemView);

                hotRecyclerView = (RecyclerView) itemView.findViewById(R.id.hotRecyclerView);
                hotRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

                hotRecyclerView.setAdapter(new HotItemRecyclerViewAdapter());
            }

            class HotItemRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

                @Override
                public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hot_recyclerview, parent, false);
                    return new Vh(v);
                }

                @Override
                public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                    Vh vh = (Vh) holder;
                    if (position < hotList.size()) {
                        vh.textView.setText(hotList.get(position));
                    } else if (position == hotList.size()) {
                        vh.textView.setText("get more");
                    }
                }

                @Override
                public int getItemCount() {
                    return hotList.size() == 0 ? 0 : hotList.size() + 1;
                }

                class Vh extends RecyclerView.ViewHolder {
                    ImageView imageView;
                    TextView textView;

                    public Vh(View itemView) {
                        super(itemView);
                        imageView = (ImageView) itemView.findViewById(R.id.itemHotImageView);
                        textView = (TextView) itemView.findViewById(R.id.itemHotTextView);
                    }
                }
            }

        }

        class NormalVh extends RecyclerView.ViewHolder {

            public NormalVh(View itemView) {
                super(itemView);
            }
        }
    }

    class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 5;
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
        public Object instantiateItem(ViewGroup container, int position) {
            View view = new View(getActivity());
            view.setBackgroundColor(colors[position]);
            container.addView(view);
            return view;
        }
    }


    class SpaceItemDecoration extends RecyclerView.ItemDecoration {
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


}
