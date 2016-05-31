package com.sumavision.talktv4.model.entity;

import java.io.Serializable;

/**
 * Created by zhoutao on 2016/5/27.
 */
public class ClassifyItem implements Serializable {
    public int id;//分类
    public String img;//分类图片地址
    public String txt;//分类名

    public ClassifyItem(){}
    public ClassifyItem(int id,String img,String txt){
            this.id = id;
        this.img=img;
        this.txt = txt;
    }
}
