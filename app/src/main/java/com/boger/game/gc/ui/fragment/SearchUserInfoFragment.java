package com.boger.game.gc.ui.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.boger.game.gc.R;
import com.boger.game.gc.base.BaseFragment;
import com.boger.game.gc.model.PlayerInfoModel;
import com.boger.game.gc.presenter.fragment.SearchUserInfoPresenter;
import com.boger.game.gc.ui.activity.LoginActivity;
import com.boger.game.gc.utils.ImageLoaderUtils;
import com.boger.game.gc.utils.ToastUtils;
import com.boger.game.gc.widget.LoadingFailView;
import com.boger.game.gc.widget.LoadingView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by liubo on 2016/5/24.
 */
public class SearchUserInfoFragment extends BaseFragment implements AreaListFragment.OnItemClickListener, LoadingFailView.ReloadClickListener {
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
    @BindView(R.id.avatarImageView)
    ImageView avatarImageView;
    @BindView(R.id.setAvatarButton)
    Button setAvatarButton;
    @BindView(R.id.avatarContainer)
    RelativeLayout avatarContainer;
    @BindView(R.id.setSuccessTextView)
    TextView setSuccessTextView;
    @BindView(R.id.progressBar)
    ContentLoadingProgressBar progressBar;

    private SearchUserInfoPresenter presenter;
    private String avatarUrl;

    private AreaListFragment fragment = AreaListFragment.newInstance();
    private String serverName = DEFAULT_SERVER_NAME;
    private String playerName;

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
        presenter.search(serverName, playerName);
    }

    @OnClick(R.id.setAvatarButton)
    void setAvatar() {
        if (!cache.readBooleanValue("isLogin", false)) {
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(getBaseActivity());
                final AlertDialog adl = builder.create();
                builder.setTitle("请登录！")
                        .setMessage("请先登录，在进行关注！")
                        .setNegativeButton("暂不关注", null)
                        .setPositiveButton("登录", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                adl.dismiss();
                                startActivity(new Intent(getBaseActivity(), LoginActivity.class));
                            }
                        });
                builder.show();
            }
            return;
        }
        String id = cache.readStringValue("userId", null);
        presenter.saveAvatar(avatarUrl, id);
    }

    private void resetLoad() {
        loadingView.start();
        loadingView.setVisibility(View.GONE);
        loadingFailView.setVisibility(View.GONE);
        resultTextView.setText("");
        avatarContainer.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        setSuccessTextView.setVisibility(View.INVISIBLE);
    }

    public static SearchUserInfoFragment newInstance() {

        Bundle args = new Bundle();

        SearchUserInfoFragment fragment = new SearchUserInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search_user_info, container, false);
        ButterKnife.bind(this, v);
        serverName = cache.readStringValue("lolServerName", DEFAULT_SERVER_NAME);
        playerName = cache.readStringValue("lolPlayerName", "");
        getBaseActivity().getSupportActionBar().setTitle("玩家信息");
        return v;
    }

    @Override
    protected void initView(View v) {
        areaButton.setText(serverName);
        userIdEditText.setText(playerName);
    }

    public void setResult(PlayerInfoModel model) {
        avatarContainer.setVisibility(View.VISIBLE);
        ImageLoaderUtils.load(model.getPortrait(), avatarImageView);
        resultTextView.setText("召唤师等级：" + model.getLevel() + " 战斗力：" + model.getZhandouli() + " 被赞：" + model.getGood());
        avatarUrl = model.getPortrait();
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

    public void saveAva() {
        progressBar.setVisibility(View.VISIBLE);
        setSuccessTextView.setVisibility(View.INVISIBLE);
        setSuccessTextView.setText("设置成功");
        setAvatarButton.setClickable(false);
    }

    public void saveAvaSuccess() {
        progressBar.setVisibility(View.INVISIBLE);
        setSuccessTextView.setVisibility(View.VISIBLE);
        setAvatarButton.setClickable(true);
    }

    public void saveAvaFail() {
        progressBar.setVisibility(View.INVISIBLE);
        setSuccessTextView.setVisibility(View.VISIBLE);
        setAvatarButton.setClickable(true);
        setSuccessTextView.setText("设置失败");
    }

    @Override
    protected void bind() {
        presenter = new SearchUserInfoPresenter();
        presenter.bind(this);
    }

    @Override
    protected void setListener() {
        fragment.setOnItemClickListener(SearchUserInfoFragment.this);
        loadingFailView.setReloadClickListener(SearchUserInfoFragment.this);
    }

    @Override
    protected void unbind() {
        presenter.unBind();
    }

    @Override
    public void onItemClick(String area) {
        this.serverName = area;
        areaButton.setText(area);
    }

    @Override
    public void onReload() {
        search();
    }
}
