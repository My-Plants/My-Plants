package com.example.myplants;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class InfoFragment extends Fragment {
    Button back_btn;
    FragmentManager fmanager;
    FragmentTransaction ftrans;
    SetFragment setFrag;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_info,
                container, false);
        // Inflate the layout for this fragment
        fmanager = getFragmentManager();
        ftrans = fmanager.beginTransaction();
        back_btn = rootView.findViewById(R.id.backBtn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFrag = new SetFragment();
                ftrans.replace(R.id.container, setFrag).commit();
            }
        });
        return rootView;
    }
}
