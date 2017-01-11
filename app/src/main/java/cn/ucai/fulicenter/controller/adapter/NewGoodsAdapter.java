package cn.ucai.fulicenter.controller.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.model.bean.NewGoodsBean;
import cn.ucai.fulicenter.model.utils.ImageLoader;

/**
 * Created by Administrator on 2017/1/11 0011.
 */

public class NewGoodsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_FOOTER = 0;
    public static final int TYPE_NEWGOODS = 1;

    Context mContext;
    ArrayList<NewGoodsBean> mNewGoodsList;

    String footer; // 判断页脚，是否还有数据可加载

    boolean isMore; // 判断是否还有更多数据可加载

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    //  此处传的List是在NewGoodsFragment中实例化的数据
    public NewGoodsAdapter(Context mContext, ArrayList<NewGoodsBean> list) {
        this.mContext = mContext;
        mNewGoodsList = new ArrayList<>();
        mNewGoodsList.addAll(list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        RecyclerView.ViewHolder holder =
//                new NewGoodsViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_newgoods, parent, false));
//        return holder;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View layout;
        if (viewType == TYPE_FOOTER) {
            //layout = inflater.inflate(R.layout.layout_footer_title, parent, false);
            layout = View.inflate(mContext, R.layout.layout_footer_title, null);
            return new FooterViewHolder(layout);
        } else if (viewType == TYPE_NEWGOODS) {
            layout = inflater.inflate(R.layout.item_newgoods, parent, false);
            return new NewGoodsViewHolder(layout);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        NewGoodsViewHolder newGoodsViewHolder;
//        newGoodsViewHolder = (NewGoodsViewHolder) holder;
//        newGoodsViewHolder.tvGoodsName.setText(mNewGoodsList.get(position).getGoodsName());
//        newGoodsViewHolder.tvGoodsPrice.setText(mNewGoodsList.get(position).getCurrencyPrice());
//        ImageLoader.downloadImg(mContext, newGoodsViewHolder.ivImageView, mNewGoodsList.get(position).getGoodsThumb());
        if (getItemViewType(position) == TYPE_FOOTER) {
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
            footerViewHolder.tvFooter.setText(getFooter());
            return;  // 必须返回reture，否则会报下标越界异常
        }

        NewGoodsBean newGoods = mNewGoodsList.get(position);
        NewGoodsViewHolder newGoodsViewHolder = (NewGoodsViewHolder) holder;
        newGoodsViewHolder.tvGoodsName.setText(newGoods.getGoodsName());
        newGoodsViewHolder.tvGoodsPrice.setText(newGoods.getCurrencyPrice());
        ImageLoader.downloadImg(mContext,newGoodsViewHolder.ivImageView,mNewGoodsList.get(position).getGoodsThumb());
    }

    @Override
    public int getItemCount() {
        return mNewGoodsList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return TYPE_FOOTER;
        }
        return TYPE_NEWGOODS;
    }

    public void initData(ArrayList<NewGoodsBean> list) {
        if (mNewGoodsList != null) {
            this.mNewGoodsList.clear();
        }
        addData(list);
    }

    public void addData(ArrayList<NewGoodsBean> list) {
        this.mNewGoodsList.addAll(list);
        notifyDataSetChanged();
    }

    /**
     *  新品的ViewHolder
     */
    static class NewGoodsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivImageView)
        ImageView ivImageView;
        @BindView(R.id.tvGoodsName)
        TextView tvGoodsName;
        @BindView(R.id.tvGoodsPrice)
        TextView tvGoodsPrice;

        NewGoodsViewHolder(View view) {
            super(view);  // 必须调用父类的super方法
            ButterKnife.bind(this, view);
        }
    }

    /**
     *  页脚的ViewHolder
     */
    static class FooterViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tvFooter)
        TextView tvFooter;
        FooterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}


