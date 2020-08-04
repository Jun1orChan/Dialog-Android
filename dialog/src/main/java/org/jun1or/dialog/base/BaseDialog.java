package org.jun1or.dialog.base;

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

    protected BaseDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    public void setOnDismissListener(@Nullable OnDismissListener listener) {
        //listener 实际上是DialogFragment
        super.setOnDismissListener(new DialogDismissListener(listener));
    }

    @Override
    public void setOnCancelListener(@Nullable OnCancelListener listener) {
//        super.setOnCancelListener(listener);
    }

    @Override
    public void setOnShowListener(@Nullable OnShowListener listener) {
//        super.setOnShowListener(listener);
    }

    public static class DialogDismissListener implements OnDismissListener {
        private WeakReference<OnDismissListener> leakDialogFragmentWeakReference;

        public DialogDismissListener(OnDismissListener listener) {
            this.leakDialogFragmentWeakReference = new WeakReference<>(listener);
        }

        @Override
        public void onDismiss(DialogInterface dialog) {
            OnDismissListener listener = leakDialogFragmentWeakReference.get();
            if (listener != null) {
                listener.onDismiss(dialog);
            }
        }
    }
}
