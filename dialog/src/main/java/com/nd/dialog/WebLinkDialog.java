package com.nd.dialog;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.text.HtmlCompat;


/**
 * @author Administrator
 */
public class WebLinkDialog extends MaterialDialog {

    private static final String KEY_SAVE_STATE_CONTENT = "save_state_content";

    private static final String KEY_SAVE_STATE_WEB_LINK_COLOR = "save_state_web_link_color";

    private TextView mTvContent;
    private String mContent;
    private OnWebLinkClickListener mOnWebLinkClickListener;
    private int mWebLinkColor = Color.BLUE;


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_SAVE_STATE_CONTENT, mContent);
        outState.putInt(KEY_SAVE_STATE_WEB_LINK_COLOR, mWebLinkColor);
    }

    @Override
    public View getDialogView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mContent = savedInstanceState.getString(KEY_SAVE_STATE_CONTENT);
            mWebLinkColor = savedInstanceState.getInt(KEY_SAVE_STATE_WEB_LINK_COLOR);
        }
        View view = super.getDialogView(inflater, container, savedInstanceState);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        mTvContent = view.findViewById(R.id.tvContent);
        mTvContent.setAutoLinkMask(Linkify.WEB_URLS);
        Spanned spanned;
        if (mContent == null) {
            return;
        }
        spanned = HtmlCompat.fromHtml(mContent, HtmlCompat.FROM_HTML_MODE_LEGACY);
        if (spanned == null) {
            return;
        }
        mTvContent.setText(getClickableHtml(spanned));
        mTvContent.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public MaterialDialog content(CharSequence content) {
        if (TextUtils.isEmpty(content)) {
            return this;
        }
        mContent = content.toString();
        return this;
    }

    /**
     * 设置网页链接点击监听器
     *
     * @param onWebLinkClickListener
     * @return
     */
    public WebLinkDialog onWebLinkClick(OnWebLinkClickListener onWebLinkClickListener) {
        this.mOnWebLinkClickListener = onWebLinkClickListener;
        return this;
    }

    /**
     * 设置链接字体颜色
     *
     * @param color
     * @return
     */
    public WebLinkDialog webLinkColor(int color) {
        mWebLinkColor = color;
        return this;
    }

    private void setLinkClickable(final SpannableStringBuilder clickableHtmlBuilder, final URLSpan urlSpan) {
        int start = clickableHtmlBuilder.getSpanStart(urlSpan);
        int end = clickableHtmlBuilder.getSpanEnd(urlSpan);
        final char[] chars = new char[end - start];
        clickableHtmlBuilder.getChars(start, end, chars, 0);
        int flags = clickableHtmlBuilder.getSpanFlags(urlSpan);
        ClickableSpan clickableSpan = new ClickableSpan() {

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                ds.setColor(mWebLinkColor);
                ds.setUnderlineText(false); //去掉下划线
            }

            @Override
            public void onClick(View widget) {
//                String originUrl = urlSpan.getURL();//获取url地址
                if (mOnWebLinkClickListener != null) {
                    mOnWebLinkClickListener.onWebLinkClick(new String(chars), urlSpan);
                }
            }
        };
        clickableHtmlBuilder.setSpan(clickableSpan, start, end, flags);
    }

    private CharSequence getClickableHtml(Spanned spannedHtml) {
        SpannableStringBuilder clickableHtmlBuilder = new SpannableStringBuilder(spannedHtml);
        URLSpan[] urls = clickableHtmlBuilder.getSpans(0, spannedHtml.length(), URLSpan.class);
        for (final URLSpan span : urls) {
            setLinkClickable(clickableHtmlBuilder, span);
        }
        return clickableHtmlBuilder;
    }

    public interface OnWebLinkClickListener {
        /**
         * 网页链接被点击
         *
         * @param text
         * @param urlSpan
         */
        void onWebLinkClick(String text, URLSpan urlSpan);
    }
}
