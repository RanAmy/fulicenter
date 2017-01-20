package cn.ucai.fulicenter.controller.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.model.bean.CartBean;
import cn.ucai.fulicenter.model.bean.GoodsDetailsBean;
import cn.ucai.fulicenter.model.utils.ImageLoader;

/**
 * Created by Administrator on 2017/1/20 0020.
 */

public class CartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context mContext;
    ArrayList<CartBean> mList;

    public CartAdapter(Context mContext, ArrayList<CartBean> mList) {
        this.mContext = mContext;
        this.mList = new ArrayList<>();
        this.mList.addAll(mList);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        CartViewHolder vh = new CartViewHolder(View.inflate(mContext, R.layout.item_cart, null));
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CartViewHolder vh = (CartViewHolder) holder;
        vh.bind(position);
//        ImageLoader.downloadImg(mContext,);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void initData(ArrayList<CartBean> list) {
        if (mList != null) {
            mList.clear();
        }
        addAll(list);
    }

    public void addAll(ArrayList<CartBean> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    class CartViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_cart_thumb)
        ImageView ivCartThumb;
        @BindView(R.id.tv_cart_good_name)
        TextView tvCartGoodName;
        @BindView(R.id.tv_cart_count)
        TextView tvCartCount;
        @BindView(R.id.tv_cart_price)
        TextView tvCartPrice;
        @BindView(R.id.cb_cart_selected)
        CheckBox cbSelected;

        CartViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bind(int position) {
            GoodsDetailsBean detailsBean = mList.get(position).getGoods();
            if (detailsBean != null) {
                ImageLoader.downloadImg(mContext, ivCartThumb, detailsBean.getGoodsThumb());
                tvCartGoodName.setText(detailsBean.getGoodsName());
                tvCartPrice.setText(detailsBean.getCurrencyPrice());
            }
            tvCartCount.setText("("+mList.get(position).getCount()+")");
            cbSelected.setChecked(false);
        }
    }
}
