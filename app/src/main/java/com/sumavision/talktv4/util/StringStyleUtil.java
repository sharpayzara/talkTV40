package com.sumavision.talktv4.util;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;

import com.sumavision.talktv4.model.entity.Gank;

/**
 *  desc  字符串转换
 *  @author  yangjh
 *  created at  16-5-24 下午9:08
 */
public class StringStyleUtil {
    private StringStyleUtil(){

    }
    public static SpannableString getGankStyleStr(Gank gank){
        String gankStr = gank.desc + " @" + gank.who;
        SpannableString spannableString = new SpannableString(gankStr);
        spannableString.setSpan(new RelativeSizeSpan(0.8f),gank.desc.length()+1,gankStr.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.GRAY),gank.desc.length()+1,gankStr.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }
}
