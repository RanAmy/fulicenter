package cn.ucai.fulicenter.model.bean;

/**
 * Created by Administrator on 2017/1/9 0009.
 */

public class CartBean {

    /**
     * id : 2016
     * userName : a123456
     * goodsId : 7672
     * goods :
     */

    private int id;
    private String userName;
    private int goodsId;
    private String goods;

    public CartBean() {
    }

    public CartBean(int id, String userName, int goodsId, String goods) {
        this.id = id;
        this.userName = userName;
        this.goodsId = goodsId;
        this.goods = goods;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    @Override
    public String toString() {
        return "CartBean{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", goodsId=" + goodsId +
                ", goods='" + goods + '\'' +
                '}';
    }
}
