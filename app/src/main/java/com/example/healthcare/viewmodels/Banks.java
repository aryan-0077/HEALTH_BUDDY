package com.example.healthcare.viewmodels;

import java.util.ArrayList;

public class Banks {

    private String name;
    private String addr;
    private Double lat;
    private Double lon;
    private ArrayList<String> bloodAvail;

    public Banks() {
        super();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public ArrayList<String> getBloodAvail() {
        return bloodAvail;
    }

    public void setBloodAvail(ArrayList<String> bloodAvail) {
        this.bloodAvail = bloodAvail;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public String getName() {
        return name;
    }

    public String getAddr() {
        return addr;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLon() {
        return lon;
    }
}
