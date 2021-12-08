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


/**
 * @author Administrator
 */
public class WebLinkDialog extends MaterialDialog {

    private TextView mTvContent;
    private String mContent;
    private OnWebLinkClickListener mOnWebLinkClickListener;
    private int mWebLinkColor = Color.BLUE;

    @Override
    public View getDialogView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.getDialogView(inflater, container, savedInstanceState);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        mTvContent = view.findViewById(R.id.tvContent);
        mTvContent.setAutoLinkMask(Linkify.WEB_URLS);
        Spanned spanned;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            spanned = Html.fromHtml(mContent, Html.FROM_HTML_MODE_LEGACY);
        } else {
            spanned = Html.fromHtml(mContent);
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
