package com.nd.dialog_android;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nd.dialog.ListDialog;
import com.nd.dialog.LoadingDialog;
import com.nd.dialog.MaterialDialog;
import com.nd.dialog.ProgressBarDialog;
import com.nd.dialog.base.BaseDialogFragment;
import com.nd.dialog.datepicker.DatePickerDialog;
import com.nd.dialog.listener.OnItemClickListener;
import com.nd.dialog.listener.TimeResultHandler;
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

    private MaterialDialog mMaterialDialog;
    private LoadingDialog mLoadingDialog;
    ProgressBarDialog progressBarDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        StatusBarUtil.setStatusBarLightMode(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        showMaterialDialog(new View(this));
//        showLoadingDialog(new View(this));
//        showProgressbarDialog(new View(this));
//        showListDialog(new View(this));
//        BaseDialogFragment.setNeedGray(true);
    }

    public void goTestActivity(View view) {
        startActivity(new Intent(this, TestActivity.class));
    }


    public void showMaterialDialog(View view) {
        final MaterialDialog materialDialog = new MaterialDialog();
//        materialDialog.setCancelableOutside(false);
//        materialDialog.setCancelable(false);
        materialDialog
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
                .btnColor(-1, Color.RED)
                .btnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(v.getContext(), "取消Click", Toast.LENGTH_SHORT).show();
                        materialDialog.dismiss();
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(v.getContext(), "确定Click", Toast.LENGTH_SHORT).show();
                        materialDialog.dismiss();
                    }
                }).show(getSupportFragmentManager());
        materialDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                Log.e("TAG", "===================ondismiss");
            }
        });
//        mMaterialDialog.show(getSupportFragmentManager());
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
        progressBarDialog = new ProgressBarDialog();
        progressBarDialog
                .leftMsg("左边文字")
//                .rightMsg("右边文字")
                .titleMsg("应用下载")
                .cancelText("Cancel")
                .onCancelListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("TAG", "========Cancel");
                        progressBarDialog.dismiss();
                    }
                })
                .show(getSupportFragmentManager());
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

    ListDialog mListDialog;

    public void showListDialog(final View view) {
        if (mListDialog == null) {
            mListDialog = new ListDialog();
        }
        mListDialog
                .itemArray(new String[]{"选项1", "选项2", "选项3", "选项4"})
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
        DatePickerDialog datePickerDialog = new DatePickerDialog();
        datePickerDialog
                .startDate(startDate)
                .endDate(getEndTime())
                .isLoop(false)
                .cancelColor(Color.RED)
                .confirmColor(Color.BLUE)
                .cancelText("Cancel_T")
                .confirmText("Confirm_T")
                .separatorColor(Color.BLUE)
                .selectedDate(endDate)
                .mode(DatePickerDialog.MODE_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
                .resultHandler(new TimeResultHandler() {
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
}
