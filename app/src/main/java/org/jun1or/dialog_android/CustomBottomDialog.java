package org.jun1or.dialog_android;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import org.jun1or.dialog.base.BaseBottomDialogFragment;

public class CustomBottomDialog extends BaseBottomDialogFragment {
    
    @Override
    public View getDialogView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_bottom, null, false);
    }
}
