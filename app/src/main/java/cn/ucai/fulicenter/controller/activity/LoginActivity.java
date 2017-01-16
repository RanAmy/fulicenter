package cn.ucai.fulicenter.controller.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.model.utils.MFGT;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.iv_login_back)
    ImageView ivLoginBack;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_free_register)
    Button btnFreeLongin;

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
                break;
            case R.id.btn_login:
                break;
            case R.id.btn_free_register:
                MFGT.gotoRegister(this);
                break;
        }
    }
}
