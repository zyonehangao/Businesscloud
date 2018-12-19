package com.cloud.shangwu.businesscloud.mvp.model.bean;

import java.io.Serializable;

public class Contact implements Serializable {
    private String mName;
    private int mType;
    private boolean isChecked=false;

    public Contact(String name, int type) {
        mName = name;
        mType = type;
    }

    public String getmName() {
        return mName;
    }

    public int getmType() {
        return mType;
    }

    public boolean getIsChecked(){return  isChecked;}

    public void setIsChecked(boolean isChecked){this.isChecked=isChecked;}

}
