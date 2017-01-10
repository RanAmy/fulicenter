package cn.ucai.fulicenter.view.utils;

import android.app.Activity;
import android.content.Intent;

import cn.ucai.fulicenter.R;

/**
 * Created by Administrator on 2017/1/10 0010.
 */

public class MFGT {
    //  点Back键，直接关闭Activity
    public static void finish(Activity activity) {
        activity.finish();
        activity.overridePendingTransition(R.anim.push_left_in,R.anim.push_right_out);
    }

    public static void startActivity(Activity context, Class<?> cla) {
        context.startActivity(new Intent(context, cla));
        context.overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
    }
}
