package com.example.menusensores_gl;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

public class SensorProximity extends Fragment implements SensorEventListener {


    //LinearLayout linear1;
    TextView txtOnOFf;
    ImageView imgOnOff;
    TextView txtVal;

    Camera camera;
    Camera.Parameters parameters;
    // TextView txtVal;
    boolean isFlash = false;
    boolean isOn = false;

    @Override
    public void onStop() {
        super.onStop();
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }

    SensorManager smAdminSensor; //administra todos los sensores
    Sensor ssMiSensor; //sensor especifico

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sensor_proximity, container, false);

        txtOnOFf = (TextView) view.findViewById(R.id.txtOnOff);
        imgOnOff = (ImageView) view.findViewById(R.id.imgOnOff);
        txtVal = (TextView) view.findViewById(R.id.txtVal);

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 1000);
        }

        if (getActivity().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
            camera = Camera.open();
            parameters = camera.getParameters();
            isFlash = true;
        }

        smAdminSensor = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        ssMiSensor = smAdminSensor.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        smAdminSensor.registerListener(this, ssMiSensor, SensorManager.SENSOR_DELAY_NORMAL);

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
// Cerrar sesión sensor
        smAdminSensor.unregisterListener(this);
    }

    //metodos de los sensores
    @Override
    public void onSensorChanged(SensorEvent event) {
        float fValor = event.values[0];
        String sValor = String.valueOf(fValor);
        txtVal.setText(sValor);
        if (isFlash) {
            if (!isOn && fValor == 0) {
                //ENCENDIDO
                txtOnOFf.setText("ON");
                  imgOnOff.setImageResource(R.drawable.on);
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                camera.setParameters(parameters);
                camera.startPreview();
                isOn = true;

            } else {
                //APAGADO
                txtOnOFf.setText("OFF");
                imgOnOff.setImageResource(R.drawable.off);
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                camera.setParameters(parameters);
                camera.stopPreview();
                isOn = false;

            }
        } else {
            Toast.makeText(getContext(), "No Tiene flash", Toast.LENGTH_SHORT).show();
        }

        //txtVal.setText(sValor);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


}
