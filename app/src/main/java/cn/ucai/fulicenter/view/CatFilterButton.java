package cn.ucai.fulicenter.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.controller.activity.CategoryChildActivity;
import cn.ucai.fulicenter.model.bean.CategoryChildBean;
import cn.ucai.fulicenter.model.utils.ImageLoader;
import cn.ucai.fulicenter.model.utils.MFGT;
/**
 * Created by Administrator on 2017/1/16 0016.
 */
public class CatFilterButton extends Button {
    boolean isExpaned;
    PopupWindow mPopupWindow;
    Context mContext;
    CatFilterAdapter adapter;
    GridView mGridView;
    String groupName;

    public CatFilterButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public void initCatFileterButton(String groupName, ArrayList<CategoryChildBean> list) {
        this.groupName = groupName;
        this.setText(groupName);
        adapter = new CatFilterAdapter(mContext, list);
        initGridView();
        setCatFilterButtonLister();
    }

    private void initGridView() {
        mGridView = new GridView(mContext);
        mGridView.setVerticalSpacing(10);
        mGridView.setHorizontalSpacing(10);
        mGridView.setNumColumns(GridView.AUTO_FIT); //  根据布局的宽度自动填充
        mGridView.setAdapter(adapter);
    }

    private void setCatFilterButtonLister() {
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isExpaned) {
                    initPopup();
                } else {
                    if (mPopupWindow.isShowing()) {
                        mPopupWindow.dismiss();
                    }
                }
                setArrow();
            }
        });
    }

    private void initPopup() {
        mPopupWindow = new PopupWindow();
        mPopupWindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        mPopupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0xbb000000));
        mPopupWindow.setContentView(mGridView);
        mPopupWindow.showAsDropDown(this);
    }
    private void setArrow() {
        Drawable right;
        if (isExpaned) {
            right = getResources().getDrawable(R.mipmap.arrow2_up);
        } else {
            right = getResources().getDrawable(R.mipmap.arrow2_down);
        }
        right.setBounds(0, 0, right.getIntrinsicWidth(), right.getIntrinsicHeight());
        this.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, right, null);
        isExpaned = !isExpaned;
    }

    class CatFilterAdapter extends BaseAdapter {
        Context context;
        ArrayList<CategoryChildBean> list;

        public CatFilterAdapter(Context context, ArrayList<CategoryChildBean> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override                 //  此处的parent是值AdapterView,没有addView方法
        public View getView(int position, View convertView, ViewGroup parent) {
            CatFilterViewHolder vh = null;
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.item_cat_filter, null);
                vh = new CatFilterViewHolder(convertView);
                convertView.setTag(vh);
            } else {
                vh = (CatFilterViewHolder) convertView.getTag();
            }
            vh.bind(position);
            return convertView;
        }

        class CatFilterViewHolder {
            @BindView(R.id.ivCategoryChildThumb)
            ImageView ivCategoryChildThumb;
            @BindView(R.id.tvCategoryChildName)
            TextView tvCategoryChildName;
            @BindView(R.id.layoutCategoryChild)
            RelativeLayout layoutCategoryChild;

            CatFilterViewHolder(View view) {
                ButterKnife.bind(this, view);
            }

            public void bind(final int position) {
                ImageLoader.downloadImg(context, ivCategoryChildThumb, list.get(position).getImageUrl());
                tvCategoryChildName.setText(list.get(position).getName());
                layoutCategoryChild.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MFGT.gotoCategoryChild(mContext,
                                list.get(position).getId(),
                                groupName,
                                list);
                        MFGT.finish((CategoryChildActivity)mContext);
                    }
                });
            }
        }
    }

}
