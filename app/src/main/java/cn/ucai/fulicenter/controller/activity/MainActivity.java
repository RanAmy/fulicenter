package cn.ucai.fulicenter.controller.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import cn.ucai.fulicenter.R;

public class MainActivity extends AppCompatActivity {
    RadioButton rbNewGoods,rbBoutique,rbCart,rbCategory,rbPersonal;
    RadioButton[] rbs = new RadioButton[4];

    int index,currentInde;  //  index:表示你以前选择的  currentIndex：表示当前要选择的

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRadioButton();
    }

    public void initRadioButton() {
        rbNewGoods = (RadioButton) findViewById(R.id.layout_new_goods);
        rbBoutique = (RadioButton) findViewById(R.id.layout_boutique);
        rbCategory = (RadioButton) findViewById(R.id.layout_category);
        rbCart = (RadioButton) findViewById(R.id.layout_cart);
        rbPersonal = (RadioButton) findViewById(R.id.layout_personal);

        rbs[0] = rbNewGoods;
        rbs[1] = rbBoutique;
        rbs[2] = rbCategory;
        rbs[3] = rbCart;
    }

    public void onCheckedChange(View view) {
        switch (view.getId()) {
            case R.id.layout_new_goods:
                index = 0;
                break;
            case R.id.layout_boutique:
                index = 1;
                break;
            case R.id.layout_category:
                 index = 2;
                break;
            case R.id.layout_cart:
                index = 3;
                break;
            case R.id.layout_personal:
                index = 4;
                break;
        }
        if (index != currentInde) {
            setRadioStatus();
        }
    }

    public void setRadioStatus() {
        for(int i=0;i<rbs.length;i++) {
            if (index != i) {
                rbs[i].setChecked(false);
            } else {
                rbs[i].setChecked(true);
            }
        }
        currentInde = index;
    }
}
