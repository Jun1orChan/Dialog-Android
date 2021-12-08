package com.nd.dialog;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.nd.dialog.base.BaseDialogFragment;
import com.nd.widget.progress.JCircleProgress;


/**
 * @author cwj
 */
public class LoadingDialog extends BaseDialogFragment {

    private TextView mTvText;
    private JCircleProgress mProgress;
    private CharSequence mLoadingText;

    @Override
    public View getDialogView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialoglib_loading, null, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        mProgress = view.findViewById(R.id.progress);
        mTvText = view.findViewById(R.id.tvText);
        if (!TextUtils.isEmpty(mLoadingText)) {
            mTvText.setText(mLoadingText);
        }
    }

    @Override
    public void onDestroyView() {
        if (mProgress != null) {
            mProgress.cancelAnimation();
        }
        super.onDestroyView();
    }

    /**
     * 设置loading文字
     *
     * @param loadingText
     */
    public LoadingDialog loadingText(CharSequence loadingText) {
        mLoadingText = loadingText;
        if (mTvText != null) {
            mTvText.setText(loadingText);
        }
        return this;
    }
}
