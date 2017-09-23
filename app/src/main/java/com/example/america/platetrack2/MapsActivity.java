package com.example.america.platetrack2;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.google.android.gms.common.SignInButton;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class MapsActivity extends ActionBarActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    public static ArrayList<Route> routes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        googleMap.clear();

        mMap = googleMap;
        mMap.setMinZoomPreference(14.0f);

        if (MapsActivity.routes != null && MapsActivity.routes.size() > 0) {



            // MapsActivity.plateCaptures to load LatLngs and create routes
            // Example

            int[] colors = new int[] {Color.RED, Color.GREEN, Color.BLUE, Color.BLACK, Color.CYAN, Color.YELLOW, Color.MAGENTA};
            int colorId = 0;
            PlateCapture previous = null;
            for (Route r : MapsActivity.routes)
            {
                for (PlateCapture pc : r.points) {
                    if (previous == null) {
                        previous = pc;
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(pc.getLatitude(), pc.getLongitude())));
                    }
                    else {
                        LatLng start = new LatLng(previous.getLatitude(), previous.getLongitude());
                        LatLng end = new LatLng( pc.getLatitude(),pc.getLongitude());
                        route(start, end, GMapV2Direction.MODE_DRIVING, colors[colorId]);
                        previous = pc;
                    }
                }
                colorId++;
                if (colorId == 7) {
                    colorId = 0;
                }
            }


        }
    }


//        // mMap.setMyLocationEnabled(true);
//        // Add a marker in Sydney and move the camera
//        LatLng springfield = new LatLng(37.2090, -93.2923);
//        LatLng springfield2 = new LatLng(37.207096, -93.292449);
//        LatLng springfield3 = new LatLng(37.206301, -93.292361);
//        LatLng springfield4 = new LatLng(37.000, -93.200);
//
////        mMap.addMarker(new MarkerOptions().position(springfield).title("Marker in Springfield"));
////
////        mMap.addMarker(new MarkerOptions().position(springfield2).title("Marker in Springfield2"));
//
//
//
//
//        route(springfield2, springfield, GMapV2Direction.MODE_DRIVING, Color.BLUE);
//        route(springfield, springfield2, GMapV2Direction.MODE_DRIVING, Color.RED);
//
//        route(springfield3, springfield4, GMapV2Direction.MODE_WALKING, Color.GREEN);

    protected void route(LatLng sourcePosition, LatLng destPosition, String mode, final int color) {
        final Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                try {
                    Document doc = (Document) msg.obj;
                    GMapV2Direction md = new GMapV2Direction();
                    ArrayList<LatLng> directionPoint = md.getDirection(doc);
                    PolylineOptions rectLine = new PolylineOptions().width(3).color(color);

                    for (int i = 0; i < directionPoint.size(); i++) {
                        rectLine.add(directionPoint.get(i));
                    }
                    Polyline polylin = mMap.addPolyline(rectLine);
                    md.getDurationText(doc);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            ;
        };

        new GMapV2DirectionAsyncTask(handler, sourcePosition, destPosition, mode, color).execute();

    }

    /**
     * Get a diff between two dates
     * @param date1 the oldest date
     * @param date2 the newest date
     * @param timeUnit the unit in which you want the diff
     * @return the diff value, in the provided unit
     */
    private long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies,TimeUnit.MINUTES);
    }
}