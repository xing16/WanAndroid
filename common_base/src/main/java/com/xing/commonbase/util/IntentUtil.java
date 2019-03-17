package com.xing.commonbase.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class IntentUtil {
    private IntentUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static void startActivity(Context context, Class clazz) {
        Intent intent = new Intent(context, clazz);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, Class clazz, Bundle bundle) {
        Intent intent = new Intent(context, clazz);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
