package cn.ucai.fulicenter.controller.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.model.bean.AlbumsBean;
import cn.ucai.fulicenter.model.bean.GoodsDetailsBean;
import cn.ucai.fulicenter.model.net.IModelGoodsDetails;
import cn.ucai.fulicenter.model.net.ModelGoodsDetails;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.utils.MFGT;

public class GoodsDetailsActivity extends AppCompatActivity {
    private static final String TAG = GoodsDetailsActivity.class.getSimpleName();
    int goodsId = 0;
    IModelGoodsDetails model;

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.tvgoodnameenglish)
    TextView tvgoodnameenglish;
    @BindView(R.id.tvgoodname)
    TextView tvgoodname;
    @BindView(R.id.tvgoodpriceshop)
    TextView tvgoodpriceshop;
    @BindView(R.id.tvgoodpricecurrent)
    TextView tvgoodpricecurrent;
    @BindView(R.id.salv)
    cn.ucai.fulicenter.controller.activity.widget.SlideAutoLoopView salv;
    @BindView(R.id.indicator)
    cn.ucai.fulicenter.controller.activity.widget.FlowIndicator indicator;
    @BindView(R.id.wvgoodbrief)
    WebView wvgoodbrief;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_details);
        ButterKnife.bind(this);
        goodsId = getIntent().getIntExtra(I.GoodsDetails.KEY_GOODS_ID, 0);
        if (goodsId == 0) {
            MFGT.finish(this);
        } else {
            initData();
        }
    }

    private void initData() {
        model = new ModelGoodsDetails();
        model.downData(this, goodsId, new OnCompleteListener<GoodsDetailsBean>() {
            @Override
            public void onSuccess(GoodsDetailsBean result) {
                if (result != null) {
                    showGoodsDetail(result);
                } else {
                    MFGT.finish(GoodsDetailsActivity.this);
                }
            }

            @Override
            public void onError(String error) {
                Log.i(TAG, "error==" + error);
            }
        });
    }

    private void showGoodsDetail(GoodsDetailsBean goods) {
        tvgoodname.setText(goods.getGoodsName());
        tvgoodnameenglish.setText(goods.getGoodsEnglishName());
        tvgoodpricecurrent.setText(goods.getCurrencyPrice());
        tvgoodpriceshop.setText(goods.getShopPrice());

        //  显示轮播和指划
        salv.startPlayLoop(indicator,getAlbumUrl(goods),getAlbumCount(goods));
        //  显示商品详情
        wvgoodbrief.loadDataWithBaseURL(null,goods.getGoodsBrief(),I.TEXT_HTML,I.UTF_8,null);
    }

    private String[] getAlbumUrl(GoodsDetailsBean goods) {
        if (goods != null && goods.getPromotePrice() != null && goods.getPromotePrice().length() > 0) {
            AlbumsBean[] albums = goods.getProperties()[0].getAlbums();
            if (albums != null && albums.length > 0) {
                String[] urls = new String[albums.length];
                for(int i=0;i<albums.length;i++) {
                    urls[i] = albums[i].getImgUrl();
                }
                return urls;
            }
        }
        return new String[0];
    }

    private int getAlbumCount(GoodsDetailsBean goods) {
        if (goods != null && goods.getProperties() != null && goods.getProperties().length > 0) {
            return goods.getProperties()[0].getAlbums().length;
        }
        return 0;
    }

    /**
     *  按标题栏的back键，返回上一层页面
     */
    @OnClick(R.id.back)
    public void onClick() {
        MFGT.finish(this);
    }
}
