package com.sumavision.talktv4.model.entity;

import java.io.Serializable;

/**
 * 这是具体更新分类的javabean
 * Created by zhoutao on 2016/5/27.
 */
public class ClassifyUpdataItem implements Serializable {
    public int id;//分类
    public String img;//分类图片地址
    public String txt;//分类名
    public int position;//该条目需要更新到的位置
    public int isadd;//本次更新是增加还是删除操作
    public int isshow;//该条目是否需要特殊显示

}
