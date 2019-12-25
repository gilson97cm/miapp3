package com.example.menusensores_gl.fingerPrint;

import android.content.DialogInterface;
import android.graphics.Color;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.menusensores_gl.R;
import com.example.menusensores_gl.SensorFingerPrintMain;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class SensorFingerPrintExample extends Fragment implements View.OnClickListener {

    Button btnBackViewDos;
    Button purchase_button;
    BiometricPrompt biometricPrompt;
    Executor executor;
    SensorFingerPrintExample sensorFingerPrintExample;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sensor_fingerprint_example, container, false);
        btnBackViewDos = (Button) view.findViewById(R.id.btnBackViewDos);
        btnBackViewDos.setOnClickListener(this);
        purchase_button = (Button) view.findViewById(R.id.purchase_button);
        purchase_button.setOnClickListener(this);
         executor = Executors.newSingleThreadExecutor();
         sensorFingerPrintExample = this;
         biometricPrompt = new BiometricPrompt.Builder(getContext())
                .setTitle("Verificar Compra")
                .setSubtitle("usuario@gmail.com")
                .setDescription("Escanee su huella digital.")
                .setNegativeButton("Cancelar", executor, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(getContext(),"Cancelado.", Toast.LENGTH_SHORT).show();
                    }
                }).build();


        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBackViewDos:
                //Customize
                btnBackViewDos.setTextColor(Color.WHITE);
                //Action
                Fragment viewMain = new SensorFingerPrintMain();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, viewMain);
                transaction.addToBackStack(null);

                // Commit a la transacción
                transaction.commit();
                break;

            case R.id.purchase_button:
                biometricPrompt.authenticate(new CancellationSignal(), executor, new BiometricPrompt.AuthenticationCallback() {
                    @Override
                    public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getContext(),"Pago realizado con éxito", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
                break;
        }
    }
}
