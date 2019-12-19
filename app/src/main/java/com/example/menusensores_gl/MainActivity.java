package com.example.menusensores_gl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.widget.Toast;

import com.shrikanthravi.customnavigationdrawer2.data.MenuItem;
import com.shrikanthravi.customnavigationdrawer2.widget.SNavigationDrawer;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    SNavigationDrawer navigationDrawer;
    Class fragmentClass;
    public static Fragment fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationDrawer = (SNavigationDrawer) findViewById(R.id.navigationDrawer);
        //Menu add
        List<MenuItem> menuItems = new ArrayList<MenuItem>();

        menuItems.add((MenuItem) new MenuItem("Portada", R.drawable.item0));
        menuItems.add((MenuItem) new MenuItem("Sensor de Proximidad", R.drawable.item1));
        menuItems.add((MenuItem) new MenuItem("Sensor Acelerómetro", R.drawable.item2));
        menuItems.add((MenuItem) new MenuItem("Sensor de Orientación", R.drawable.item3));
        menuItems.add((MenuItem) new MenuItem("Sensor 2", R.drawable.item4));

        navigationDrawer.setMenuItemList(menuItems);

        fragmentClass = CoverPage.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in,
                    android.R.animator.fade_out).replace(R.id.frameLayout, fragment).commit();
        }

        navigationDrawer.setOnMenuItemClickListener(new SNavigationDrawer.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClicked(int position) {
                System.out.println("Position " + position);

                switch (position) {
                    case 0: {
                        fragmentClass = CoverPage.class;
                        Toast.makeText(getApplicationContext(), "portada", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case 1: {
                        fragmentClass = SensorProximity.class;
                        Toast.makeText(getApplicationContext(), "proximidad", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case 2: {
                        fragmentClass = SensorAccelerometer.class;
                        Toast.makeText(getApplicationContext(), "acelerómetro", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case 3: {
                        fragmentClass = SensorOrientationMain.class;
                        Toast.makeText(getApplicationContext(), "orientación", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case 4: {
                        fragmentClass = SensorDosMain.class;
                        Toast.makeText(getApplicationContext(), "sensor 2", Toast.LENGTH_SHORT).show();
                        break;
                    }

                }

                //Listener for drawer events such as opening and closing.
                navigationDrawer.setDrawerListener(new SNavigationDrawer.DrawerListener() {

                    @Override
                    public void onDrawerOpened() {

                    }

                    @Override
                    public void onDrawerOpening() {

                    }

                    @Override
                    public void onDrawerClosing() {
                        System.out.println("Drawer closed");

                        try {
                            fragment = (Fragment) fragmentClass.newInstance();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (fragment != null) {
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frameLayout, fragment).commit();

                        }
                    }

                    @Override
                    public void onDrawerClosed() {

                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        System.out.println("State " + newState);
                    }
                });
            }
        });

    }
}
