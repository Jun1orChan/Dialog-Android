package org.jun1or.dialog.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.istrong.dialog.R;

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
