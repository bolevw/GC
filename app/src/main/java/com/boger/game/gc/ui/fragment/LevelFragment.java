package com.boger.game.gc.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.boger.game.gc.R;
import com.boger.game.gc.base.BaseFragment;
import com.boger.game.gc.model.LevelModel;
import com.boger.game.gc.presenter.fragment.LevelPresenter;
import com.boger.game.gc.utils.ToastUtils;
import com.boger.game.gc.widget.LoadingFailView;
import com.boger.game.gc.widget.LoadingView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by liubo on 2016/5/30.
 */
public class LevelFragment extends BaseFragment implements AreaListFragment.OnItemClickListener, LoadingFailView.ReloadClickListener {

    private static final String DEFAULT_SERVER_NAME = "选择大区";

    @BindView(R.id.lolAreaButton)
    AppCompatButton areaButton;
    @BindView(R.id.resultTextView)
    TextView resultTextView;
    @BindView(R.id.userIdEditText)
    EditText userIdEditText;
    @BindView(R.id.searchButton)
    AppCompatButton searchButton;
    @BindView(R.id.loadingView)
    LoadingView loadingView;
    @BindView(R.id.loadingFailView)
    LoadingFailView loadingFailView;

    private AreaListFragment fragment = AreaListFragment.newInstance(true);
    private String playerName;
    private String serverNum = DEFAULT_SERVER_NAME;

    private LevelPresenter presenter;

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
        if (serverNum.equals(DEFAULT_SERVER_NAME)) {
            ToastUtils.showNormalToast("请选择大区");
            return;
        }
        resetLoad();
        hideSoftKeyboard();
        cache.saveStringValue("lolServerNums", serverNum);
        cache.saveStringValue("lolPlayerName", playerName);
        presenter.getPlayerLevel(serverNum, playerName);
    }

    public void loading() {
        loadingView.start();
        loadingView.setVisibility(View.VISIBLE);
    }

    public void stopLoading() {
        loadingView.stopAnim();
        loadingView.setVisibility(View.GONE);

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
        resultTextView.setText("");
    }

    public void setResult(LevelModel model) {
        resultTextView.setText(model.toString());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search_user_info, container, false);
        ButterKnife.bind(this, v);
        playerName = cache.readStringValue("lolPlayerName", "");
        serverNum = DEFAULT_SERVER_NAME;
        return v;
    }

    public static LevelFragment newInstance() {

        Bundle args = new Bundle();

        LevelFragment fragment = new LevelFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initView(View v) {
        areaButton.setText(DEFAULT_SERVER_NAME);
        userIdEditText.setText(playerName);
        if (getBaseActivity().getSupportActionBar() != null) {
            getBaseActivity().getSupportActionBar().setTitle("玩家段位");
        }
    }

    @Override
    protected void bind() {
        presenter = new LevelPresenter();
        presenter.bind(this);
    }

    @Override
    protected void setListener() {
        fragment.setOnItemClickListener(this);
        loadingFailView.setReloadClickListener(this);
    }

    @Override
    public void onItemClick(String area) {
        serverNum = area.substring(area.indexOf(" ") + 1, area.length());
        areaButton.setText(area);
    }

    @Override
    public void onReload() {
        search();
    }

    @Override
    protected void unbind() {
        presenter.unBind();
    }
}
