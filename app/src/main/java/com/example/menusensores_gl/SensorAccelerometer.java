package com.example.menusensores_gl;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.menusensores_gl.accelerometer.GraphView;

import static android.content.Context.SENSOR_SERVICE;

public class SensorAccelerometer extends Fragment implements SensorEventListener {

    private final static String TAG = "MainActivity";

    private TextView rateView, accuracyView;
    private GraphView xView, yView, zView;

    private SensorManager sensorMgr;
    private Sensor accelerometer;

    private final static long GRAPH_REFRESH_WAIT_MS = 20;

    private GraphRefreshThread th = null;
    private Handler handler;

    private float vx, vy, vz;
    private float rate;
    private int accuracy;
    private long prevts;
    private final int N = 5;
    private float[] ax = new float[N];
    private float[] ay = new float[N];
    private float[] az = new float[N];
    private int idx = 0;
    private int idy = 0;
    private int idz = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sensor_accelerometer, container, false);

      //  rateView = (TextView) view.findViewById(R.id.rate_view);
       // accuracyView = (TextView) view.findViewById(R.id.accuracy_view);
        xView = (GraphView) view.findViewById(R.id.x_view);
        yView = (GraphView) view.findViewById(R.id.y_view);
        zView = (GraphView) view.findViewById(R.id.z_view);

        sensorMgr = (SensorManager) getActivity().getSystemService(SENSOR_SERVICE);
        accelerometer = sensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        handler = new Handler();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
        sensorMgr.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_FASTEST);
        th = new GraphRefreshThread();
        th.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
        th = null;
        sensorMgr.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        ax[idx] = event.values[0];
        float sx =0;
        for(int i=0; i<N; i++){
            sx = sx + ax[i];
            vx = sx/N;
            idx = (idx+1)%N;
        }

        ay[idy] = event.values[1];
        float sy =0;
        for(int i=0; i<N; i++){
            sy = sy + ay[i];
            vy = sy/N;
            idy = (idy+1)%N;
        }

        az[idz] = event.values[2];
        float sz =0;
        for(int i=0; i<N; i++){
            sz = sz + az[i];
            vz = sz/N;
            idz = (idz+1)%N;
        }
        rate = ((float) (event.timestamp - prevts)) / (1000 * 1000);
        prevts = event.timestamp;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.i(TAG, "onAccuracyChanged: ");
        this.accuracy = accuracy;
    }

    private class GraphRefreshThread extends Thread {
        public void run() {
            try {
                while (th != null) {
                    handler.post(new Runnable() {
                        public void run() {
                           // rateView.setText(Float.toString(rate));
                            //accuracyView.setText(Integer.toString(accuracy));
                            xView.addData(vx, true);
                            yView.addData(vy, true);
                            zView.addData(vz, true);
                        }
                    });
                    Thread.sleep(GRAPH_REFRESH_WAIT_MS);
                }
            }
            catch (InterruptedException e) {
                Log.e(TAG, e.toString());
                th = null;
            }
        }
    }
}
