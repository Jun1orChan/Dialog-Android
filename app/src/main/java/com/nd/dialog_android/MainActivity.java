package com.nd.dialog_android;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.nd.dialog.ListDialog;
import com.nd.dialog.LoadingDialog;
import com.nd.dialog.MaterialDialog;
import com.nd.dialog.ProgressBarDialog;
import com.nd.dialog.WebLinkDialog;
import com.nd.dialog.base.BaseDialogFragment;
import com.nd.dialog.datepicker.DatePickerDialog;
import com.nd.dialog.listener.OnItemClickListener;
import com.nd.dialog.listener.TimeResultHandler;
import com.nd.util.AppUtil;
import com.nd.util.DateUtil;
import com.nd.util.StatusBarUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

    private LoadingDialog mLoadingDialog;


    private static final String SAVE_STATE_LIST_DIALOG_TAG = "save_state_list_dialog_tag";

    private static final String SAVE_STATE_PROGRESS_BAR_DIALOG_TAG = "save_state_progress_bar_dialog_tag";

    private static final String SAVE_STATE_MATERIAL_DIALOG_TAG = "save_state_material_dialog_tag";

    private static final String SAVE_STATE_DATE_PICKER_TAG = "save_state_date_picker_tag";

    private static final String SAVE_STATE_WEB_LINK_DIALOG_TAG = "save_state_web_link_dialog_tag";

    ProgressBarDialog progressBarDialog;
    String mProgressBarDialogTag;

    ListDialog mListDialog;
    String mListDialogTag;

    MaterialDialog mMaterialDialog;
    String mMaterialDialogTag;

    DatePickerDialog mDatePickerDialog;
    String mDatePickerTag;

    WebLinkDialog mWebLinkDialog;
    String mWebLinkDialogTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        StatusBarUtil.setStatusBarLightMode(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            mListDialogTag = savedInstanceState.getString(SAVE_STATE_LIST_DIALOG_TAG);
            mListDialog = (ListDialog) getSupportFragmentManager().findFragmentByTag(mListDialogTag);
            if (mListDialog != null && mListDialog.isAdded()) {
                Log.e("TAG", "==mListDialogTag=======================" + mListDialogTag);
                showListDialog(null);
            }

            mProgressBarDialogTag = savedInstanceState.getString(SAVE_STATE_PROGRESS_BAR_DIALOG_TAG);
            progressBarDialog = (ProgressBarDialog) getSupportFragmentManager().findFragmentByTag(mProgressBarDialogTag);
            if (progressBarDialog != null && progressBarDialog.isAdded()) {
                showProgressbarDialog(null);
            }

            mMaterialDialogTag = savedInstanceState.getString(SAVE_STATE_MATERIAL_DIALOG_TAG);
            mMaterialDialog = (MaterialDialog) getSupportFragmentManager().findFragmentByTag(mMaterialDialogTag);
            if (mMaterialDialog != null && mMaterialDialog.isAdded()) {
                showMaterialDialog(null);
            }

            mDatePickerTag = savedInstanceState.getString(SAVE_STATE_DATE_PICKER_TAG);
            mDatePickerDialog = (DatePickerDialog) getSupportFragmentManager().findFragmentByTag(mDatePickerTag);
            if (mDatePickerDialog != null && mDatePickerDialog.isAdded()) {
                showDatePicker(null);
            }

            mWebLinkDialogTag = savedInstanceState.getString(SAVE_STATE_WEB_LINK_DIALOG_TAG);
            mWebLinkDialog = (WebLinkDialog) getSupportFragmentManager().findFragmentByTag(mWebLinkDialogTag);
            if (mWebLinkDialog != null && mWebLinkDialog.isAdded()) {
                showWebLinkDialog(null);
            }
        }
    }

    public void goTestActivity(View view) {
        startActivity(new Intent(this, TestActivity.class));
    }


    public void showMaterialDialog(View view) {
        if (mMaterialDialog == null) {
            mMaterialDialog = new MaterialDialog();
            mMaterialDialogTag = mMaterialDialog.getFragmentTag();
            mMaterialDialog
                    .title("标题")
//                .titleColor(Color.BLUE)
//                .titleSize(17)
                    .content("这是内容")
//                .content("这是一个很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长" +
//                        "很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很" +
//                        "长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长" +
//                        "很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长的文本")
//                .contentColor(Color.BLACK)
//                .contentSize(16)
                    .btnText("取消", "确定")
                    .btnColor(-1, Color.RED);
        }
//        mMaterialDialog.setCanceledOnTouchOutside(false);
//        mMaterialDialog.setCancelable(false);

        mMaterialDialog.btnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "取消Click", Toast.LENGTH_SHORT).show();
                mMaterialDialog.dismiss();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "确定Click", Toast.LENGTH_SHORT).show();
                mMaterialDialog.dismiss();
            }
        }).show(getSupportFragmentManager());

        mMaterialDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                Log.e("TAG", "===================ondismiss");
            }
        });
    }

    public void showLoadingDialog(View view) {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog();
        }
        mLoadingDialog.setDimBackground(false);
//        loadingDialog.setCanceledOnTouchOutside(false);
//        loadingDialog.setCancelable(false);
        mLoadingDialog.show(getSupportFragmentManager());
//        mLoadingDialog.dismiss();
//        new Handler(Looper.getMainLooper()).post(new Runnable() {
//            @Override
//            public void run() {
//                mLoadingDialog.show(getSupportFragmentManager());
//                Log.e("TAG", "isShowing=========" + mLoadingDialog.isShowing());
//            }
//        });
    }


    public void showProgressbarDialog(View view) {
        if (progressBarDialog == null) {
            progressBarDialog = new ProgressBarDialog();
            mProgressBarDialogTag = progressBarDialog.getFragmentTag();
            progressBarDialog
                    .leftMsg("左边文字")
//                .rightMsg("右边文字")
                    .titleMsg("应用下载")
                    .cancelText("Cancel");
        }
        progressBarDialog.onCancelListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TAG", "========Cancel");
                progressBarDialog.dismiss();
            }
        }).show(getSupportFragmentManager());

        Flowable.intervalRange(1, 100, 0, 30, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        progressBarDialog.progress(Integer.parseInt(aLong + ""));
                        progressBarDialog.leftMsg("当前进度:" + aLong);
//                        progressBarDialog.rightMsg("总进度:" + (int) (aLong / 10));
                        if (aLong == 100) {
                            progressBarDialog.dismiss();
                        }
                    }
                });
    }


    public void showListDialog(final View view) {
        if (mListDialog == null) {
            //可能之前已经dismiss
            mListDialog = new ListDialog();
            mListDialogTag = mListDialog
                    .itemArray(new String[]{"选项1", "选项2", "选项3", "选项4"})
                    .getFragmentTag();
        }
        mListDialog
                .itemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Toast.makeText(getApplicationContext(), "===" + position, Toast.LENGTH_SHORT).show();
                        mListDialog.dismiss();
                    }
                })
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {

                    }
                });
        mListDialog.show(getSupportFragmentManager());
    }


    public void showCustomBottomDialog(View view) {
        CustomBottomDialog customBottomDialog = new CustomBottomDialog();
        customBottomDialog.show(getSupportFragmentManager());
    }

    public void showDatePicker(View view) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse("2013-08-08 08:08:08");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date startDate =
                DateUtil.stringToDate("2020-02-28 08:00:32",
                        "yyyy-MM-dd HH:mm", new Date());
        Date endDate =
                DateUtil.stringToDate("2021-10-28 22:00:32",
                        "yyyy-MM-dd HH:mm", new Date());

        if (mDatePickerDialog == null) {
            mDatePickerDialog = new DatePickerDialog();
            mDatePickerTag = mDatePickerDialog.getFragmentTag();
            mDatePickerDialog
                    .startDate(startDate)
                    .endDate(getEndTime())
                    .isLoop(false)
                    .cancelColor(Color.RED)
                    .confirmColor(Color.BLUE)
                    .cancelText("Cancel_T")
                    .confirmText("Confirm_T")
                    .separatorColor(Color.BLUE)
                    .selectedDate(endDate)
                    .mode(DatePickerDialog.MODE_YEAR_MONTH);

        }
        mDatePickerDialog.resultHandler(new TimeResultHandler() {
            @Override
            public void onTimeHandle(Date date) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Toast.makeText(getApplicationContext(), "result:" + sdf.format(date), Toast.LENGTH_SHORT).show();
            }
        })
                .show(getSupportFragmentManager());
    }

    public static Date getEndTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.HOUR_OF_DAY, 1);
        return calendar.getTime();
    }


    public void showWebLinkDialog(View view) {
        if (mWebLinkDialog == null) {
            mWebLinkDialog = new WebLinkDialog();
            mWebLinkDialogTag = mWebLinkDialog.getFragmentTag();
            mWebLinkDialog.setCancelable(false);
            mWebLinkDialog.setCanceledOnTouchOutside(false);
            String content = getString(R.string.app_privacy_policy_tips);
            content = String.format(content, AppUtil.getAppName(this));
            mWebLinkDialog.webLinkColor(Color.parseColor("#0AA451"))
                    .title("提示：")
                    .content(content)
                    .btnText("拒绝", "同意")
                    .btnColor(ContextCompat.getColor(this, R.color.dialoglib_gray), Color.parseColor("#0AA451"));
        }
        mWebLinkDialog
                .onWebLinkClick(new WebLinkDialog.OnWebLinkClickListener() {
                    @Override
                    public void onWebLinkClick(String text, URLSpan urlSpan) {
                        Log.e("TAG", text + "===============" + urlSpan.getURL());
                    }
                })
                .btnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("TAG", "===============拒绝");
                        mWebLinkDialog.dismiss();
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("TAG", "===============同意");
                        mWebLinkDialog.dismiss();
                    }
                })
                .show(getSupportFragmentManager());

    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SAVE_STATE_LIST_DIALOG_TAG, mListDialogTag);
        outState.putString(SAVE_STATE_PROGRESS_BAR_DIALOG_TAG, mProgressBarDialogTag);
        outState.putString(SAVE_STATE_MATERIAL_DIALOG_TAG, mMaterialDialogTag);
        outState.putString(SAVE_STATE_DATE_PICKER_TAG, mDatePickerTag);
        outState.putString(SAVE_STATE_WEB_LINK_DIALOG_TAG, mWebLinkDialogTag);
    }
}
