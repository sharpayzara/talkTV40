package com.sumavision.talktv4.dao;

import android.content.Context;

import com.sumavision.talktv4.dao.ormlite.OrmLiteDatabaseHelper;

/**
 *  desc  ormlite操作数据库Helper
 *  @author  yangjh
 *  created at  16-5-24 下午5:31
 */
public class DatabaseHelper extends OrmLiteDatabaseHelper {

    /**
     * 数据库名称
     */
    private static final String DATABASE_NAME = "tvFan.db";

    /**
     * 数据库版本号
     */
    private static final int DATABASE_VERSION = 4;
    private static DatabaseHelper instance;

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            synchronized (DatabaseHelper.class) {
                if (instance == null) {
                    instance = new DatabaseHelper(context);
                }
            }
        }
        return instance;
    }

    /**
     * 注册数据表
     */

}
