package cn.ucai.fulicenter.controller.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.model.bean.User;
import cn.ucai.fulicenter.model.net.SharePreferenceUtils;
import cn.ucai.fulicenter.model.utils.ImageLoader;
import cn.ucai.fulicenter.model.utils.MFGT;

public class SettingsActivity extends AppCompatActivity {

    @BindView(R.id.iv_user_profile_avatar)
    ImageView ivUserProfileAvatar;
    @BindView(R.id.tv_user_profile_name)
    TextView tvUserProfileName;
    @BindView(R.id.tv_user_profile_nick)
    TextView tvUserProfileNick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        User user = FuLiCenterApplication.getUser();
        if (user != null) {
            loadUserInfo(user);
        } else {
            MFGT.gotoLogin(this);
        }
    }

    private void loadUserInfo(User user) {
        ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user), this, ivUserProfileAvatar);
        tvUserProfileName.setText(user.getMuserName());
        tvUserProfileNick.setText(user.getMuserNick());
    }

    //  退出登录的方法
    @OnClick(R.id.btn_logout)
    public void onClick() {
        FuLiCenterApplication.setUser(null);
        SharePreferenceUtils.getInstance(this).removeUser();
        MFGT.gotoLogin(this);
        finish(); //  回到个人中心界面，而不是Settings界面
    }
}
