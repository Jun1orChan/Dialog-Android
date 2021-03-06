package org.jun1or.dialog;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import org.jun1or.dialog.base.BaseDialogFragment;
import org.jun1or.widget.progress.HorizentalProgressBar;


/**
 * @author Administrator
 */
public class ProgressBarDialog extends BaseDialogFragment {

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
    public View getDialogView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.dialoglib_progressbar, null, false);
        initViews(inflate);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        return inflate;
    }

    private void initViews(View view) {
        mPbar = (HorizentalProgressBar) view.findViewById(R.id.progressBar);
        mTvLeftMsg = (TextView) view.findViewById(R.id.tvLeftMsg);
        mTvRightMsg = (TextView) view.findViewById(R.id.tvRightMsg);
        mTvTitleMsg = (TextView) view.findViewById(R.id.tvTitle);
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
        mTvCancel = (TextView) view.findViewById(R.id.tvCancel);
        mTvCancel.setText(mCancelText);
        if (mCancelClickListener != null) {
            mTvCancel.setOnClickListener(mCancelClickListener);
        } else {
            mTvCancel.setVisibility(View.GONE);
        }
    }


    /**
     * ????????????
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
     * ??????????????????
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
     * ??????????????????
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
     * ??????????????????
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
