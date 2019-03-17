package com.xing.commonbase.receiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.xing.commonbase.util.NetworkUtil;
import com.xing.commonbase.widget.NoNetworkTip;

public class NetworkChangeReceiver extends BroadcastReceiver {

    private static final String TAG = "NetworkChangeReceiver";
    private Activity activity;
    private NoNetworkTip noNetworkTip;

    public NetworkChangeReceiver(Activity activity) {
        this.activity = activity;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG, "onReceive: ");
        if (noNetworkTip == null) {
            noNetworkTip = new NoNetworkTip(activity);
        }
        if (NetworkUtil.isNetworkAvailable(context)) {
            if (noNetworkTip != null && noNetworkTip.isShowing()) {
                noNetworkTip.dismiss();
            }
        } else {
            if (noNetworkTip != null && !noNetworkTip.isShowing()) {
                noNetworkTip.show();
            }
        }
    }

    public void onDestroy() {
        if (noNetworkTip != null && noNetworkTip.isShowing()) {
            noNetworkTip.dismiss();
        }
        activity = null;
        noNetworkTip = null;
    }
}
