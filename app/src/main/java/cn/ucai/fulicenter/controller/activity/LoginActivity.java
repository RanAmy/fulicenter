package cn.ucai.fulicenter.controller.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.model.bean.Result;
import cn.ucai.fulicenter.model.bean.User;
import cn.ucai.fulicenter.model.dao.UserDao;
import cn.ucai.fulicenter.model.net.IModelUser;
import cn.ucai.fulicenter.model.net.ModelUser;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.net.SharePreferenceUtils;
import cn.ucai.fulicenter.model.utils.CommonUtils;
import cn.ucai.fulicenter.model.utils.L;
import cn.ucai.fulicenter.model.utils.MFGT;
import cn.ucai.fulicenter.model.utils.ResultUtils;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();
    IModelUser model;

    @BindView(R.id.iv_login_back)
    ImageView ivLoginBack;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_free_register)
    Button btnFreeLongin;
    @BindView(R.id.et_userName)
    EditText etUserName;
    @BindView(R.id.et_password)
    EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_login_back, R.id.btn_login, R.id.btn_free_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_login_back:
                MFGT.finish(this);
                break;
            case R.id.btn_login:
                checkInput();
                break;
            case R.id.btn_free_register:
                MFGT.gotoRegister(this);
                break;
        }
    }

    private void checkInput() {
        String username = etUserName.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            etUserName.setError(getString(R.string.user_name_connot_be_empty));
            etUserName.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            etPassword.setError(getString(R.string.password_connot_be_empty));
            etPassword.requestFocus();
        } else {
            login(username, password);
        }
    }

    private void login(String username, String password) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.logining));
        model = new ModelUser();
        model.login(this, username, password, new OnCompleteListener<String>() {
            @Override
            public void onSuccess(String s) {
                if (s != null) {  //  因为数据库中已经添加了用户了，所以要使用User.class,而不是result.class
                    Result result = ResultUtils.getResultFromJson(s, User.class);
                    Log.e(TAG, "result=" + result);
                    if (result != null) {
                        if (result.isRetMsg()) {
                            User user = (User) result.getRetData();
                            boolean saveUser = UserDao.getInstance().saveUser(user);
                            Log.e(TAG, "saveUser=" + saveUser);
                            if (saveUser) {
                                SharePreferenceUtils.getInstance(LoginActivity.this).saveUser(user.getMuserName());
                                FuLiCenterApplication.setUser(user);
                                setResult(RESULT_OK);
                                MFGT.finish(LoginActivity.this);
                            }
                        } else {
                            if (result.getRetCode() == I.MSG_LOGIN_UNKNOW_USER) {
                                CommonUtils.showLongToast(getString(R.string.login_fail_unknow_user));
                            }
                            if (result.getRetCode() == I.MSG_LOGIN_ERROR_PASSWORD) {
                                CommonUtils.showLongToast(getString(R.string.login_fail_error_password));
                            }
                        }
                    } else {
                        CommonUtils.showLongToast(getString(R.string.login_fail));
                    }
                } else {
                    CommonUtils.showLongToast(getString(R.string.login_fail));
                }
                dialog.dismiss();
            }

            @Override
            public void onError(String error) {
                dialog.dismiss();
                L.e(TAG,"error="+error);
                CommonUtils.showLongToast(error);
            }
        });
    }
}
