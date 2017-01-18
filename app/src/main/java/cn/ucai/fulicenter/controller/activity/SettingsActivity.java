package cn.ucai.fulicenter.controller.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;


import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.model.bean.Result;
import cn.ucai.fulicenter.model.bean.User;
import cn.ucai.fulicenter.model.net.DisplayUtils;
import cn.ucai.fulicenter.model.net.IModelUser;
import cn.ucai.fulicenter.model.net.ModelUser;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.net.SharePreferenceUtils;
import cn.ucai.fulicenter.model.utils.CommonUtils;
import cn.ucai.fulicenter.model.utils.ImageLoader;
import cn.ucai.fulicenter.model.utils.L;
import cn.ucai.fulicenter.model.utils.MFGT;
import cn.ucai.fulicenter.model.utils.OnSetAvatarListener;
import cn.ucai.fulicenter.model.utils.ResultUtils;

public class SettingsActivity extends AppCompatActivity {
    private static final String TAG = SettingsActivity.class.getSimpleName();

    OnSetAvatarListener mOnSetAvatarListener;
    IModelUser model;

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
        DisplayUtils.initBackWithTitle(this, "设置");
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

    @OnClick(R.id.tv_user_profile_nick)
    public void updateNick() {
        MFGT.gotoUpdateNick(this);
    }

    @OnClick(R.id.tv_user_profile_name)
    public void onClickUserName() {
        CommonUtils.showLongToast(R.string.username_connot_be_modify);
    }

    @OnClick(R.id.layout_user_profile_avatar)
    public void onClickAvatar() {
        mOnSetAvatarListener = new OnSetAvatarListener(this,
                R.id.layout_user_profile_avatar,
                FuLiCenterApplication.getUser().getMuserName(),
                I.AVATAR_TYPE_USER_PATH);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, "onActivityResult,requestCode=" + requestCode
                + "resultOk=" + RESULT_OK
                + ",requestCode=" + requestCode
                + ",user=" + FuLiCenterApplication.getUser());
        if (requestCode != RESULT_OK) {
            return;
        }
        if(requestCode == I.REQUEST_CODE_NICK) {
            tvUserProfileNick.setText(FuLiCenterApplication.getUser().getMuserNick());
        } else if (requestCode == OnSetAvatarListener.REQUEST_CROP_PHOTO) {
            uploadAvatar();
        } else {
            mOnSetAvatarListener.setAvatar(requestCode,data,ivUserProfileAvatar);
        }
    }

    private void uploadAvatar() {
        User user = FuLiCenterApplication.getUser();
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.update_user_avatar));
        dialog.show();
        File file = null;
        file = new File(String.valueOf(OnSetAvatarListener.getAvatarFile(this,
                OnSetAvatarListener.getAvatarPath(this,
                        "/" + user.getMuserName() + user.getMavatarSuffix()))));
        L.e(TAG,"file="+file.getAbsolutePath());
        model = new ModelUser();
        model.uploadAvatar(this,
//                FuLiCenterApplication.getUser().getMuserName(),
                user.getMuserName(),
                file,
                new OnCompleteListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        L.e(TAG, "s=" + s);
                        int msg = R.string.update_user_avatar_fail;
                        if (s != null) {
                            Result result = ResultUtils.getResultFromJson(s, User.class);
                            if (result != null) {
                                if (result.isRetMsg()) {
                                    msg = R.string.update_user_avatar_success;
                                }
                            }
                        }
                        CommonUtils.showLongToast(msg);
                        dialog.dismiss();
                    }

                    @Override
                    public void onError(String error) {
                        CommonUtils.showLongToast(error);
                        dialog.dismiss();
                    }
                });
    }
}
