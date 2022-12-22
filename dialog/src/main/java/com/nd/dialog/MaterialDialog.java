package com.nd.dialog;

import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.nd.dialog.base.BaseDialogFragment;

import java.lang.ref.WeakReference;

/**
 * @author cwj
 */
public class MaterialDialog extends BaseDialogFragment {


    private static final String KEY_SAVE_STATE_TITLE = "save_state_title";
    private static final String KEY_SAVE_STATE_CONTENT = "save_state_content";
    private static final String KEY_SAVE_STATE_CANCEL_TEXT = "save_state_cancel_text";
    private static final String KEY_SAVE_STATE_OK_TEXT = "save_state_ok_text";

    private static final String KEY_SAVE_STATE_TITLE_SIZE = "save_state_title_size";
    private static final String KEY_SAVE_STATE_CONTENT_SIZE = "save_state_content_size";
    private static final String KEY_SAVE_STATE_BTN_SIZE = "save_state_btn_size";

    private static final String KEY_SAVE_STATE_TITLE_COLOR = "save_state_title_color";
    private static final String KEY_SAVE_STATE_CONTENT_COLOR = "save_state_content_color";
    private static final String KEY_SAVE_STATE_BTN_CANCEL_COLOR = "save_state_btn_cancel_color";
    private static final String KEY_SAVE_STATE_BTN_OK_COLOR = "save_state_btn_ok_color";


    private CharSequence mTitle;
    private CharSequence mContent;
    private CharSequence mCancelText;
    private CharSequence mOKText;

    private float mTitleSize = -1;
    private float mContentSize = -1;
    private float mBtnSize = -1;

    private int mTitleColor = -1;
    private int mContentColor = -1;
    private int mBtnCancelColor = -1;
    private int mBtnOKColor = -1;

    private TextView mTvTitle, mTvContent;
    private TextView mTvCancel, mTvOK;
    private View.OnClickListener mBtnCancelClickListener, mBtnOKClickListener;

    @Override
    public View getDialogView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mTitle = savedInstanceState.getCharSequence(KEY_SAVE_STATE_TITLE);
            mContent = savedInstanceState.getCharSequence(KEY_SAVE_STATE_CONTENT);
            mCancelText = savedInstanceState.getCharSequence(KEY_SAVE_STATE_CANCEL_TEXT);
            mOKText = savedInstanceState.getCharSequence(KEY_SAVE_STATE_OK_TEXT);

            mTitleSize = savedInstanceState.getFloat(KEY_SAVE_STATE_TITLE_SIZE, -1);
            mContentSize = savedInstanceState.getFloat(KEY_SAVE_STATE_CONTENT_SIZE, -1);
            mBtnSize = savedInstanceState.getFloat(KEY_SAVE_STATE_BTN_SIZE, -1);

            mTitleColor = savedInstanceState.getInt(KEY_SAVE_STATE_TITLE_COLOR, -1);
            mContentColor = savedInstanceState.getInt(KEY_SAVE_STATE_CONTENT_COLOR, -1);
            mBtnCancelColor = savedInstanceState.getInt(KEY_SAVE_STATE_BTN_CANCEL_COLOR, -1);
            mBtnOKColor = savedInstanceState.getInt(KEY_SAVE_STATE_BTN_OK_COLOR, -1);
        }
        View view = inflater.inflate(R.layout.dialoglib_common_tip, null, false);
        initViews(view);
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence(KEY_SAVE_STATE_TITLE, mTitle);
        outState.putCharSequence(KEY_SAVE_STATE_CONTENT, mContent);
        outState.putCharSequence(KEY_SAVE_STATE_CANCEL_TEXT, mCancelText);
        outState.putCharSequence(KEY_SAVE_STATE_OK_TEXT, mOKText);

        outState.putFloat(KEY_SAVE_STATE_TITLE_SIZE, mTitleSize);
        outState.putFloat(KEY_SAVE_STATE_CONTENT_SIZE, mContentSize);
        outState.putFloat(KEY_SAVE_STATE_BTN_SIZE, mBtnSize);

        outState.putInt(KEY_SAVE_STATE_TITLE_COLOR, mTitleColor);
        outState.putInt(KEY_SAVE_STATE_CONTENT_COLOR, mContentColor);
        outState.putInt(KEY_SAVE_STATE_BTN_CANCEL_COLOR, mBtnCancelColor);
        outState.putInt(KEY_SAVE_STATE_BTN_OK_COLOR, mBtnOKColor);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void initViews(View view) {
        mTvTitle = view.findViewById(R.id.tvTitle);
        if (TextUtils.isEmpty(mTitle)) {
            mTvTitle.setVisibility(View.GONE);
        } else {
            mTvTitle.setText(mTitle);
        }
        if (mTitleSize != -1) {
            mTvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTitleSize);
        }
        if (mTitleColor != -1) {
            mTvTitle.setTextColor(mTitleColor);
        }
        mTvContent = view.findViewById(R.id.tvContent);
        mTvContent.setMovementMethod(ScrollingMovementMethod.getInstance());
        mTvContent.setText(mContent);
        if (mContentSize != -1) {
            mTvContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, mContentSize);
        }
        if (mContentColor != -1) {
            mTvContent.setTextColor(mContentColor);
        }
        mTvCancel = view.findViewById(R.id.tvCancel);
        mTvOK = view.findViewById(R.id.tvOK);
        if (mBtnSize != -1) {
            mTvCancel.setTextSize(TypedValue.COMPLEX_UNIT_SP, mBtnSize);
            mTvOK.setTextSize(TypedValue.COMPLEX_UNIT_SP, mBtnSize);
        }
        mTvCancel.setOnClickListener(mBtnCancelClickListener == null ? new DefaultClickListener(this) : mBtnCancelClickListener);
        mTvOK.setOnClickListener(mBtnOKClickListener == null ? new DefaultClickListener(this) : mBtnOKClickListener);
        if (mBtnCancelColor != -1) {
            mTvCancel.setTextColor(mBtnCancelColor);
        }
        if (mBtnOKColor != -1) {
            mTvOK.setTextColor(mBtnOKColor);
        }
        if (TextUtils.isEmpty(mCancelText)) {
            mTvCancel.setVisibility(View.GONE);
        } else {
            mTvCancel.setText(mCancelText);
        }
        if (TextUtils.isEmpty(mOKText)) {
            mOKText = view.getContext().getString(R.string.dialoglib_ok);
        }
        mTvOK.setText(mOKText);
    }

    public MaterialDialog title(CharSequence title) {
        mTitle = title;
        return this;
    }

    public MaterialDialog titleSize(float titleSize_sp) {
        mTitleSize = titleSize_sp;
        return this;
    }

    public MaterialDialog titleColor(int titleColor) {
        mTitleColor = titleColor;
        return this;
    }

    public MaterialDialog content(CharSequence content) {
        mContent = content;
        return this;
    }

    public MaterialDialog contentColor(int contentColor) {
        this.mContentColor = contentColor;
        return this;
    }

    public MaterialDialog contentSize(float contentSize) {
        this.mContentSize = contentSize;
        return this;
    }

    /**
     * 默认按钮是只有一个确定按钮
     *
     * @param btnTexts
     * @return
     */
    public MaterialDialog btnText(CharSequence... btnTexts) {
        if (btnTexts == null || btnTexts.length == 0) {
//            mOKText = getContext().getString(R.string.dialoglib_ok);
        } else if (btnTexts.length == 1) {
            mOKText = btnTexts[0];
        } else if (btnTexts.length >= 2) {
            mCancelText = btnTexts[0];
            mOKText = btnTexts[1];
        }
        return this;
    }

    public MaterialDialog btnSize(float btnSize) {
        mBtnSize = btnSize;
        return this;
    }

    public MaterialDialog btnColor(int... btnColors) {
        if (btnColors == null || btnColors.length == 0) {
            return this;
        } else if (btnColors.length == 1) {
            mBtnOKColor = btnColors[0];
        } else if (btnColors.length >= 2) {
            mBtnCancelColor = btnColors[0];
            mBtnOKColor = btnColors[1];
        }
        return this;
    }

    public MaterialDialog btnClickListener(View.OnClickListener... onClickListeners) {
        if (onClickListeners == null || onClickListeners.length == 0) {
            return this;
        } else if (onClickListeners.length == 1) {
            mBtnOKClickListener = onClickListeners[0];
        } else if (onClickListeners.length >= 2) {
            mBtnCancelClickListener = onClickListeners[0];
            mBtnOKClickListener = onClickListeners[1];
        }
        return this;
    }

    private static class DefaultClickListener implements View.OnClickListener {

        private final WeakReference<DialogFragment> mReference;

        public DefaultClickListener(DialogFragment dialogFragment) {
            mReference = new WeakReference<>(dialogFragment);
        }

        @Override
        public void onClick(View v) {
            if (mReference.get() != null) {
                mReference.get().dismiss();
            }
        }
    }
}
