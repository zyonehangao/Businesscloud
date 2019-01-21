package com.cloud.shangwu.businesscloud.mvp.model.bean;

import android.os.Parcel;
import android.os.Parcelable;



public class Contact  implements Parcelable {


    private int mType;
    private boolean isChecked=false;

    public Contact( int type) {
        mType = type;
    }


    protected Contact(Parcel in) {
        mType = in.readInt();
        isChecked = in.readByte() != 0;
    }

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    public int getmType() {
        return mType;
    }

    public boolean getIsChecked(){return  isChecked;}

    public void setIsChecked(boolean isChecked){this.isChecked=isChecked;}



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mType);
        dest.writeByte((byte) (isChecked ? 1 : 0));
    }
}
