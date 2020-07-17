package com.example.minitiktok.base;


import android.app.Application;

public class Data extends Application {
    private String b;
    private String a;

    public String getB() {
        return this.b;
    }

    public String getA(){
        return this.a;
    }

    public void setB(String c) {
        this.b = c;
    }

    public void setA(String c){
        this.a = c;
    }

    @Override
    public void onCreate() {
        b = "user";
        a = "18888916233";
        super.onCreate();
    }
}