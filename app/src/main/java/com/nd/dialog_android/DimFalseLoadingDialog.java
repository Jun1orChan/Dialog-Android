package com.nd.dialog_android;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.nd.dialog.LoadingDialog;

/**
 * 由于dim设置为0f，这个时候如果会导致状态栏变为空白
 * 所以需要自定义主题，参考:R.style.dialoglib_dim_false
 *
 * @author cwj
 * @date 2021/3/2 16:28
 */
public class DimFalseLoadingDialog extends LoadingDialog {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setDimAmount(0f);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public int getTheme() {
        return R.style.dialoglib_dim_false;
    }

}
