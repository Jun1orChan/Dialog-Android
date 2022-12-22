package com.nd.dialog.base;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.nd.dialog.R;

import java.util.UUID;


/**
 * @author cwj
 */
public abstract class BaseDialogFragment extends DialogFragment {


    private static final String KEY_SAVE_STATE_GRAVITY = "save_state_gravity";

    private static final String KEY_SAVE_STATE_DIM_AMOUNT = "save_state_dim_amount";

    private static final String KEY_SAVE_STATE_CANCELABLE_ON_TOUCH_OUTSIDE = "save_state_cancelable_on_touch_outside";

    private static final String KEY_SAVE_STATE_WIDTH_ASPECT = "save_state_width_aspect";

    private static final String KEY_SAVE_STATE_ANIM_RES = "save_state_anim_res";

    private static final String KEY_SAVE_STATE_TAG = "save_state_tag";

    private static final String KEY_SAVE_STATE_DIM_BACKGROUND = "save_state_dim_background";


    private static final float DEFAULT_DIM_AMOUNT = 0.6F;
    private int mGravity = Gravity.CENTER;
    private float mDimAmount = DEFAULT_DIM_AMOUNT;
    private boolean mIsCancelableOnTouchOutside = true;
    private float mWidthAspect = 0.75f;
    private int mAnimRes = -1;
    private String mTag;
    private DialogInterface.OnDismissListener mOnDismissListener;

    /**
     * 是否需要置灰，默认不置灰
     */
    private static boolean sNeedGray = false;

    /**
     * 设置背景蒙版，默认：有蒙版
     */
    private boolean mDimBackground = true;

    /**
     * 设置置灰
     *
     * @param needGray
     */
    public static void setNeedGray(boolean needGray) {
        sNeedGray = needGray;
    }

    /**
     * 获取Dialog显示的view
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    public abstract View getDialogView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);


//    @Override
//    public int getTheme() {
//        return R.style.dialoglib_dim_false;
//    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_SAVE_STATE_GRAVITY, mGravity);
        outState.putFloat(KEY_SAVE_STATE_DIM_AMOUNT, mDimAmount);
        outState.putBoolean(KEY_SAVE_STATE_CANCELABLE_ON_TOUCH_OUTSIDE, mIsCancelableOnTouchOutside);
        outState.putFloat(KEY_SAVE_STATE_WIDTH_ASPECT, mWidthAspect);
        outState.putInt(KEY_SAVE_STATE_ANIM_RES, mAnimRes);
        outState.putString(KEY_SAVE_STATE_TAG, mTag);
        outState.putBoolean(KEY_SAVE_STATE_DIM_BACKGROUND, mDimBackground);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            //还原
            mGravity = savedInstanceState.getInt(KEY_SAVE_STATE_GRAVITY);
            mDimAmount = savedInstanceState.getFloat(KEY_SAVE_STATE_DIM_AMOUNT);
            mIsCancelableOnTouchOutside = savedInstanceState.getBoolean(KEY_SAVE_STATE_CANCELABLE_ON_TOUCH_OUTSIDE);
            mWidthAspect = savedInstanceState.getFloat(KEY_SAVE_STATE_WIDTH_ASPECT);
            mAnimRes = savedInstanceState.getInt(KEY_SAVE_STATE_ANIM_RES);
            mTag = savedInstanceState.getString(KEY_SAVE_STATE_TAG);
            mDimBackground = savedInstanceState.getBoolean(KEY_SAVE_STATE_DIM_BACKGROUND);
        }
        ViewGroup containerView = (ViewGroup) inflater.inflate(R.layout.dialoglib_base, container, false);
        containerView.addView(getDialogView(inflater, container, savedInstanceState));
        return containerView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (sNeedGray) {
            setGray();
        }
    }

    /**
     * 取消置灰
     */
    public void cancelGray() {
        if (getDialog() == null || getDialog().getWindow() == null || getDialog().getWindow().getDecorView() == null) {
            return;
        }
        getDialog().getWindow().getDecorView().setLayerType(View.LAYER_TYPE_HARDWARE, null);
    }

    /**
     * 设置置灰
     */
    public void setGray() {
        if (getDialog() == null || getDialog().getWindow() == null || getDialog().getWindow().getDecorView() == null) {
            return;
        }
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        paint.setColorFilter(new ColorMatrixColorFilter(cm));
        getDialog().getWindow().getDecorView().setLayerType(View.LAYER_TYPE_HARDWARE, paint);
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //去除Dialog默认头部
        setStyle(STYLE_NO_TITLE, getTheme());
        return new BaseDialog(this.getActivity(), this.getTheme());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Dialog dialog = getDialog();
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(mIsCancelableOnTouchOutside);
    }

    @Override
    public void onStart() {
        Window window = getDialog().getWindow();
        if (window != null) {
            //设置窗体背景色透明
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //设置宽高
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.width = (int) (getScreenWidth(window.getContext()) * mWidthAspect);
            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            //透明度
            layoutParams.dimAmount = mDimAmount;
            //位置
            layoutParams.gravity = mGravity;
            window.setAttributes(layoutParams);
            window.setWindowAnimations(mAnimRes == -1 ? R.style.dialoglib_anim_default : mAnimRes);
        }
        super.onStart();
    }

    /**
     * 动画
     *
     * @param resId
     */
    public void setAnimRes(@StyleRes int resId) {
        this.mAnimRes = resId;
    }

    /**
     * 宽度占比
     *
     * @param widthAspect
     */
    public void setWidthAspect(float widthAspect) {
        this.mWidthAspect = widthAspect;
    }

    /**
     * 背景透明度
     *
     * @param dimAmount
     */
    public void setDimAmount(float dimAmount) {
        this.mDimAmount = dimAmount;
    }

    /**
     * 位置
     *
     * @param gravity
     */
    public void setGravity(int gravity) {
        this.mGravity = gravity;
    }

    /**
     * 点击外部是否取消
     *
     * @param isCancelableOnTouchOutside
     */
    public void setCanceledOnTouchOutside(boolean isCancelableOnTouchOutside) {
        this.mIsCancelableOnTouchOutside = isCancelableOnTouchOutside;
    }

    public String getFragmentTag() {
        if (TextUtils.isEmpty(mTag)) {
            mTag = UUID.randomUUID().toString().toLowerCase();
        }
        return mTag;
    }


    /**
     * 设置是否蒙版，默认：true
     *
     * @param dimBackground
     */
    public void setDimBackground(boolean dimBackground) {
        this.mDimBackground = dimBackground;
    }

    /**
     * 显示
     *
     * @param fragmentManager
     */
    public void show(final FragmentManager fragmentManager) {
        if (fragmentManager == null) {
            return;
        }
        FragmentManagerProxy proxy = new FragmentManagerProxy(fragmentManager);
        if (isShowing()) {
            return;
        }
        if (!isAdded()) {
            try {
                show(proxy, getFragmentTag());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        fragmentManager.executePendingTransactions();
    }

    /**
     * 当前是否显示
     *
     * @return
     */
    public boolean isShowing() {
        return getDialog() != null && getDialog().isShowing();
    }

    /**
     * dialog消失监听
     *
     * @param onDismissListener
     */
    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        mOnDismissListener = onDismissListener;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (mOnDismissListener != null) {
            mOnDismissListener.onDismiss(dialog);
        }
    }

    @Override
    public void dismiss() {
        try {
            dismissAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取设备屏幕高度
     *
     * @param context
     * @return
     */
    private int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }


    @Override
    public int getTheme() {
        if (mDimBackground) {
            return R.style.Theme_AppCompat_Light_Dialog;
        } else {
            return R.style.dialoglib_dim_false;
        }
    }


    @Override
    public void onDestroyView() {
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.setCancelMessage(null);
        }
        // https://codeday.me/bug/20180423/157579.html
        if ((dialog != null) && getRetainInstance()) {
            dialog.setDismissMessage(null);
        }
        super.onDestroyView();
    }
}
