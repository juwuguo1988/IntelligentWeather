package com.kunminx.architecture.load;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.lifecycle.LifecycleOwner;

import com.kunminx.architecture.R;
import com.kunminx.architecture.utils.DisplayUtils;


/**
 * 全局加载 loading，基于帧动画实现
 *
 * @data 4/28/21 @time 4:19 PM
 */
public class LoadingView extends LifecycleManager implements ILoading {

    private final Context context;
    private AnimationDrawable animationDrawable;
    private Animation alphaOut;
    private View loading;
    private boolean alphaOuting = false;
    private ImageView iv_loading;

    /**
     * 默认会加到 DecoView 最上层
     *
     * @param context
     */
    public LoadingView(Context context) {
        this(context, null, null);
    }

    /**
     * @param context
     * @param parent  展示loading的根布局，若无特殊要求，可以不传，会默认以DecoView为根布局
     * @param params  展示loading的布局参数，(若传了 parent，则必须要传此参数)
     */
    public LoadingView(Context context, ViewGroup parent, ViewGroup.LayoutParams params) {
        super(context);
        this.context = context;
        initAnim(parent, params);
    }

    /**
     * 显示loading
     */
    @Override
    public void showLoading() {
        //如果loading正在执行退出动画，先取消掉退出动画
        if (alphaOut != null && alphaOuting) {
            alphaOut.cancel();
        }
        if (animationDrawable != null && !animationDrawable.isRunning()) {
            animationDrawable.start();
        }
        loading.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏loading
     */
    @Override
    public void hideLoading() {
        //有可能执行完onDestroy之后，又调用hideLoading
        if (animationDrawable == null) return;
        if (animationDrawable.isRunning()) {
            animationDrawable.stop();
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

        if (animationDrawable != null) {
            if (animationDrawable.isRunning()) {
                animationDrawable.stop();
            }
        }

        if (alphaOut != null) {
            alphaOut.cancel();
        }
    }


    @SuppressLint("ResourceType")
    private void initAnim(ViewGroup parent, ViewGroup.LayoutParams params) {
        loading = LayoutInflater.from(context).inflate(R.layout.layout_loading_view, null);
        //若parent为null，则直接获取DecorView为根布局
        if (parent == null) {
            parent = (FrameLayout) ((Activity) context).getWindow().getDecorView();
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(DisplayUtils.dp2px(150), DisplayUtils.dp2px(150));
            layoutParams.gravity = Gravity.CENTER;
            parent.addView(loading, layoutParams);
        } else {
            parent.addView(loading, params);
        }

        iv_loading = loading.findViewById(R.id.iv_loading);
        iv_loading.setBackgroundResource(R.drawable.center_load_animation);
        animationDrawable = (AnimationDrawable) iv_loading.getBackground();
    }
}
