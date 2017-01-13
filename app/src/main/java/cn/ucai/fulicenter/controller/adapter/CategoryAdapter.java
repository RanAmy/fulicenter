package cn.ucai.fulicenter.controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import java.util.ArrayList;
import java.util.zip.Inflater;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.model.bean.CategoryChildBean;
import cn.ucai.fulicenter.model.bean.CategoryGroupBean;

/**
 * Created by Administrator on 2017/1/13 0013.
 */

public class CategoryAdapter extends BaseExpandableListAdapter {
    Context mContext;
    ArrayList<CategoryGroupBean> groupBeen;
    ArrayList<ArrayList<CategoryChildBean>> childBean;

    public CategoryAdapter(Context mContext, ArrayList<CategoryGroupBean> groupBeen,
                           ArrayList<ArrayList<CategoryChildBean>> childBean) {
        this.mContext = mContext;
        this.groupBeen = new ArrayList<>();
        groupBeen.addAll(groupBeen);
        this.childBean = new ArrayList<>();
        childBean.addAll(childBean);
    }

    @Override
    public int getGroupCount() {
        return groupBeen!=null?groupBeen.size():0;
    }

    @Override
    public int getChildrenCount(int i) {
        return childBean!=null&&childBean.get(i)!=null?childBean.get(i).size():0;
    }

    @Override
    public CategoryGroupBean getGroup(int groupPosition) {
        return groupBeen.get(groupPosition);
    }

    @Override
    public CategoryChildBean getChild(int groupPosition, int childPosition) {
        if (childBean != null && childBean.get(childPosition) != null) {
            return childBean.get(groupPosition).get(childPosition);
        }
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View gruopLayout = LayoutInflater.from(mContext).inflate(R.layout.item_category_group, null);
        return gruopLayout;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View childLayout = LayoutInflater.from(mContext).inflate(R.layout.item_category_child, null);
        return childLayout;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
