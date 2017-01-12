package cn.ucai.fulicenter.model.net;

import android.content.Context;

import cn.ucai.fulicenter.model.bean.GoodsDetailsBean;
import cn.ucai.fulicenter.model.utils.OkHttpUtils;

/**
 * Created by Administrator on 2017/1/12 0012.
 */

public interface IModelGoodsDetails {
    void downData(Context context, int goodsId, OkHttpUtils.OnCompleteListener<GoodsDetailsBean> listener);
}
