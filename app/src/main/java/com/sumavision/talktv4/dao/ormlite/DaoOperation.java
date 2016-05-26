package com.sumavision.talktv4.dao.ormlite;

/**
 *  desc  数据库操作类型
 *  @author  yangjh
 *  created at  16-5-24 下午5:29
 */
public interface DaoOperation {

    int INSERT = 1;
    int DELETE = 2;
    int UPDATE = 3;
    int SELECT = 4;
    int INSERT_BATCH = 5;
    int DELETE_BATCH = 6;
    int UPDATE_BATCH = 7;
}
