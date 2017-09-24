package com.example.america.platetrack2;

import java.sql.Time;
import java.sql.Date;

/**
 * Created by Matthew on 4/29/2017.
 */

public class PlateCapture {
    private Date date;
    private Time time;
    private double latitude;
    private double longitude;

    public PlateCapture(Date d, Time t, double lat, double lon){
        this.date = d;
        this.time = t;
        this.latitude = lat;
        this.longitude = lon;
    }

    public Date getDate(){ return  this.date; }

    public Time getTime() { return  this.time; }

    public double getLatitude() { return this.latitude; }

    public double getLongitude() { return  this.longitude; }
}
