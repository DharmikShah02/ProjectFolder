package com.societypay;

import android.content.Context;
import android.content.SharedPreferences;

public class userinfo {

    SharedPreferences sharedPreferences;
    Context context;

    public  void  removeUser(){
        sharedPreferences.edit().clear().commit();
    }

    public Boolean getMobNo() {
        mobNo=sharedPreferences.getBoolean("MobileNo",false);
        return mobNo;
    }

    public void setMobNo(boolean mobNo) {
        this.mobNo = mobNo;
        sharedPreferences.edit().putBoolean("MobileNo",true).apply();
    }

    private Boolean mobNo;

    public userinfo(Context context)
    {
        this.context=context;
        sharedPreferences=context.getSharedPreferences("UserInformation",Context.MODE_PRIVATE);
    }
}
