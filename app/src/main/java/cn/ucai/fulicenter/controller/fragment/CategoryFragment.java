package cn.ucai.fulicenter.controller.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.controller.adapter.CategoryAdapter;
import cn.ucai.fulicenter.model.bean.CategoryChildBean;
import cn.ucai.fulicenter.model.bean.CategoryGroupBean;
import cn.ucai.fulicenter.model.net.IModelCategory;
import cn.ucai.fulicenter.model.net.ModelCategory;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.utils.ConvertUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment {
    public static final String TAG = CategoryFragment.class.getSimpleName();

    IModelCategory model;
    CategoryAdapter mAdapter;
    int groupCount;

    ArrayList<CategoryGroupBean> mGroupBean = new ArrayList<>();
    ArrayList<ArrayList<CategoryChildBean>> mChildBean = new ArrayList<>();

    @BindView(R.id.elv_category)
    ExpandableListView elvCategory;
    @BindView(R.id.tv_more)
    TextView tvMore;

    public CategoryFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_category, container, false);
        ButterKnife.bind(this, layout);
        mAdapter = new CategoryAdapter(getContext(), mGroupBean, mChildBean);
        elvCategory.setAdapter(mAdapter);
        //  取消分类页面自带的扩展按钮
        elvCategory.setGroupIndicator(null);
        initView(false);
        initData();
        return layout;
    }

    private void initData() {
        model = new ModelCategory();
        model.downData(getContext(), new OnCompleteListener<CategoryGroupBean[]>() {
            @Override
            public void onSuccess(CategoryGroupBean[] result) {
                if (result != null) {
                    initView(true);
                    ArrayList<CategoryGroupBean> list = ConvertUtils.array2List(result);
                    mGroupBean.addAll(list);
                    for (int i=0;i<list.size();i++) {
                        //  注意你要使用的方法：是add方法，还是addAll方法
                        mChildBean.add(new ArrayList<CategoryChildBean>());
                        //  downloadChildData方法中添加一个参数 i ，
                        //  目的是将group和child的数据两者相对应
                        downloadChildData(list.get(i).getId(),i);
                    }
                } else {
                    initView(false);
                }
            }

            @Override
            public void onError(String error) {
                Log.i(TAG, "error=" + error);
            }
        });
    }

    private void downloadChildData(int id, final int index) {
        model.downData(getContext(), id, new OnCompleteListener<CategoryChildBean[]>() {
            @Override
            public void onSuccess(CategoryChildBean[] result) {
                groupCount++;
                if (result != null) {
                    ArrayList<CategoryChildBean> list = ConvertUtils.array2List(result);
//                    mChildBean.add(list);
                    mChildBean.set(index, list);
                }
                if (groupCount == mGroupBean.size()) {
                    mAdapter.initData(mGroupBean, mChildBean);
                }
                Log.e(TAG, "groupCount=" + groupCount);
            }

            @Override
            public void onError(String error) {
                groupCount++;
                Log.i(TAG, "error=" + error);
            }
        });
    }

    private void initView(boolean hasData) {
        elvCategory.setVisibility(hasData ? View.VISIBLE : View.GONE);
        tvMore.setVisibility(hasData?View.GONE:View.VISIBLE);
    }

}
