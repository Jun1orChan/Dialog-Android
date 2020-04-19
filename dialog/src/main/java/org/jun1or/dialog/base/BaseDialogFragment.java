package org.jun1or.dialog.base;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.istrong.dialog.R;

import java.util.UUID;


public abstract class BaseDialogFragment extends DialogFragment {

    private static final String SAVED_DIALOG_STATE_TAG = "android:savedDialogState";

    private static final float DEFAULT_DIMAMOUNT = 0.6F;

    private int mGravity = Gravity.CENTER;
    private float mDimAmount = DEFAULT_DIMAMOUNT;
    private boolean mIsCancelableOnTouchOutside = true;
    private float mWidthAspect = 0.75f;
    private int mAnimRes = -1;
    private String mTag;
    private DialogInterface.OnDismissListener mOnDismissListener;


    public abstract View getDialogView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setRetainInstance(true);
        ViewGroup containerView = (ViewGroup) inflater.inflate(R.layout.dialoglib_base, container, false);
        containerView.addView(getDialogView(inflater, container, savedInstanceState));
        return containerView;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new BaseDialog(this.getActivity(), this.getTheme());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //去除Dialog默认头部
        Dialog dialog = getDialog();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
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
//            Log.e("TAG", "layoutParams.width:" + layoutParams.width);
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

    public void setAnimRes(@StyleRes int resId) {
        this.mAnimRes = resId;
    }

    public void setWidthAspect(float widthAspect) {
        this.mWidthAspect = widthAspect;
    }

    public void setDimAmount(float dimAmount) {
        this.mDimAmount = dimAmount;
    }

    public void setGravity(int gravity) {
        this.mGravity = gravity;
    }

    public void setCanceledOnTouchOutside(boolean isCancelableOnTouchOutside) {
        this.mIsCancelableOnTouchOutside = isCancelableOnTouchOutside;
    }

    public String getFragmentTag() {
        if (TextUtils.isEmpty(mTag)) {
            mTag = UUID.randomUUID().toString().toLowerCase();
        }
        return mTag;
    }

    public void show(final FragmentManager fragmentManager) {
        if (fragmentManager == null)
            return;
        if (isShowing()) {
            return;
        }
        if (!isAdded()) {
            try {
                show(fragmentManager, getFragmentTag());
            } catch (Exception e) {

            }
        }
        fragmentManager.executePendingTransactions();
    }

    public boolean isShowing() {
        return getDialog() != null && getDialog().isShowing();
    }

    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        mOnDismissListener = onDismissListener;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (mOnDismissListener != null)
            mOnDismissListener.onDismiss(dialog);
    }

    //获取设备屏幕高度
    private int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    @Override
    public void onDestroyView() {
        Dialog dialog = getDialog();
        if (dialog != null)
            dialog.setCancelMessage(null);
        // https://codeday.me/bug/20180423/157579.html
        if ((dialog != null) && getRetainInstance())
            dialog.setDismissMessage(null);
        super.onDestroyView();
    }

}
