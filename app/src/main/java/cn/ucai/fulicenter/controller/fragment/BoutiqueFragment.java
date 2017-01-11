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
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.controller.adapter.BoutiqueAdapter;
import cn.ucai.fulicenter.model.bean.BoutiqueBean;
import cn.ucai.fulicenter.model.net.IModelBoutique;
import cn.ucai.fulicenter.model.net.ModelBoutique;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.utils.CommonUtils;
import cn.ucai.fulicenter.model.utils.ConvertUtils;
import cn.ucai.fulicenter.model.utils.L;
import cn.ucai.fulicenter.model.utils.SpaceItemDecoration;


public class BoutiqueFragment extends Fragment {
    public static final int ACTION_DOWN = 0;
    public static final int ACTION_PULL_DOWN = 1;
    public static final int ACTION_PULL_UP = 2;

    @BindView(R.id.tvRefresh)
    TextView tvRefresh;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    LinearLayoutManager mManager;
    BoutiqueAdapter mAdapter;
    ArrayList<BoutiqueBean> boutiqueList;

    IModelBoutique model;
    int pageId;

    public BoutiqueFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initData() {
        pageId = 1;
        downloadboutiquemList(ACTION_DOWN,pageId);
    }

    private void downloadboutiquemList(final int action, int pageId) {
        model.downData(getContext(),new OnCompleteListener<BoutiqueBean[]>() {
            @Override
            public void onSuccess(BoutiqueBean[] result) {
                if (result != null && result.length > 0) {
                    mAdapter.setMore(true);
                    ArrayList<BoutiqueBean> mList = ConvertUtils.array2List(result);
                    Log.e("TAG", "mList.size==" + mList.size());

                    switch (action) {
                        case ACTION_DOWN:
                            mAdapter.initData(mList);
                            break;
                        case ACTION_PULL_DOWN:
                            swipeRefreshLayout.setRefreshing(false);
                            tvRefresh.setVisibility(View.GONE);
                            mAdapter.initData(mList);
                            break;
                        case ACTION_PULL_UP:
                            swipeRefreshLayout.setRefreshing(false);
                            mAdapter.addData(mList);
                            break;
                    }
                } else {
                    mAdapter.setMore(false);


                }
                if (!mAdapter.isMore()) {
                    mAdapter.setFooter("没有数据可加载");
                } else {
                    mAdapter.setFooter("加载更多数据");
                }
            }

            @Override
            public void onError(String error) {
                swipeRefreshLayout.setRefreshing(false);
                tvRefresh.setVisibility(View.GONE);
                mAdapter.setMore(false);
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_new_goods, container, false);
        ButterKnife.bind(this, layout);
        initView();
        model = new ModelBoutique();
        initData();
        setmListener();
        return layout;
    }

    private void setmListener() {
        setPullUpmListener();
        setPullDownmListener();
    }

    private void setPullDownmListener() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                tvRefresh.setVisibility(View.INVISIBLE);
                pageId = 1;
                downloadboutiquemList(ACTION_PULL_DOWN,pageId);
            }
        });
    }

    private void setPullUpmListener() {
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastPosition ;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && mAdapter.isMore()
                        && lastPosition == mAdapter.getItemCount() - 1) {
                    pageId ++;
                    downloadboutiquemList(ACTION_PULL_UP,pageId);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastPosition= mManager.findLastVisibleItemPosition();
            }
        });
    }

}
