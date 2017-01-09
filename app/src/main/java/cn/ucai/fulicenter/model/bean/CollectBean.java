package cn.ucai.fulicenter.model.bean;

/**
 * Created by Administrator on 2017/1/9 0009.
 */

public class CollectBean {

    /**
     * success : true
     * msg : 1
     */

    private boolean success;
    private String msg;

    public CollectBean() {
    }

    public CollectBean(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "CollectBean{" +
                "success=" + success +
                ", msg='" + msg + '\'' +
                '}';
    }
}
