package com.example.america.platetrack2;

import android.app.Activity;
import android.os.Bundle;

public class VideoCaptureActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_capture);
        if (null == savedInstanceState) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, VideoCaptureFragment.newInstance())
                    .commit();
        }
    }
}






