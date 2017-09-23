package com.example.america.platetrack2;

import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by matth on 5/7/2017.
 */

public class Route {
    public ArrayList<PlateCapture> points;

    public boolean isChecked = false;

    public ArrayList<PlateCapture> getPoints() {
        return points;
    }
}
