package com.nd.dialog.base;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.nd.dialog.R;


/**
 * @author cwj
 */
public abstract class BaseBottomDialogFragment extends BaseDialogFragment {

    @Override
    public void onStart() {
        setGravity(Gravity.BOTTOM);
        setAnimRes(R.style.dialoglib_anim_bottom);
        setWidthAspect(1);
        super.onStart();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
