package com.example.america.platetrack2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Matthew on 4/29/2017.
 */

public class PlateTracker extends AsyncTask<Void, Void, String> {
    static final String url = "jdbc:mysql://35.184.136.131:3306/PlateTrackDB";
    static final String user = "root";
    static final String pass = "CSC450@2pm";

    ProgressDialog mProgressDialog;
    Context context;
    String plate, start, end;

    ArrayList<PlateCapture> dateLongLat;

    public PlateTracker(Context c, String plate, String start, String end) {
        this.context = c;
        this.plate = plate;
        this.start = start;
        this.end = end;
    }

    protected void onPreExecute() {
        mProgressDialog = ProgressDialog.show(context, "",
                "Searching database, please wait...");
    }

    protected String doInBackground(Void... params) {
        // Perform database query
        try {
            Class.forName("com.mysql.jdbc.Driver");
            java.sql.Connection con = DriverManager.getConnection(PlateTracker.url, PlateTracker.user, PlateTracker.pass);
            java.sql.Statement st = con.createStatement();

//            if (m == method.insert) {
//                st.executeUpdate("INSERT INTO PlateTrackDB.user_plate (user_id, plate_number) VALUES (" + this.userId + ", '" + this.plate +  "');");
//            }
//            else {
//                st.executeUpdate("DELETE FROM PlateTrackDB.user_plate WHERE user_id = " + userId + " AND plate_number = '" + this.plate + "';");
//            }

            java.sql.ResultSet rs = st.executeQuery("SELECT capture_date, capture_time, latitude, longitude FROM PlateTrackDB.plate_capture WHERE plate_number = '" + this.plate +
                    "' AND capture_date BETWEEN STR_TO_DATE('" + start + "', '%m/%d/%Y') AND STR_TO_DATE('" + end + "', '%m/%d/%Y')" +
                    " ORDER BY capture_time;");

            dateLongLat = new ArrayList<>();
            while (rs.next()) {
                dateLongLat.add(new PlateCapture(rs.getDate("capture_date"), rs.getTime("capture_time"), rs.getDouble("latitude"), rs.getDouble("longitude")));
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "Complete";
    }

    protected void onPostExecute(String result) {
        if (result.equals("Complete")) {

            //Intent i = new Intent(context, MapsActivity.class);

            MainActivity.routes = new ArrayList<>();   // TODO: 9/16/17 Create test case to determine where data is going
            Route route = null;
            PlateCapture previous = null;
            for (PlateCapture pc : this.dateLongLat)
            {
                long date = pc.getDate().getTime();
                long time = pc.getTime().getTime();

                // This means its the beginning of the routes
                if (previous == null){
                    previous = pc;
                    route = new Route();
                    route.points = new ArrayList<>();
                    route.points.add(pc);
                }
                // As an example these will all be blue. Eventually we will need to differentiate using the date and time properties and use differing colors.
                else {
                    long dateDiff = pc.getDate().getTime() - previous.getDate().getTime();
                    long timeDiff = pc.getTime().getTime() - previous.getTime().getTime();
                    timeDiff = (timeDiff/1000)/60;
                    // long timeDiff =  getDateDiff(previous.getDate(),pc.getDate(),TimeUnit.MINUTES);
                    if (dateDiff == 0 && timeDiff > 30){
                        MainActivity.routes.add(route);
                        previous = pc;
                        route = new Route();
                        route.points = new ArrayList<>();
                        route.points.add(pc);
                    }
                    else {
                        route.points.add(pc);
                        previous = pc;
                    }
                }

            }
            MainActivity.routes.add(route);

            mProgressDialog.dismiss();

            ((MainActivity) context).showRouteList();

            //context.startActivity(new Intent(context, MapsActivity.class));
        }
    }
}
