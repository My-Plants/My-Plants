package com.example.myplants;

class Item {
    private String mName;
    private int msize;
    private int mlevel;
    private String mfeature;
    private String mwatering;
    private String mnickname;
    private String mPhoto;


    Item(String name, int size, int level, String feature, String watering, String nickname,String photo ){
        mName = name;
        msize=size;
        mfeature=feature;
        mlevel=level;
        mwatering=watering;
        mnickname=nickname;
        mPhoto=photo;
    }

    String getPhoto() {
        return mPhoto;
    }

    String getName() {
        return mName;
    }
    String getMfeature() {
        return mfeature;
    }
    String getMnickname() {
        return mnickname;
    }
    String getMwatering() {
        return mwatering;
    }

    int getMsize() {
        return msize;
    }
    int getMlevel() {
        return mlevel;
    }
}