package com.sumavision.talktv4.util;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;

/**
 *  desc  文件操作辅助类
 *  @author  yangjh
 *  created at  16-5-24 下午5:33
 */
public class FileUtil {

    private FileUtil() {
        throw new UnsupportedOperationException("can not be instanced");
    }

    public static boolean isSDCardEnable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static Uri saveBitmapToSDCard(Bitmap bitmap, String title) {
        File appDir = new File(Environment.getExternalStorageDirectory(), "Gank");
        if (!appDir.exists()) {
            appDir.mkdirs();
        }
        String fileName = title.replace("/","-") + "-girl.jpg";
        File file = new File(appDir,fileName);
        FileOutputStream outputStream;
        try {
            outputStream = new FileOutputStream(file);
            assert bitmap != null;
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return Uri.fromFile(file);
    }

}
