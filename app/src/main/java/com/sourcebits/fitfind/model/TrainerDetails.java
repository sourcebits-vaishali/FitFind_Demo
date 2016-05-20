package com.sourcebits.fitfind.model;

import android.graphics.Bitmap;

/**
 * Created by vaishaliarora on 19/05/16.
 */
public class TrainerDetails {

    private String mName;
    private String mLocation;
    private String mSex;
    private double mDistance;
    private Bitmap mPicture;

    public TrainerDetails(String name, String location, String sex ,double distance, Bitmap picture){
        mName = name;
        mLocation = location;
        mSex = sex;
        mDistance = distance;
        mPicture = picture;
    }

    public String getName(){
        return mName;
    }

    public String getLocation(){
        return mLocation;
    }

    public double getDistance(){
        return mDistance;
    }

    public Bitmap getPicture(){
        return mPicture;
    }
    public String getSex() {
        return mSex;
    }
}
