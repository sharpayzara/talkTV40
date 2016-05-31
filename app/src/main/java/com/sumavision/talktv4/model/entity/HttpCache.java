package com.sumavision.talktv4.model.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 *  desc  http304缓存
 *  @author  yangjh
 *  created at  16-5-30 上午10:27
 */
@DatabaseTable(tableName = "http_cache")
public class HttpCache {
    @DatabaseField
    private String urlStr;
    @DatabaseField
    private String ifModifiedSince;
    @DatabaseField
    private String ifNoneMatch;

    public String getUrlStr() {
        return urlStr;
    }

    public void setUrlStr(String urlStr) {
        this.urlStr = urlStr;
    }

    public String getIfModifiedSince() {
        return ifModifiedSince;
    }

    public void setIfModifiedSince(String ifModifiedSince) {
        this.ifModifiedSince = ifModifiedSince;
    }

    public String getIfNoneMatch() {
        return ifNoneMatch;
    }

    public void setIfNoneMatch(String ifNoneMatch) {
        this.ifNoneMatch = ifNoneMatch;
    }
}
