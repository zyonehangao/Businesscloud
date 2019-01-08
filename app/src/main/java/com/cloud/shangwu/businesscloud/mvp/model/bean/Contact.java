package com.cloud.shangwu.businesscloud.mvp.model.bean;

import com.quickblox.users.model.QBUser;

import java.io.Serializable;

public class Contact extends QBUser implements Serializable {

    public QBUser mUser;
    private int mType;
    private boolean isChecked=false;

    public Contact(QBUser user, int type) {
        mUser=user;
        mType = type;
    }


    public int getmType() {
        return mType;
    }

    public boolean getIsChecked(){return  isChecked;}

    public void setIsChecked(boolean isChecked){this.isChecked=isChecked;}

    public String getName(){
        return mUser.getFullName()==null?mUser.getLogin():mUser.getFullName();
    }

}
