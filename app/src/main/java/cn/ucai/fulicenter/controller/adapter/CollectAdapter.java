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
import cn.ucai.fulicenter.model.bean.CollectBean;
import cn.ucai.fulicenter.model.utils.ImageLoader;
import cn.ucai.fulicenter.model.utils.MFGT;

/**
 * Created by Administrator on 2017/1/11 0011.
 */

public class CollectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_FOOTER = 0;
    public static final int TYPE_NEWGOODS = 1;

    Context mContext;
    ArrayList<CollectBean> mNewGoodsList;

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
    public CollectAdapter(Context mContext, ArrayList<CollectBean> list) {
        this.mContext = mContext;
        mNewGoodsList = new ArrayList<>();
        mNewGoodsList.addAll(list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View layout;
        if (viewType == TYPE_FOOTER) {
            layout = View.inflate(mContext, R.layout.layout_footer_title, null);
            return new FooterViewHolder(layout);
        } else if (viewType == TYPE_NEWGOODS) {
            layout = inflater.inflate(R.layout.item_collect, parent, false);
            return new CollectViewHolder(layout);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == TYPE_FOOTER) {
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
            footerViewHolder.tvFooter.setText(getFooter());
            return;  // 必须返回reture，否则会报下标越界异常
        }

        CollectBean newGoods = mNewGoodsList.get(position);
        CollectViewHolder CollectViewHolder = (CollectViewHolder) holder;
        CollectViewHolder.ivGoodsName.setText(newGoods.getGoodsName());
        ImageLoader.downloadImg(mContext, CollectViewHolder.ivGoodsThumb, mNewGoodsList.get(position).getGoodsThumb());
        CollectViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MFGT.gotoGoodsDetail(mContext, mNewGoodsList.get(position).getGoodsId());
            }
        });
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

    public void initData(ArrayList<CollectBean> list) {
        if (mNewGoodsList != null) {
            this.mNewGoodsList.clear();
        }
        addData(list);
    }

    public void addData(ArrayList<CollectBean> list) {
        this.mNewGoodsList.addAll(list);
        notifyDataSetChanged();
    }
    

    /**
     * 页脚的ViewHolder
     */
    static class FooterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvFooter)
        TextView tvFooter;

        FooterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class CollectViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.ivGoodsThumb)
        ImageView ivGoodsThumb;
        @BindView(R.id.ivGoodsName)
        TextView ivGoodsName;
        @BindView(R.id.iv_collect_delete)
        ImageView ivCollectDelete;

        CollectViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

