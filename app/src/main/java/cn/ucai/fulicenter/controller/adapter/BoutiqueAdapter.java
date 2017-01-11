package cn.ucai.fulicenter.controller.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.model.bean.BoutiqueBean;
import cn.ucai.fulicenter.model.utils.ImageLoader;

/**
 * Created by Administrator on 2017/1/11 0011.
 */

public class BoutiqueAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_FOOTER = 0;
    public static final int TYPE_BOUTIQUE = 1;

    Context mContext;
    ArrayList<BoutiqueBean> mBoutiqueList;

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
    public BoutiqueAdapter(Context mContext, ArrayList<BoutiqueBean> mList) {
        this.mContext = mContext;
        mBoutiqueList = new ArrayList<>();
        mBoutiqueList.addAll(mList);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View layout;
        if (viewType == TYPE_FOOTER) {
            layout = View.inflate(mContext, R.layout.layout_footer_title, null);
            return new FooterViewHolder(layout);
        } else if (viewType == TYPE_BOUTIQUE) {
            layout = inflater.inflate(R.layout.item_boutique, parent, false);
            return new BoutiqueViewHolder(layout);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_FOOTER) {
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
            footerViewHolder.tvFooter.setText(getFooter());
            return;  // 必须返回reture，否则会报下标越界异常
        }

        BoutiqueBean boutique = mBoutiqueList.get(position);
        BoutiqueViewHolder BoutiqueViewHolder = (BoutiqueViewHolder) holder;
        BoutiqueViewHolder.etName.setText(boutique.getName());
        BoutiqueViewHolder.etTitle.setText(boutique.getTitle());
        BoutiqueViewHolder.etDscription.setText(boutique.getDescription());
        ImageLoader.downloadImg(mContext, BoutiqueViewHolder.ivBoutique, mBoutiqueList.get(position).getImageurl());
    }

    @Override
    public int getItemCount() {
        return mBoutiqueList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return TYPE_FOOTER;
        }
        return TYPE_BOUTIQUE;
    }

    public void initData(ArrayList<BoutiqueBean> mList) {
        if (mBoutiqueList != null) {
            this.mBoutiqueList.clear();
        }
        addData(mList);
    }

    public void addData(ArrayList<BoutiqueBean> mList) {
        this.mBoutiqueList.addAll(mList);
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

    /**
     * 精选的ViewHolder
     */
    static class BoutiqueViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.ivBoutique)
        ImageView ivBoutique;
        @BindView(R.id.etName)
        EditText etName;
        @BindView(R.id.etTitle)
        EditText etTitle;
        @BindView(R.id.etDscription)
        EditText etDscription;

        BoutiqueViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}


