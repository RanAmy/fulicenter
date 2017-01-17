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
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.model.bean.Result;
import cn.ucai.fulicenter.model.net.IModelUser;
import cn.ucai.fulicenter.model.net.ModelUser;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.utils.CommonUtils;
import cn.ucai.fulicenter.model.utils.MFGT;
import cn.ucai.fulicenter.model.utils.ResultUtils;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = RegisterActivity.class.getSimpleName();
    IModelUser model;

    @BindView(R.id.iv_register_back)
    ImageView ivRegisterBack;
    @BindView(R.id.et_userName)
    TextView etUserName;
    @BindView(R.id.et_userNick)
    TextView etUserNick;
    @BindView(R.id.et_password)
    TextView etPassword;
    @BindView(R.id.et_queren_password)
    EditText etQuerenPassword;
    @BindView(R.id.btn_free_register)
    Button btnFreeRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_register_back, R.id.btn_free_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_register_back:
                MFGT.finish(this);
                break;
            case R.id.btn_free_register:
                checkInput();
                MFGT.finish(this);
                break;
        }
    }

    /**
     * 免费注册
     */
    private void checkInput() {
        String username = etUserName.getText().toString().trim();
        String usernick = etUserNick.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirm = etQuerenPassword.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            etUserName.setError(getResources().getString(R.string.user_name_connot_be_empty));
            etUserName.requestFocus();
        } else if (!username.matches("[a-zA-Z]\\w{5,15}")) {
            etUserName.setError(getResources().getString(R.string.illegal_user_name));
            etUserName.requestFocus();
        } else if (TextUtils.isEmpty(usernick)) {
            etUserNick.setError(getResources().getString(R.string.nick_name_connot_be_empty));
            etUserNick.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            etPassword.setError(getResources().getString(R.string.password_connot_be_empty));
            etPassword.requestFocus();
        } else if (TextUtils.isEmpty(confirm)) {
            etQuerenPassword.setError(getResources().getString(R.string.confirm_password_connot_be_empty));
            etQuerenPassword.requestFocus();
        } else if (!password.equals(confirm)) {
            etQuerenPassword.setError(getResources().getString(R.string.two_input_password));
            etQuerenPassword.requestFocus();
        } else {
            register(username, usernick, password);
        }
    }

    private void register(String username, String usernick, String password) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.registering));
        dialog.show();
        model = new ModelUser();
        model.register(this, username, usernick, password, new OnCompleteListener<String>() {
            @Override
            public void onSuccess(String s) {
                if (s != null) {
                    Result result = ResultUtils.getResultFromJson(s, Result.class);
                    Log.e(TAG, "result=" + result);
                    if (result != null) {
                        if (result.isRetMsg()) {
                            // register success
                            CommonUtils.showLongToast(R.string.register_success);
                            MFGT.finish(RegisterActivity.this);
                        } else {  //  register fail
                            CommonUtils.showLongToast(R.string.register_fail_exists);
                        }
                    }
                    dialog.dismiss();
                }
            }

            @Override   //  注册失败
            public void onError(String error) {
                dialog.dismiss();
                CommonUtils.showLongToast(error);
            }
        });
    }

}
