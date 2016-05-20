package com.sourcebits.fitfind.model;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vaishaliarora on 19/05/16.
 */
public class DummyData {

    public static List<TrainerDetails> getData(){
        List<TrainerDetails> results = new ArrayList<>();

        TrainerDetails data1 = new TrainerDetails("ABC","Noida", "Male",1, null);
        TrainerDetails data2 = new TrainerDetails("XYZ","Gurgaon", "Female",0, null);
        TrainerDetails data3 = new TrainerDetails("AA","Bangalore", "None",2, null);
        TrainerDetails data4 = new TrainerDetails("OIIU","Paris", "Female",0.5, null);
        TrainerDetails data5 = new TrainerDetails("SSD","London","Male", 4, null);
        TrainerDetails data6 = new TrainerDetails("CDCED","UK", "Male",6, null);
        TrainerDetails data7 = new TrainerDetails("CSDXXX","Noida", "Female",6, null);
        TrainerDetails data8 = new TrainerDetails("POPOP","Bangalore","Male", 6, null);
        TrainerDetails data9 = new TrainerDetails("SWSW","New Delhi", "",4, null);
        TrainerDetails data10 = new TrainerDetails("BGBGB","Australia","Female", 2, null);
        TrainerDetails data11 = new TrainerDetails("ZAZAZA","Paris", "",3, null);
        TrainerDetails data12 = new TrainerDetails("BHBHB","Pakistan","", 5, null);
        TrainerDetails data13 = new TrainerDetails("LKLKLK","South Africa", "",1, null);
        TrainerDetails data14 = new TrainerDetails("OOOO","China", "",2, null);
        TrainerDetails data15 = new TrainerDetails("YYYY","Japan","Female", 3, null);
        TrainerDetails data16 = new TrainerDetails("XXXX","Noida","Male", 4, null);
        TrainerDetails data17 = new TrainerDetails("DDDD","Gurgaon","Male", 8, null);
        TrainerDetails data18 = new TrainerDetails("HHHH","Chennai", "Female",1, null);
        TrainerDetails data19 = new TrainerDetails("LKLKLK","Kerala","Male", 5, null);
        TrainerDetails data20 = new TrainerDetails("WEWEEW","Mumbai", "Male",10, null);

        results.add(data1);
        results.add(data2);
        results.add(data3);
        results.add(data4);
        results.add(data5);
        results.add(data6);
        results.add(data7);
        results.add(data8);
        results.add(data9);
        results.add(data10);
        results.add(data11);
        results.add(data12);
        results.add(data13);
        results.add(data14);
        results.add(data15);
        results.add(data16);
        results.add(data17);
        results.add(data18);
        results.add(data19);
        results.add(data20);


        return results;
    }
}
