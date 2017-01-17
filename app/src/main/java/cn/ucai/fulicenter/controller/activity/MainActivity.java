package cn.ucai.fulicenter.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.controller.fragment.BoutiqueFragment;
import cn.ucai.fulicenter.controller.fragment.CategoryFragment;
import cn.ucai.fulicenter.controller.fragment.NewGoodsFragment;
import cn.ucai.fulicenter.controller.fragment.PersonalFragment;
import cn.ucai.fulicenter.model.utils.L;
import cn.ucai.fulicenter.model.utils.MFGT;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    RadioButton[] rbs = new RadioButton[5];

    int index, currentIndex;  //  index:表示你以前选择的  currentIndex：表示当前要选择的

    @BindView(R.id.layout_new_goods)
    RadioButton layoutNewGoods;
    @BindView(R.id.layout_boutique)
    RadioButton layoutBoutique;
    @BindView(R.id.layout_category)
    RadioButton layoutCategory;
    @BindView(R.id.layout_cart)
    RadioButton layoutCart;
    @BindView(R.id.layout_personal)
    RadioButton layoutPersonal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initRadioButton();
    }

    public void initRadioButton() {
        rbs[0] = layoutNewGoods;
        rbs[1] = layoutBoutique;
        rbs[2] = layoutCategory;
        rbs[3] = layoutCart;
        rbs[4] = layoutPersonal;
    }

    public void onCheckedChange(View view) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (view.getId()) {
            case R.id.layout_new_goods:
                index = 0;
                Log.e("main", ">>>>>>>执行到这里");
                transaction.replace(R.id.layout_content, new NewGoodsFragment()).commit();
                break;
            case R.id.layout_boutique:
                index = 1;
                transaction.replace(R.id.layout_content, new BoutiqueFragment()).commit();
                break;
            case R.id.layout_category:
                index = 2;
                transaction.replace(R.id.layout_content, new CategoryFragment()).commit();
                break;
            case R.id.layout_cart:
                index = 3;
                break;
            case R.id.layout_personal:
                if (FuLiCenterApplication.getUser() == null) {
                    MFGT.gotoLogin(this);
                } else {
                    index = 4;
                    transaction.replace(R.id.layout_content, new PersonalFragment()).commit();
                }
                break;
        }
        if (index != currentIndex) {
            setRadioStatus();
        }
    }

    public void setRadioStatus() {
        for (int i = 0; i < rbs.length; i++) {
            if (index != i) {
                rbs[i].setChecked(false);
            } else {
                rbs[i].setChecked(true);
            }
        }
        currentIndex = index;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        L.e(TAG, "onResume,currentIndex=" + currentIndex + ",index"
                + index + ",user=" + FuLiCenterApplication.getUser());
        setRadioStatus();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == I.REQUEST_CODE_LOGIN) {
            index = 4;
            setRadioStatus();
        }
    }
}
