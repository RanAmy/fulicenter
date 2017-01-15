package cn.ucai.fulicenter.controller.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
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
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.controller.adapter.NewGoodsAdapter;
import cn.ucai.fulicenter.model.bean.NewGoodsBean;
import cn.ucai.fulicenter.model.net.IModelNewGoods;
import cn.ucai.fulicenter.model.net.ModelNewGoods;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.utils.CommonUtils;
import cn.ucai.fulicenter.model.utils.ConvertUtils;
import cn.ucai.fulicenter.model.utils.L;
import cn.ucai.fulicenter.model.utils.SpaceItemDecoration;


public class NewGoodsFragment extends Fragment {
    public static final int ACTION_DOWN = 0;
    public static final int ACTION_PULL_DOWN = 1;
    public static final int ACTION_PULL_UP = 2;

    @BindView(R.id.tvRefresh)
    TextView tvRefresh;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    GridLayoutManager mManager;
    NewGoodsAdapter mAdapter;
    ArrayList<NewGoodsBean> newGoodsList;

    IModelNewGoods model;
    int pageId;

    public NewGoodsFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initData() {
        pageId = 1;
        downloadNewGoodsList(ACTION_DOWN,pageId);
    }

    private void downloadNewGoodsList(final int action, int pageId) {
        int catId = getActivity().getIntent().getIntExtra(I.NewAndBoutiqueGoods.CAT_ID, I.CAT_ID);
        model.downData(getContext(),catId, pageId, new OnCompleteListener<NewGoodsBean[]>() {
            @Override
            public void onSuccess(NewGoodsBean[] result) {
                if (result != null && result.length > 0) {
                    mAdapter.setMore(true);
                    ArrayList<NewGoodsBean> list = ConvertUtils.array2List(result);
                    Log.e("TAG", "list.size==" + list.size());

                    switch (action) {
                        case ACTION_DOWN:
                            mAdapter.initData(list);
                            break;
                        case ACTION_PULL_DOWN:
                            swipeRefreshLayout.setRefreshing(false);
                            tvRefresh.setVisibility(View.GONE);
                            mAdapter.initData(list);
                            break;
                        case ACTION_PULL_UP:
                            swipeRefreshLayout.setRefreshing(false);
                            mAdapter.addData(list);
                            break;
                    }
                } else {
                    mAdapter.setMore(false);
                }
                if (mAdapter.isMore()) {
                    mAdapter.setFooter("加载更多数据");
                } else {
                    mAdapter.setFooter("没有数据可加载");
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
        mManager = new GridLayoutManager(getContext(), I.COLUM_NUM);
        recyclerView.setLayoutManager(mManager);
        recyclerView.setHasFixedSize(true); // 自适配
        recyclerView.addItemDecoration(new SpaceItemDecoration(12));  // 设置每个控件之间的间距
        newGoodsList = new ArrayList<>();
        mAdapter = new NewGoodsAdapter(getContext(), newGoodsList);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_new_goods, container, false);
        ButterKnife.bind(this, layout);
        initView();
        model = new ModelNewGoods();
        initData();
        setListener();
        return layout;
    }

    private void setListener() {
        setPullUpListener();
        setPullDownListener();
    }

    private void setPullDownListener() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                tvRefresh.setVisibility(View.INVISIBLE);
                pageId = 1;
                downloadNewGoodsList(ACTION_PULL_DOWN,pageId);
            }
        });
    }

    private void setPullUpListener() {
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastPosition ;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && mAdapter.isMore()
                        && lastPosition == mAdapter.getItemCount() - 1) {
                    pageId ++;
                    downloadNewGoodsList(ACTION_PULL_UP,pageId);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastPosition= mManager.findLastVisibleItemPosition();
            }
        });
    }

    /**
     *  分类二级页面中 商品排序方法
     * @param sortBy
     */
    public void sortGoods(int sortBy) {
        mAdapter.sortGoods(sortBy);
    }

}
