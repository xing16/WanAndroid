package com.xing.commonbase.util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {

    private static Toast toast;

    private ToastUtil() {

    }

    public static void show(Context context, int resId) {
        if (toast == null) {
            toast = Toast.makeText(context, resId, Toast.LENGTH_SHORT);
        } else {
            toast.setText(resId);
        }
        toast.show();
    }

    public static void show(Context context, CharSequence text) {
        if (toast == null) {
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        } else {
            toast.setText(text);
        }
        toast.show();
    }
}
