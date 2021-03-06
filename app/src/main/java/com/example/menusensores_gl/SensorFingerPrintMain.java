package com.example.menusensores_gl;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.hardware.SensorEventListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.menusensores_gl.fingerPrint.SensorFingerPrintExample;

public class SensorFingerPrintMain extends Fragment implements SensorEventListener, View.OnClickListener {

    TextView txtTitleS2;
    TextView txtDescriptionS2;
    Button btnViewExample2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sensor_fingerprint_main, container, false);
        txtTitleS2 = (TextView) view.findViewById(R.id.txtTitleS2);
        txtDescriptionS2 = (TextView) view.findViewById(R.id.txtDescriptionS2);
        btnViewExample2 = (Button) view.findViewById(R.id.btnViewExampleDos);

        btnViewExample2.setOnClickListener(this);
        loadData();
        return view;
    }

    //metodos de los sensores
    @Override
    public void onSensorChanged(SensorEvent event) {


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnViewExampleDos:
                Toast.makeText(v.getContext(), "Ejemplo" , Toast.LENGTH_SHORT).show();
                Fragment viewExample2 = new SensorFingerPrintExample();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, viewExample2);
                transaction.addToBackStack(null);

                // Commit a la transacción
                transaction.commit();
                break;
        }
    }

    private void loadData(){
        String title = "Sensor de Huella";
        String description = "Un sensor de huella digital (también conocido como sensor de huella dactilar, lector de huella dactilar o sensor biométrico) es un dispositivo que es capaz de leer, guardar e identificar las huellas dactilares (Generalmente del dedo pulgar, aunque la mayoría no tienen problemas en aceptar los demás dedos). Todos los sensores biométricos cuentan mínimamente con una pieza que es sensible al tacto.";
        txtTitleS2.setText(title);
        txtDescriptionS2.setText(description);
    }
}
