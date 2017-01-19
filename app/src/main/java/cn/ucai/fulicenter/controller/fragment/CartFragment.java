package cn.ucai.fulicenter.controller.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.controller.adapter.BoutiqueAdapter;
import cn.ucai.fulicenter.model.bean.BoutiqueBean;
import cn.ucai.fulicenter.model.bean.CartBean;
import cn.ucai.fulicenter.model.bean.User;
import cn.ucai.fulicenter.model.net.IModelUser;
import cn.ucai.fulicenter.model.net.ModelUser;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.utils.CommonUtils;
import cn.ucai.fulicenter.model.utils.ConvertUtils;
import cn.ucai.fulicenter.model.utils.L;
import cn.ucai.fulicenter.model.utils.SpaceItemDecoration;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {
    private static final String TAG = CategoryFragment.class.getSimpleName();
    public static final int ACTION_DOWN = 0;
    public static final int ACTION_PULL_DOWN = 1;
    User user;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.tvRefresh)
    TextView tvRefresh;


    LinearLayoutManager mManager;
    BoutiqueAdapter mAdapter;
    ArrayList<BoutiqueBean> boutiqueList;

    IModelUser model;
    int pageId;
    @BindView(R.id.tv_nothing)
    TextView tvNoMore;


    public CartFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initData() {
        pageId = 1;
        if (user != null) {
            downloadboutiquemList(ACTION_DOWN, pageId);
        }
    }

    private void downloadboutiquemList(final int action, int pageId) {
        model.getCart(getContext(), user.getMuserName(), new OnCompleteListener<CartBean[]>() {
            @Override
            public void onSuccess(CartBean[] result) {
                swipeRefreshLayout.setVisibility(View.VISIBLE);
                tvNoMore.setVisibility(View.GONE);
                if (result != null && result.length > 0) {
                    ArrayList<CartBean> mList = ConvertUtils.array2List(result);
                    Log.e("TAG", "mList.size==" + mList.size());
                    switch (action) {
                        case ACTION_DOWN:
//                            mAdapter.initData(mList);
                            break;
                        case ACTION_PULL_DOWN:
                            swipeRefreshLayout.setRefreshing(false);
                            tvRefresh.setVisibility(View.GONE);
//                            mAdapter.initData(mList);
                            break;
                    }
                } else {
                    swipeRefreshLayout.setVisibility(View.GONE);
                    tvNoMore.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(String error) {
                swipeRefreshLayout.setRefreshing(false);
                tvRefresh.setVisibility(View.GONE);
                CommonUtils.showLongToast(error);
                L.e("TAG", "error==" + error);
            }
        });
    }

    //  设置刷新时,刷新图标的颜色
    private void initView() {
        swipeRefreshLayout.setColorSchemeColors(
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow)
        );
        mManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mManager);
        recyclerView.setHasFixedSize(true); // 自适配
        recyclerView.addItemDecoration(new SpaceItemDecoration(12));  // 设置每个控件之间的间距
        boutiqueList = new ArrayList<>();
        mAdapter = new BoutiqueAdapter(getContext(), boutiqueList);
        recyclerView.setAdapter(mAdapter);
        swipeRefreshLayout.setVisibility(View.GONE);
        tvNoMore.setVisibility(View.VISIBLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_cart, container, false);
        ButterKnife.bind(this, layout);
        initView();
        model = new ModelUser();
        user = FuLiCenterApplication.getUser();
        initData();
        setListener();
        return layout;
    }

    private void setListener() {
        setPullDownListener();
    }

    private void setPullDownListener() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                tvRefresh.setVisibility(View.INVISIBLE);
                pageId = 1;
                downloadboutiquemList(ACTION_PULL_DOWN, pageId);
            }
        });
    }

    @OnClick(R.id.tv_nothing)
    public void onClick() {
        setPullDownListener();
    }

}
