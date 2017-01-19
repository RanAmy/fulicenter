package cn.ucai.fulicenter.controller.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.controller.adapter.NewGoodsAdapter;
import cn.ucai.fulicenter.model.bean.CollectBean;
import cn.ucai.fulicenter.model.bean.NewGoodsBean;
import cn.ucai.fulicenter.model.bean.User;
import cn.ucai.fulicenter.model.net.DisplayUtils;
import cn.ucai.fulicenter.model.net.IModelUser;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.utils.ConvertUtils;
import cn.ucai.fulicenter.model.utils.L;
import cn.ucai.fulicenter.model.utils.SpaceItemDecoration;

public class CollectsActivity extends AppCompatActivity {
    private static final String TAG = CollectsActivity.class.getSimpleName();
    User user;
    IModelUser model;
    GridLayoutManager gm;
    NewGoodsAdapter mAdapter;
    int pageId = 1;

    @BindView(R.id.tvRefresh)
    TextView tvRefresh;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collects);
        ButterKnife.bind(this);
        DisplayUtils.initBackWithTitle(this,"收藏的宝贝");
        user = FuLiCenterApplication.getUser();
        if (user == null) {  //  如果用户为空，关闭
            finish();
        } else {  //  若用户不为空，则实例化数据
            initView();
            initData();
        }
    }

    private void initData() {
        model.getCollects(this, user.getMuserName(),pageId, I.PAGE_SIZE_DEFAULT, new OnCompleteListener<CollectBean[]>() {
            @Override
            public void onSuccess(CollectBean[] result) {
                if (result == null) {

                } else {
                    ArrayList<CollectBean> list = ConvertUtils.array2List(result);
                    L.e(TAG, "list=" + list.size());
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void initView() {
        srl.setColorSchemeColors(
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow)
        );
        gm = new GridLayoutManager(this, I.COLUM_NUM);
        rv.addItemDecoration(new SpaceItemDecoration(12));
        rv.setLayoutManager(gm);
        rv.setHasFixedSize(true);
        mAdapter = new NewGoodsAdapter(this, new ArrayList<NewGoodsBean>());
        rv.setAdapter(mAdapter);
    }
}
