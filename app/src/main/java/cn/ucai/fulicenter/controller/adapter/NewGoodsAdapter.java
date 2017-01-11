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
    Context mContext;
    ArrayList<NewGoodsBean> mNewGoodsList;

    //  此处传的List是在NewGoodsFragment中实例化的数据
    public NewGoodsAdapter(Context mContext, ArrayList<NewGoodsBean> list) {
        this.mContext = mContext;
        mNewGoodsList = new ArrayList<>();
        mNewGoodsList.addAll(list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder =
                new NewGoodsViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_newgoods, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        NewGoodsViewHolder newGoodsViewHolder;
        newGoodsViewHolder = (NewGoodsViewHolder) holder;
        newGoodsViewHolder.tvGoodsName.setText(mNewGoodsList.get(position).getGoodsName());
        newGoodsViewHolder.tvGoodsPrice.setText(mNewGoodsList.get(position).getCurrencyPrice());
        ImageLoader.downloadImg(mContext,newGoodsViewHolder.ivImageView,mNewGoodsList.get(position).getGoodsThumb());
    }

    @Override
    public int getItemCount() {
        return mNewGoodsList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public void initData(ArrayList<NewGoodsBean> list) {
        if (mNewGoodsList != null) {
            this.mNewGoodsList.clear();
        }
        this.mNewGoodsList.addAll(list);
        notifyDataSetChanged();
    }

    public void addAll(ArrayList<NewGoodsBean> list) {
        this.mNewGoodsList.addAll(list);
        notifyDataSetChanged();
    }


    static class NewGoodsViewHolder extends RecyclerView.ViewHolder{
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
}


