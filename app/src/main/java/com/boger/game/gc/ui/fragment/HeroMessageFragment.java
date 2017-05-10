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
import android.widget.TextView;

import com.boger.game.gc.R;
import com.boger.game.gc.base.BaseFragment;
import com.boger.game.gc.model.HeroDetailModel;
import com.boger.game.gc.model.HeroMessageModel;
import com.boger.game.gc.presenter.fragment.HeroMessagePresenter;
import com.boger.game.gc.utils.ToastUtils;
import com.boger.game.gc.widget.LoadingFailView;
import com.boger.game.gc.widget.LoadingView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by liubo on 2016/6/2.
 */
public class HeroMessageFragment extends BaseFragment implements AreaListFragment.OnItemClickListener, LoadingFailView.ReloadClickListener {
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

    private HeroMessageModel viewData = new HeroMessageModel();
    private AreaListFragment fragment = AreaListFragment.newInstance();

    private String serverName = DEFAULT_SERVER_NAME;
    private String playerName;

    private HeroMessagePresenter presenter;


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
        presenter.getHeroMessage(serverName, playerName);
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
        return R.layout.fragment_hreo_message;
    }

    @Override
    protected void initViewData() {
        serverName = cache.readStringValue("lolServerName", DEFAULT_SERVER_NAME);
        playerName = cache.readStringValue("lolPlayerName", "");
        areaButton.setText(serverName);
        userIdEditText.setText(playerName);
        if (getBaseActivity().getSupportActionBar() != null) {
            getBaseActivity().getSupportActionBar().setTitle("英雄信息");
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
        if (viewData.getContent() != null) {
            viewData.getContent().clear();
        }
        resultRecyclerView.getAdapter().notifyDataSetChanged();
    }

    public void setResult(HeroMessageModel model) {
        this.viewData = model;
        resultRecyclerView.getAdapter().notifyDataSetChanged();
    }

    public static HeroMessageFragment newInstance() {

        Bundle args = new Bundle();

        HeroMessageFragment fragment = new HeroMessageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void bind() {
        presenter = new HeroMessagePresenter(this);
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
            return new VH(LayoutInflater.from(getBaseActivity()).inflate(R.layout.item_hero_message, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            VH vh = (VH) holder;
            HeroDetailModel model = viewData.getContent().get(position);
            vh.heroNameTextView.setText(model.getChampionNameCN() + " " + model.getChampionName());
            vh.winRateTextView.setText(model.getMatchStat() + "场/" + model.getWinRate() + "%");
            vh.KDATextView.setText(model.getAverageKDA().toString() + "/" + model.getAverageKDARating());
            vh.damageTextView.setText(model.getAverageDamage().toString() + "点伤害/" + model.getAverageEarn().toString() + "金币");
            vh.MVPTextView.setText("MVP" + model.getTotalMVP() + "次");
        }

        @Override
        public int getItemCount() {
            return viewData.getContent() == null ? 0 : viewData.getContent().size();
        }

        private class VH extends RecyclerView.ViewHolder {
            private TextView winRateTextView;
            private TextView heroNameTextView;
            private TextView KDATextView;
            private TextView damageTextView;
            private TextView MVPTextView;

            public VH(View itemView) {
                super(itemView);
                winRateTextView = (TextView) itemView.findViewById(R.id.itemWinRateTextView);
                heroNameTextView = (TextView) itemView.findViewById(R.id.itemHeroNameTextView);
                KDATextView = (TextView) itemView.findViewById(R.id.itemKDATextView);
                damageTextView = (TextView) itemView.findViewById(R.id.itemDamageTextView);
                MVPTextView = (TextView) itemView.findViewById(R.id.itemMVPTextView);
            }
        }
    }
}