package org.jun1or.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.istrong.dialog.R;
import org.jun1or.dialog.base.BaseDialogFragment;
import com.istrong.widget.progress.JCircleProgress;

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
        mProgress = (JCircleProgress) view.findViewById(R.id.progress);
        mTvText = (TextView) view.findViewById(R.id.tvText);
        if (!TextUtils.isEmpty(mLoadingText)) {
            mTvText.setText(mLoadingText);
        }
    }

    @Override
    public void onDestroyView() {
        if (mProgress != null)
            mProgress.cancelAnimation();
        super.onDestroyView();
    }


    /**
     * 设置loading文字
     *
     * @param loadingText
     */
    public LoadingDialog loadingText(CharSequence loadingText) {
        mLoadingText = loadingText;
        if (mTvText != null)
            mTvText.setText(loadingText);
        return this;
    }
}
