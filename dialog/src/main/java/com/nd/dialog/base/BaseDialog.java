package com.nd.dialog.base;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.ref.WeakReference;

/**
 * @author cwj
 */
public class BaseDialog extends Dialog {

    public BaseDialog(@NonNull Context context) {
        super(context);
    }

    public BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected BaseDialog(@NonNull Context context, boolean cancelable, @Nullable DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    public void setOnDismissListener(@Nullable DialogInterface.OnDismissListener listener) {
        //listener 实际上是DialogFragment
        super.setOnDismissListener(new DialogDismissListener(listener));
    }

    @Override
    public void setOnCancelListener(@Nullable DialogInterface.OnCancelListener listener) {
//        super.setOnCancelListener(listener);
    }

    @Override
    public void setOnShowListener(@Nullable DialogInterface.OnShowListener listener) {
//        super.setOnShowListener(listener);
    }

    public static class DialogDismissListener implements DialogInterface.OnDismissListener {
        private WeakReference<DialogInterface.OnDismissListener> leakDialogFragmentWeakReference;

        public DialogDismissListener(DialogInterface.OnDismissListener listener) {
            this.leakDialogFragmentWeakReference = new WeakReference<>(listener);
        }

        @Override
        public void onDismiss(DialogInterface dialog) {
            DialogInterface.OnDismissListener listener = leakDialogFragmentWeakReference.get();
            if (listener != null) {
                listener.onDismiss(dialog);
            }
        }
    }
}
