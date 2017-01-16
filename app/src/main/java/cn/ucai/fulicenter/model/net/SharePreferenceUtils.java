package cn.ucai.fulicenter.model.net;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2017/1/16 0016.
 */

public class SharePreferenceUtils {
    private static final String SHARE_NAME = "saveUserInfo";
    private static final String SHARE_KEY_USER_NAME = "share_key_user_name";
    private static SharePreferenceUtils instance;
    private static SharedPreferences preferences;
    private static SharedPreferences mSharePreferences;
    private  static SharedPreferences.Editor mEditor;

    public SharePreferenceUtils(Context context) {
        preferences = context.getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE);
        mEditor = mSharePreferences.edit();
    }

    public static SharePreferenceUtils getInstance(Context context) {
        if (instance == null) {
            instance = new SharePreferenceUtils(context);
        }
        return instance;
    }

    public static void saveUser(String username) {
        mEditor.putString(SHARE_KEY_USER_NAME, username);
        mEditor.commit();
    }

    public static String getUser() {
        return mSharePreferences.getString(SHARE_KEY_USER_NAME,null);
    }

    public void removeUser() {
        mEditor.remove(SHARE_KEY_USER_NAME);
        mEditor.commit();
    }
}
