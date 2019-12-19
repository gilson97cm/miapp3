package com.example.menusensores_gl;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.menusensores_gl.orientation.SensorOrientationExample;


public class SensorOrientationMain extends Fragment implements  View.OnClickListener {


  Button viewExample;
    Context context;
    TextView txtTitleSO;
    TextView txtDescriptionSO;
    Class fragmentClass;
    public static Fragment fragment;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sensor_orientation_main, container, false);

      context = view.getContext();
      viewExample = (Button) view.findViewById(R.id.btnViewExample);
      txtTitleSO = (TextView) view.findViewById(R.id.txtTitleSO);
      txtDescriptionSO =(TextView) view.findViewById(R.id.txtDescriptionSO);
      viewExample.setOnClickListener(this);

      loadData();
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnViewExample:
                Toast.makeText(v.getContext(), "Ejemplo" , Toast.LENGTH_SHORT).show();
                Fragment viewExample = new SensorOrientationExample();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, viewExample);
                transaction.addToBackStack(null);

                // Commit a la transacción
                transaction.commit();
        }
    }

    private void loadData(){
        String title = "Sensor de Orientación";
        String description = "El sensor de orientación da información al dispositivo (un terminal móvil en este caso) del valor del acimut en grados sexagesimales y, las rotaciones pitch y roll, no se trata de un sensor físico, sino que es un sensor virtual cuyo resultado se obtiene combinando varios sensores (normalmente la brújula y el acelerómetro).  ";
        txtTitleSO.setText(title);
        txtDescriptionSO.setText(description);
    }
}
