package com.example.healthcare.viewmodels;

public class Upload {
    // this class contians a name and an image url
    private String mName;
    private String mImageUrl;

    public Upload(){
        // empty constructotor needed..c
    }

    public Upload(String name, String imageUrl){
       if(name.trim().equals("")){
           // if it is an empty string then no name
           name = "No Name";
       }

        mName = name;
        mImageUrl = imageUrl;
    }

    public String getmName(){

        return mName;
    }

    public void setmName(String name){
        mName = name;
    }

    public String getmImageUrl(){
        return mImageUrl;
    }

    public void setmImageUrl(String imageUrl){
        mImageUrl = imageUrl;
    }
}
