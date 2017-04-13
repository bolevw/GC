package com.boger.game.gc.ui.fragment;

import android.content.res.Resources;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boger.game.gc.R;
import com.boger.game.gc.base.BaseFragment;
import com.boger.game.gc.model.SquareItemModel;
import com.boger.game.gc.model.SquareListModel;
import com.boger.game.gc.presenter.fragment.SquarePresenter;
import com.boger.game.gc.ui.activity.ForumLabelListActivity;
import com.boger.game.gc.utils.ImageLoad;
import com.boger.game.gc.widget.RecyclerViewCutLine;
import com.boger.game.gc.widget.pullToRefreshLayout.PullToRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * Created by liubo on 2016/3/22.
 */
public class SquareFragment extends BaseFragment {

    private SquarePresenter presenter;

    private List<SquareListModel> viewData = new ArrayList<>();

    @BindView(R.id.forumRecyclerView)
    RecyclerView forumRecyclerView;
    @BindView(R.id.ptr)
    PullToRefreshLayout ptr;
    @BindView(R.id.sectionTv)
    TextView sectionTv;

    private int sectionHeight;
    private LinearLayoutManager manager;
    private int mCurrentPosition = 0;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_square;
    }

    @Override
    protected void initViewData() {
        forumRecyclerView.setLayoutManager(manager = new LinearLayoutManager(getActivity()));
    }

    @Override
    protected void setListener() {
        forumRecyclerView.setAdapter(new RVAdapter());
        forumRecyclerView.addItemDecoration(new RecyclerViewCutLine(getResources().getDimensionPixelSize(R.dimen.cut_line), 0));
        forumRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                sectionHeight = sectionTv.getMeasuredHeight();
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                View view = manager.findViewByPosition(mCurrentPosition + 1);
                if (view != null) {
                    if (view.getTop() <= sectionHeight) {
                        sectionTv.setY(-(sectionHeight - view.getTop()));
                    } else {
                        sectionTv.setY(0);
                    }
                }

                if (mCurrentPosition != manager.findFirstVisibleItemPosition()) {
                    mCurrentPosition = manager.findFirstVisibleItemPosition();
                    sectionTv.setY(0);

                    updateSuspensionBar();
                }
            }
        });

        ptr.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void reset() {
                sectionTv.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPull() {
                sectionTv.setVisibility(View.GONE);
            }

            @Override
            public void onRefresh() {
                presenter.getData();
            }
        });
    }

    public void stopRefresh() {
        ptr.refreshComplete();
    }

    private void updateSuspensionBar() {
        sectionTv.setText(viewData.get(mCurrentPosition).getTitle());
    }

    @Override
    protected void bind() {
        this.presenter = new SquarePresenter();
        this.presenter.bind(this);
    }

    public void notifyChange(List<SquareListModel> model) {
        viewData.clear();
        viewData.addAll(model);
        updateSuspensionBar();
        forumRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.presenter.getData();
    }

    @Override
    protected void unbind() {
        this.presenter.unBind();
    }

    private class RVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(getActivity()).inflate(R.layout.item_square_container, parent, false);
            VH vh = new VH(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            SquareListModel data = viewData.get(position);

            VH vh = (VH) holder;
            vh.sectionTv.setText(data.getTitle());

            vh.setList(viewData, position);

        }

        @Override
        public void onViewRecycled(RecyclerView.ViewHolder holder) {
            super.onViewRecycled(holder);
            if (holder instanceof VH) {
                VH vh = (VH) holder;
                vh.list.clear();
            }
        }

        @Override
        public int getItemCount() {
            return viewData.size();
        }

        private class VH extends RecyclerView.ViewHolder {

            private TextView sectionTv;
            private ViewPager viewPager;
            private List<SquareItemModel> list = new ArrayList<>();

            public void setList(List<SquareListModel> data, int position) {

                this.list.clear();
                this.list.addAll(data.get(position).getList());
                viewPager.setAdapter(new ItemAdapter(list));
            }

            public VH(View itemView) {
                super(itemView);
                sectionTv = (TextView) itemView.findViewById(R.id.sectionTv);
                viewPager = (ViewPager) itemView.findViewById(R.id.squareVp);

            }
        }
    }

    private class ItemAdapter extends PagerAdapter {

        private TextView[] tvs = new TextView[9];
        private ImageView[] imageViews = new ImageView[9];
        private LinearLayout[] linearLayouts = new LinearLayout[9];

        List<SquareItemModel> data = new ArrayList<>();

        public ItemAdapter(List<SquareItemModel> data) {
            this.data.clear();
            this.data.addAll(data);
        }

        @Override
        public int getCount() {
            return data.size() / 9 + 1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return object.equals(view);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ViewGroup v = (ViewGroup) LayoutInflater.from(getBaseActivity()).inflate(R.layout.item_square_grid_layout, container, false);

            Resources res = getBaseActivity().getResources();
            String pack = getBaseActivity().getPackageName();
            for (int i = 0; i < tvs.length; i++) {
                int id = i + 1;
                int titleid = res.getIdentifier("tv" + id,//需要转换的资源名称
                        "id",        //资源类型
                        pack);//R类所在的包名
                Log.e(TAG, "instantiateItem: " + titleid);
                int imageId = res.getIdentifier("iv" + id,//需要转换的资源名称
                        "id",        //资源类型
                        pack);//R类所在的包名
                int lid = res.getIdentifier("l" + id,//需要转换的资源名称
                        "id",        //资源类型
                        pack);//R类所在的包名
                tvs[i] = (TextView) v.findViewById(titleid);
                imageViews[i] = (ImageView) v.findViewById(imageId);
                linearLayouts[i] = (LinearLayout) v.findViewById(lid);
            }

            for (int i = 0; i < Math.min(9, Math.max(0, data.size() - 9 * position)); i++) {
                final SquareItemModel model = data.get(i + 9 * position);
                tvs[i].setText(model.getTitle());

                ImageLoad.load(model.getImgSrc()).placeholder(R.color.percent50Black).into(imageViews[i]);
                linearLayouts[i].setVisibility(View.VISIBLE);
                linearLayouts[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ForumLabelListActivity.newInstance(getBaseActivity(), model.getHrefUrl());
                    }
                });
            }
            container.addView(v);
            return v;
        }

    }
}
