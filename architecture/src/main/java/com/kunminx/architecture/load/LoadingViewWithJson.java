package com.kunminx.architecture.load;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import androidx.lifecycle.LifecycleOwner;

import com.airbnb.lottie.LottieAnimationView;
import com.kunminx.architecture.R;
import com.kunminx.architecture.utils.DisplayUtils;


/**
 * 全局加载 loading，基于JSON实现
 *
 * @data 5/7/21 @time 3:36 PM
 */
public class LoadingViewWithJson extends LifecycleManager implements ILoading {
    private final Context context;
    private Animation alphaOut;
    private View loading;
    private boolean alphaOuting = false;
    private LottieAnimationView iv_loading;

    /**
     * 默认会加到 DecoView 最上层
     *
     * @param context
     */
    public LoadingViewWithJson(Context context) {
        this(context, null, null);
    }

    /**
     * @param context
     * @param parent  展示loading的根布局，若无特殊要求，可以不传，会默认以DecoView为根布局
     * @param params  展示loading的布局参数，(若传了 parent，则必须要传此参数)
     */
    public LoadingViewWithJson(Context context, ViewGroup parent, ViewGroup.LayoutParams params) {
        super(context);
        this.context = context;
        initAnim(parent, params);
    }

    /**
     * 显示loading
     */
    @Override
    public void showLoading() {
        if (alphaOut != null && alphaOuting) {
            alphaOut.cancel();
        }
        if (iv_loading != null && !iv_loading.isAnimating()) {
            iv_loading.playAnimation();
        }
        loading.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏loading
     */
    @Override
    public void hideLoading() {
        //有可能执行完onDestroy之后，又调用hideLoading
        if (iv_loading == null) return;
        if (iv_loading.isAnimating()) {
            iv_loading.cancelAnimation();
        }
        int s = loading.getVisibility();
        if (alphaOuting || s == View.GONE) return;
        alphaOuting = true;
        alphaOut = AnimationUtils.loadAnimation(context, R.anim.alpha_out);
        alphaOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                loading.setVisibility(View.GONE);
                alphaOuting = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }


        });
        loading.startAnimation(alphaOut);
    }

    /**
     * 销毁时释放资源，停止动画
     *
     * @param owner
     */
    @Override
    public void onDestroy(LifecycleOwner owner) {
        Log.d(TAG, owner.getClass().getSimpleName() + "=====LoadingView====>onDestroy()");
        if (iv_loading != null) {
            iv_loading.clearAnimation();
        }

        if (iv_loading != null) {
            if (iv_loading.isAnimating()) {
                iv_loading.cancelAnimation();
            }
        }

        if (alphaOut != null) {
            alphaOut.cancel();
        }
    }


    @SuppressLint("ResourceType")
    private void initAnim(ViewGroup parent, ViewGroup.LayoutParams params) {
        loading = LayoutInflater.from(context).inflate(R.layout.layout_loading_json_view, null);
        //若parent为null，则直接获取DecorView为根布局
        if (parent == null) {
            parent = (FrameLayout) ((Activity) context).getWindow().getDecorView();
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(DisplayUtils.dp2px(150), DisplayUtils.dp2px(150));
            layoutParams.gravity = Gravity.CENTER;
            parent.addView(loading, layoutParams);
        } else {
            parent.addView(loading, params);
        }

        iv_loading = loading.findViewById(R.id.animation_view);
    }
}
