package com.nd.dialog;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nd.dialog.base.BaseDialogFragment;
import com.nd.widget.progress.HorizentalProgressBar;

/**
 * @author Administrator
 */
public class ProgressBarDialog extends BaseDialogFragment {

    private static final String KEY_SAVE_STATE_LEFT_MSG = "save_state_left_msg";
    private static final String KEY_SAVE_STATE_RIGHT_MSG = "save_state_right_msg";
    private static final String KEY_SAVE_STATE_TITLE = "save_state_title";
    private static final String KEY_SAVE_STATE_CANCEL = "save_state_cancel";

    private HorizentalProgressBar mPbar = null;
    private TextView mTvLeftMsg = null;
    private TextView mTvRightMsg = null;
    private TextView mTvTitleMsg = null;
    private TextView mTvCancel = null;


    private CharSequence mLeftMsg = null;
    private CharSequence mRightMsg = null;
    private CharSequence mTitleMsg = null;
    private CharSequence mCancelText = null;

    private View.OnClickListener mCancelClickListener;

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence(KEY_SAVE_STATE_LEFT_MSG, mLeftMsg);
        outState.putCharSequence(KEY_SAVE_STATE_RIGHT_MSG, mRightMsg);
        outState.putCharSequence(KEY_SAVE_STATE_TITLE, mTitleMsg);
        outState.putCharSequence(KEY_SAVE_STATE_CANCEL, mCancelText);
    }

    @Override
    public View getDialogView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mLeftMsg = savedInstanceState.getCharSequence(KEY_SAVE_STATE_LEFT_MSG);
            mRightMsg = savedInstanceState.getCharSequence(KEY_SAVE_STATE_RIGHT_MSG);
            mTitleMsg = savedInstanceState.getCharSequence(KEY_SAVE_STATE_TITLE);
            mCancelText = savedInstanceState.getCharSequence(KEY_SAVE_STATE_CANCEL);
        }
        View inflate = inflater.inflate(R.layout.dialoglib_progressbar, null, false);
        initViews(inflate);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        return inflate;
    }

    private void initViews(View view) {
        mPbar = view.findViewById(R.id.progressBar);
        mTvLeftMsg = view.findViewById(R.id.tvLeftMsg);
        mTvRightMsg = view.findViewById(R.id.tvRightMsg);
        mTvTitleMsg = view.findViewById(R.id.tvTitle);
        mPbar.setProgress(0);
        mTvTitleMsg.setText(mTitleMsg);
        mTvLeftMsg.setText(mLeftMsg);
        if (TextUtils.isEmpty(mLeftMsg)) {
            mTvLeftMsg.setVisibility(View.GONE);
        }
        mTvRightMsg.setText(mRightMsg);
        if (TextUtils.isEmpty(mRightMsg)) {
            mTvRightMsg.setVisibility(View.GONE);
        }
        mTvCancel = view.findViewById(R.id.tvCancel);
        mTvCancel.setText(mCancelText);
        if (mCancelClickListener != null) {
            mTvCancel.setOnClickListener(mCancelClickListener);
        } else {
            mTvCancel.setVisibility(View.GONE);
        }
    }


    /**
     * 设置进度
     *
     * @param progress
     */
    public ProgressBarDialog progress(int progress) {
        if (mPbar != null) {
            mPbar.setProgress(progress);
        }
        return this;
    }

    /**
     * 设置左边文本
     *
     * @param msg
     */
    public ProgressBarDialog leftMsg(CharSequence msg) {
        mLeftMsg = msg;
        if (mTvLeftMsg != null) {
            if (!TextUtils.isEmpty(msg)) {
                mTvLeftMsg.setVisibility(View.VISIBLE);
                mTvLeftMsg.setText(msg);
            }
        }
        return this;
    }

    /**
     * 设置右边文本
     *
     * @param msg
     */

    public ProgressBarDialog rightMsg(CharSequence msg) {
        mRightMsg = msg;
        if (mTvRightMsg != null) {
            if (!TextUtils.isEmpty(msg)) {
                mTvRightMsg.setVisibility(View.VISIBLE);
                mTvRightMsg.setText(msg);
            }
        }
        return this;
    }

    /**
     * 设置标题文本
     *
     * @param msg
     */
    public ProgressBarDialog titleMsg(CharSequence msg) {
        mTitleMsg = msg;
        if (mTvTitleMsg != null) {
            mTvTitleMsg.setText(msg);
        }
        return this;
    }

    public ProgressBarDialog cancelText(CharSequence cancelText) {
        mCancelText = cancelText;
        if (mTvCancel != null) {
            mTvCancel.setText(cancelText);
        }
        return this;
    }

    public ProgressBarDialog onCancelListener(View.OnClickListener cancelClickListener) {
        this.mCancelClickListener = cancelClickListener;
        return this;
    }


    private int spToPx(float spValue) {
        float fontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

}
