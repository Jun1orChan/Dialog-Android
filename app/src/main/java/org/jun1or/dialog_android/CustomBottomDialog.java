package org.jun1or.dialog_android;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jun1or.dialog.base.BaseBottomDialogFragment;

public class CustomBottomDialog extends BaseBottomDialogFragment {
    
    @Override
    public View getDialogView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_bottom, null, false);
    }
}
