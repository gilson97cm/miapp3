package com.example.menusensores_gl;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


public class SensorDosExample extends Fragment implements View.OnClickListener {

    Button btnBackViewDos;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sensor_dos_example, container, false);
        btnBackViewDos = (Button) view.findViewById(R.id.btnBackViewDos);
        btnBackViewDos.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBackViewDos:
                //Customize
                btnBackViewDos.setTextColor(Color.WHITE);
                //Action
                Fragment viewMain = new SensorDosMain();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, viewMain);
                transaction.addToBackStack(null);

                // Commit a la transacción
                transaction.commit();
                break;
        }
    }
}
