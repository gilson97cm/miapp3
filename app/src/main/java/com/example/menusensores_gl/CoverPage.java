package com.example.menusensores_gl;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CoverPage extends Fragment {
    TextView txtTitleCoverPage;
    TextView txtNAme1CP;
    TextView txtName2CP;
    TextView txtLevelCP;
    TextView txtDateCP;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cover_page, container, false);
         txtTitleCoverPage = (TextView) view.findViewById(R.id.txtTitleCoverPage);
        txtNAme1CP = (TextView) view.findViewById(R.id.txtNAme1CP);
        txtName2CP = (TextView) view.findViewById(R.id.txtName2CP);
        txtLevelCP = (TextView) view.findViewById(R.id.txtLevelCP);
        txtDateCP = (TextView) view.findViewById(R.id.txtDateCP);

        loadData();
        return view;
    }

    private void loadData(){
        String title = "Menú de Sensores";
        String name1 = "Chariguamán Gilson";
        String name2 = "Quilumbaquin Nataly";
        String level = "9 'A'";
        String date = "20/12/2019";
        txtTitleCoverPage.setText(title);
        txtNAme1CP.setText(name1);
        txtName2CP.setText(name2);
        txtLevelCP.setText(level);
        txtDateCP.setText(date);
    }
}
