package com.boger.game.gc.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.boger.game.gc.R;
import com.boger.game.gc.base.BaseFragment;
import com.boger.game.gc.model.RecordModel;
import com.boger.game.gc.presenter.fragment.RecordPresenter;
import com.boger.game.gc.utils.ToastUtils;
import com.boger.game.gc.widget.LoadingFailView;
import com.boger.game.gc.widget.LoadingView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by liubo on 2016/5/30.
 */
public class RecordFragment extends BaseFragment implements AreaListFragment.OnItemClickListener, LoadingFailView.ReloadClickListener {
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

    private AreaListFragment fragment = AreaListFragment.newInstance();
    private String serverName = DEFAULT_SERVER_NAME;
    private String playerName;
    private RecordPresenter presenter;

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
        presenter.getRecord(serverName, playerName);
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
        return R.layout.fragment_search_user_info;
    }

    @Override
    protected void initViewData() {
        serverName = cache.readStringValue("lolServerName", DEFAULT_SERVER_NAME);
        playerName = cache.readStringValue("lolPlayerName", "");

        if (getBaseActivity().getSupportActionBar() != null) {
            getBaseActivity().getSupportActionBar().setTitle("战绩查询");
        }
        areaButton.setText(serverName);
        userIdEditText.setText(playerName);
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

    public static RecordFragment newInstance() {

        Bundle args = new Bundle();

        RecordFragment fragment = new RecordFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void setResult(RecordModel model) {
        resultTextView.setText(model.toString());
    }

    @Override
    protected void bind() {
        presenter = new RecordPresenter();
        presenter.bind(this);
    }

    @Override
    protected void setListener() {
        fragment.setOnItemClickListener(RecordFragment.this);
        loadingFailView.setReloadClickListener(RecordFragment.this);
    }

    @Override
    protected void unbind() {

    }

    @Override
    public void onItemClick(String area) {
        serverName = area;
        areaButton.setText(area);
    }

    @Override
    public void onReload() {
        search();

    }
}
