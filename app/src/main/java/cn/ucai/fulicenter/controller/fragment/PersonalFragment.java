package cn.ucai.fulicenter.controller.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.model.bean.MessageBean;
import cn.ucai.fulicenter.model.bean.User;
import cn.ucai.fulicenter.model.net.IModelUser;
import cn.ucai.fulicenter.model.net.ModelUser;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.utils.ImageLoader;
import cn.ucai.fulicenter.model.utils.MFGT;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalFragment extends Fragment {
    private static final String TAG = PersonalFragment.class.getSimpleName();
    IModelUser model;

    @BindView(R.id.iv_user_avatar)
    ImageView ivUserAvatar;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_center_settings)
    TextView tvCenterSettings;
    @BindView(R.id.tv_collect_count)
    TextView tvCollectCount;

    public PersonalFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_personal, container, false);
        ButterKnife.bind(this, layout);
        initData();
        getCollectCount();
        return layout;
    }

    private void initData() {
        User user = FuLiCenterApplication.getUser();
        if (user != null) {
            loadUserInfo(user);
        } else {
            Log.e(TAG, "initData,null user");
//            MFGT.gotoLogin(getActivity());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void loadUserInfo(User user) {
//        ImageLoader.downloadImg(getContext(), ivUserAvatar, user.getAvaterPath());
        ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user), getContext(), ivUserAvatar);
        tvUserName.setText(user.getMuserNick());
        loadCollectCount("0");
    }

    /**
     * 收藏的数量
     */
    private void getCollectCount() {
        model = new ModelUser();
        model.collectCount(getContext(), FuLiCenterApplication.getUser().getMuserName(),
                new OnCompleteListener<MessageBean>() {
                    @Override
                    public void onSuccess(MessageBean result) {
                        Log.e(TAG, "result=" + result);
                        if (result != null && result.isSuccess()) {
                            loadCollectCount(result.getMsg());
                        } else {
                            loadCollectCount("0");
                        }
                    }

                    @Override
                    public void onError(String error) {
                        Log.e(TAG, "error=" + error);
                        loadCollectCount("0");
                    }
                });
    }

    private void loadCollectCount(String count) {
        tvCollectCount.setText(String.valueOf(count));
    }


    @OnClick({R.id.tv_center_settings, R.id.center_user_info})
    public void settings() {
        MFGT.gotoSettings(getActivity());
    }


}
