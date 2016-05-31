package com.sumavision.talktv4.model.entity.decor;

import java.io.Serializable;

/**
 * Created by xybcoder on 2016/3/1.
 */
public class BaseData implements Serializable{
    public boolean error;
    public boolean isCacheSource = false;

    public boolean isCacheSource() {
        return isCacheSource;
    }

    public void setCacheSource(boolean cacheSource) {
        isCacheSource = cacheSource;
    }

    @Override
    public String toString() {
        return "BaseData{" +
                "error=" + error +
                '}';
    }
}
