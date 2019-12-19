package com.example.menusensores_gl.orientation;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.menusensores_gl.R;
import com.example.menusensores_gl.SensorOrientationMain;
import com.example.menusensores_gl.orientation.view.CompassView;
import com.liuguangqiang.swipeback.SwipeBackActivity;
import com.liuguangqiang.swipeback.SwipeBackLayout;
import com.trycatch.mysnackbar.Prompt;
import com.trycatch.mysnackbar.TSnackbar;

import static android.content.Context.VIBRATOR_SERVICE;


public class SensorOrientationExample extends Fragment implements SensorEventListener, View.OnClickListener {
    private final String SP_CONFIG = "sp_config";
    private final String IS_VIBRATE = "IS_VIBRATE";
    private final String IS_FIRST_OPEN = "IS_FIRST_OPEN";
    private final String[] mDirectionText = new String[]{"Norte", "Noreste", "Este", "Sudeste", "Sur", "Suroeste", "Oeste", "Noroeste"};
    private ViewGroup mViewGroup;
    private TextView mDirection;
    private CompassView mCompassView;
    private TSnackbar accuracyWarnSnackBar;
    private boolean isFirstOpen = true;
    private boolean isVibrate = true;
    private long lastTime = 0;
    private int times = 0;
    private SensorManager mSensorManager;
    String direction = "Desconocido";
    private float lastDirAngel = 0;

     Button btnBackView;

    @Override
    public void onResume() {
        super.onResume();
        init();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sensor_orientation_example, container, false);

        final SharedPreferences sp = getActivity().getSharedPreferences(SP_CONFIG, Context.MODE_PRIVATE);
        isFirstOpen = sp.getBoolean(IS_FIRST_OPEN, true);
        isVibrate = sp.getBoolean(IS_VIBRATE, true);
        // mViewGroup = (ViewGroup) view.findViewById(android.R.id.content).getRootView();
        mDirection = (TextView) view.findViewById(R.id.tv_dir);
        mCompassView = (CompassView) view.findViewById(R.id.compass);

        btnBackView = (Button) view.findViewById(R.id.btnBackView);
        btnBackView.setOnClickListener(this);

        return view;
    }

    @SuppressWarnings("deprecation")
    private void init() {
        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);

        //Regístrate para escuchar
        mSensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST);
    }


    @Override
    public void onPause() {
        super.onPause();
// Cerrar sesión sensor
        mSensorManager.unregisterListener(this);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        float dirAngel = event.values[0];

        mCompassView.setDirectionAngle(dirAngel);
        direction = mDirectionText[((int) (dirAngel + 22.5f) % 360) / 45];
        mDirection.setText(direction);
        if (isVibrate && (int) dirAngel % 30 == 0 && (int) dirAngel != (int) lastDirAngel) {
            Vibrator vibrator = (Vibrator) getActivity().getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(20);
        }
        lastDirAngel = dirAngel;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        if (accuracy != SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM
                && accuracy != SensorManager.SENSOR_STATUS_ACCURACY_HIGH) {
            if (accuracyWarnSnackBar == null) {
                accuracyWarnSnackBar = TSnackbar.make(mViewGroup, "Puede haber interferencia electromagnética cerca,agite el teléfono para calibrar la brújula",
                        TSnackbar.LENGTH_INDEFINITE, TSnackbar.APPEAR_FROM_TOP_TO_DOWN);
                accuracyWarnSnackBar.setPromptThemBackground(Prompt.WARNING);
            }
            accuracyWarnSnackBar.show();
        } else if (accuracyWarnSnackBar != null) {
            accuracyWarnSnackBar.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBackView:
                //Customize
                btnBackView.setTextColor(Color.WHITE);
                //Action
                Fragment viewMain = new SensorOrientationMain();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, viewMain);
                transaction.addToBackStack(null);

                // Commit a la transacción
                transaction.commit();
        }
    }
}
