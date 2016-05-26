package com.sumavision.talktv4.util;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 *  desc  图像工具类
 *  @author  yangjh
 *  created at  16-5-18 下午4:30
 */
public class BitmapUtil {

    /**
     * desc 获取Asset中的图片
     */
    public static Bitmap getAssetBitmap(Context context, String path) {
        Bitmap mBitmap = null;
        try {
            InputStream is = context.getAssets().open(path);
            mBitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mBitmap;
    }
}
