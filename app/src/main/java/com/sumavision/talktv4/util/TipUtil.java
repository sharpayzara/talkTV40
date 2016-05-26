package com.sumavision.talktv4.util;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 *  desc  用于显示提示信息用于显示提示信息
 *  @author  yangjh
 *  created at  16-5-24 下午5:33
 */
public class TipUtil {

    private TipUtil() {

    }

    public static void showTipWithAction(View view, String tipText, String actionText, View.OnClickListener listener) {
        Snackbar.make(view, tipText, Snackbar.LENGTH_INDEFINITE).setAction(actionText, listener).show();
    }

    public static void showTipWithAction(View view, String tipText, String actionText, View.OnClickListener listener,int duration){
        Snackbar.make(view, tipText, duration).setAction(actionText, listener).show();
    }

    public static void showSnackTip(View view, String tipText) {
        Snackbar.make(view, tipText, Snackbar.LENGTH_SHORT).show();
    }
}
