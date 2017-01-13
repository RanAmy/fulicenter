package cn.ucai.fulicenter.controller.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.model.bean.CategoryChildBean;
import cn.ucai.fulicenter.model.bean.CategoryGroupBean;
import cn.ucai.fulicenter.model.utils.ImageLoader;

/**
 * Created by Administrator on 2017/1/13 0013.
 */

public class CategoryAdapter extends BaseExpandableListAdapter {
    Context mContext;
    ArrayList<CategoryGroupBean> mGroupBeen;
    ArrayList<ArrayList<CategoryChildBean>> mChildBean;

    public CategoryAdapter(Context mContext, ArrayList<CategoryGroupBean> groupBeen,
                           ArrayList<ArrayList<CategoryChildBean>> childBean) {
        this.mContext = mContext;
        //  实例化，并添加数据
        this.mGroupBeen = new ArrayList<>();
        mGroupBeen.addAll(groupBeen);
        this.mChildBean = new ArrayList<>();
        mChildBean.addAll(childBean);
    }

    @Override
    public int getGroupCount() {
        return mGroupBeen != null ? mGroupBeen.size() : 0;
    }

    @Override
    public int getChildrenCount(int i) {
        return mChildBean != null && mChildBean.get(i) != null ? mChildBean.get(i).size() : 0;
    }

    @Override
    public CategoryGroupBean getGroup(int groupPosition) {
        return mGroupBeen.get(groupPosition);
    }

    @Override
    public CategoryChildBean getChild(int groupPosition, int childPosition) {
        // 代码有错误
//        if (mChildBean != null && mChildBean.get(childPosition) != null) {
//            return mChildBean.get(groupPosition).get(childPosition);
//        }
//        return null;
        return mChildBean.get(groupPosition).get(childPosition);
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
        Log.e("main", "================getGroupView.groupPosition"+groupPosition+"<<<<<<<<<<<");
        GroupViewHolder vh = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_category_group, null);
            vh = new GroupViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (GroupViewHolder) convertView.getTag();
        }
        ImageLoader.downloadImg(mContext,vh.ivGroupIcon, mGroupBeen.get(groupPosition).getImageUrl());
        vh.tvGroupName.setText(mGroupBeen.get(groupPosition).getName());
        vh.ivGroupIndicator.setImageResource(isExpanded ? R.mipmap.expand_off : R.mipmap.expand_on);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Log.e("main", ">>>>>getChildView.groupPosition" + groupPosition + ">>>>>>");
        ChildViewHolder vh = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_category_child, null);
            vh = new ChildViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ChildViewHolder) convertView.getTag();
        }
        ImageLoader.downloadImg(mContext, vh.ivChildThubm,getChild(groupPosition,childPosition).getImageUrl());
        vh.tvChildName.setText(getChild(groupPosition,childPosition).getName());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public void initData(ArrayList<CategoryGroupBean> groupBean, ArrayList<ArrayList<CategoryChildBean>> childBean) {

        Log.e(">>>>>>??????", "initData>>>>>>>>>>>>>>>>>>>>>>>"+groupBean.size());
        mGroupBeen.clear();
        mGroupBeen.addAll(groupBean);
        mChildBean.clear();
        mChildBean.addAll(childBean);
        notifyDataSetChanged();
    }


    class GroupViewHolder {
        @BindView(R.id.iv_group_icon)
        ImageView ivGroupIcon;
        @BindView(R.id.tv_group_name)
        TextView tvGroupName;
        @BindView(R.id.iv_group_indicator)
        ImageView ivGroupIndicator;

        GroupViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    class ChildViewHolder {
        @BindView(R.id.iv_child_thubm)
        ImageView ivChildThubm;
        @BindView(R.id.tv_child_name)
        TextView tvChildName;

        ChildViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
