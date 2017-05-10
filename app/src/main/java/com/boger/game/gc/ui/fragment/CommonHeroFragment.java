package com.boger.game.gc.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.boger.game.gc.R;
import com.boger.game.gc.base.BaseFragment;
import com.boger.game.gc.model.CommonHeroModel;
import com.boger.game.gc.presenter.fragment.CommonHeroPresenter;
import com.boger.game.gc.utils.ImageLoaderUtils;
import com.boger.game.gc.utils.ToastUtils;
import com.boger.game.gc.widget.LoadingFailView;
import com.boger.game.gc.widget.LoadingView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by liubo on 2016/5/30.
 */
public class CommonHeroFragment extends BaseFragment implements AreaListFragment.OnItemClickListener, LoadingFailView.ReloadClickListener {
    private static final String DEFAULT_SERVER_NAME = "选择大区";

    @BindView(R.id.lolAreaButton)
    AppCompatButton areaButton;
    @BindView(R.id.resultRecyclerView)
    RecyclerView resultRecyclerView;
    @BindView(R.id.userIdEditText)
    EditText userIdEditText;
    @BindView(R.id.searchButton)
    AppCompatButton searchButton;
    @BindView(R.id.loadingView)
    LoadingView loadingView;
    @BindView(R.id.loadingFailView)
    LoadingFailView loadingFailView;

    private CommonHeroModel viewData = new CommonHeroModel();
    private AreaListFragment fragment = AreaListFragment.newInstance();
    private String serverName = DEFAULT_SERVER_NAME;
    private String playerName;

    CommonHeroPresenter presenter;

    @OnClick(R.id.lolAreaButton)
    void show() {
        hideSoftKeyboard();
        getFragmentManager().beginTransaction().addToBackStack(AreaListFragment.class.getSimpleName()).add(R.id.fragmentContainer, fragment).commit();
    }

    @OnClick(R.id.searchButton)
    void search() {
        playerName = userIdEditText.getText().toString().trim();
        if (TextUtils.isEmpty(playerName)) {
            ToastUtils.showNormalToast("请输入玩家名称");
            return;
        }
        if (serverName.equals(DEFAULT_SERVER_NAME)) {
            ToastUtils.showNormalToast("请选择大区");
            return;
        }
        resetLoad();
        hideSoftKeyboard();
        cache.saveStringValue("lolServerName", serverName);
        cache.saveStringValue("lolPlayerName", playerName);
        presenter.getCommonHero(serverName, playerName);
    }

    public void loading() {
        loadingView.start();
        loadingView.setVisibility(View.VISIBLE);
    }

    public void stopLoading() {
        loadingView.stopAnim();
        loadingView.setVisibility(View.GONE);

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_common_hero;
    }

    @Override
    protected void initViewData() {
        serverName = cache.readStringValue("lolServerName", DEFAULT_SERVER_NAME);
        playerName = cache.readStringValue("lolPlayerName", "");

        areaButton.setText(serverName);
        userIdEditText.setText(playerName);
        if (getBaseActivity().getSupportActionBar() != null) {
            getBaseActivity().getSupportActionBar().setTitle("常用英雄");
        }
    }

    public void loadingFail() {
        loadingView.stopAnim();
        loadingView.setVisibility(View.GONE);
        loadingFailView.setVisibility(View.VISIBLE);
        loadingFailView.setLoadFailText("召唤师数据被大龙吃掉了！");
    }

    private void resetLoad() {
        loadingView.start();
        loadingView.setVisibility(View.GONE);
        loadingFailView.setVisibility(View.GONE);
        if (viewData.getHerostr() != null) {
            viewData.getHerostr().clear();
        }
        resultRecyclerView.getAdapter().notifyDataSetChanged();
    }

    public void setResult(CommonHeroModel model) {
        this.viewData = model;
        resultRecyclerView.getAdapter().notifyDataSetChanged();
    }


    public static CommonHeroFragment newInstance() {

        Bundle args = new Bundle();

        CommonHeroFragment fragment = new CommonHeroFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void bind() {
        presenter = new CommonHeroPresenter(this);
    }

    @Override
    protected void setListener() {
        fragment.setOnItemClickListener(this);
        loadingFailView.setReloadClickListener(this);
        resultRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseActivity()));
        resultRecyclerView.setAdapter(new RVAdapter());

    }

    @Override
    public void onItemClick(String area) {
        areaButton.setText(area);
        serverName = area;
    }

    @Override
    public void onReload() {
        search();
    }

    private class RVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new VH(LayoutInflater.from(getBaseActivity()).inflate(R.layout.item_common_hero, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            VH vh = (VH) holder;
            vh.heroNameTextView.setText(viewData.getHerostr().get(position).getAttr().getTitle());
            ImageLoaderUtils.load(viewData.getHerostr().get(position).getAttr().getSrc(), vh.heroImageView);
        }

        @Override
        public int getItemCount() {
            return viewData.getHerostr() == null ? 0 : viewData.getHerostr().size();
        }

        private class VH extends RecyclerView.ViewHolder {
            private ImageView heroImageView;
            private TextView heroNameTextView;

            public VH(View itemView) {
                super(itemView);
                heroImageView = (ImageView) itemView.findViewById(R.id.itemHeroImageView);
                heroNameTextView = (TextView) itemView.findViewById(R.id.itemHeroNameTextView);
            }
        }
    }
}
