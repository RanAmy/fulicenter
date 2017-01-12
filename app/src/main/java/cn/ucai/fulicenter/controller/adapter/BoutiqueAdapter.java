package cn.ucai.fulicenter.controller.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.controller.activity.BoutiqueChildActivity;
import cn.ucai.fulicenter.model.bean.BoutiqueBean;
import cn.ucai.fulicenter.model.utils.ImageLoader;

/**
 * Created by Administrator on 2017/1/11 0011.
 */

public class BoutiqueAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context mContext;
    ArrayList<BoutiqueBean> mBoutiqueList;


    //  此处传的List是在NewGoodsFragment中实例化的数据
    public BoutiqueAdapter(Context mContext, ArrayList<BoutiqueBean> mList) {
        this.mContext = mContext;
        mBoutiqueList = new ArrayList<>();
        mBoutiqueList.addAll(mList);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View layout
             = inflater.inflate(R.layout.item_boutique, parent, false);
            return new BoutiqueViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        BoutiqueBean boutique = mBoutiqueList.get(position);
        BoutiqueViewHolder BoutiqueViewHolder = (BoutiqueViewHolder) holder;
        BoutiqueViewHolder.etName.setText(boutique.getName());
        BoutiqueViewHolder.etTitle.setText(boutique.getTitle());
        BoutiqueViewHolder.etDscription.setText(boutique.getDescription());
        ImageLoader.downloadImg(mContext, BoutiqueViewHolder.ivBoutique, mBoutiqueList.get(position).getImageurl());
        //  点击精选首页的itemView
        BoutiqueViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, BoutiqueChildActivity.class)
                        .putExtra(I.NewAndBoutiqueGoods.CAT_ID, mBoutiqueList.get(position).getId()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBoutiqueList.size();
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


