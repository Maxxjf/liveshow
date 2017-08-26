package com.qcloud.liveshow.beans;

/**
 * 类说明：
 * Author: Kuzan
 * Date: 2017/8/26 16:15.
 */
public class PagerItemBean {
    private boolean select;
    private int index;
    private Object o;

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Object getO() {
        return o;
    }

    public void setO(Object o) {
        this.o = o;
    }
}
