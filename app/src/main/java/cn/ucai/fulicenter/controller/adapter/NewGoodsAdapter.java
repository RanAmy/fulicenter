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
    ArrayList<NewGoodsBean> newGoodsList;

    public NewGoodsAdapter(Context mContext, ArrayList<NewGoodsBean> newGoodsList) {
        this.mContext = mContext;
        newGoodsList = new ArrayList<>();
        newGoodsList.addAll(newGoodsList);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder =
                new NewGoodsViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_newgoods, parent, false));
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        NewGoodsViewHolder newGoodsViewHolder;
        newGoodsViewHolder = (NewGoodsViewHolder) holder;
        newGoodsViewHolder.tvGoodsName.setText(newGoodsList.get(position).getGoodsName());
        newGoodsViewHolder.tvGoodsPrice.setText(newGoodsList.get(position).getCurrencyPrice());
        ImageLoader.downloadImg(mContext,newGoodsViewHolder.ivImageView,newGoodsList.get(position).getGoodsThumb());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
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


