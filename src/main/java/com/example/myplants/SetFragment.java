package com.example.myplants;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class SetFragment extends Fragment {

   Button pro_btn;
   Button alarm_btn;
   Button info_btn;
   Button back_btn;
   ProfileFragment proFrag ;
   InfoFragment infoFrag;
   SubmainFragment mainFrag;
   FragmentManager fmanager;
   FragmentTransaction ftrans;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_set,null);
        fmanager = getFragmentManager();
        ftrans = fmanager.beginTransaction();

        pro_btn = rootView.findViewById(R.id.set_pro);
        proFrag = new ProfileFragment();

        alarm_btn = rootView.findViewById(R.id.set_alarm);

        info_btn = rootView.findViewById(R.id.set_info);
        infoFrag = new InfoFragment();
        back_btn = rootView.findViewById(R.id.backBtn);
        mainFrag = new SubmainFragment();
        pro_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ftrans.replace(R.id.container, proFrag).commit();
            }
        });

        info_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ftrans.replace(R.id.container, infoFrag).commit();
            }
        });
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ftrans.replace(R.id.container, mainFrag).commit();
            }
        });
        // Inflate the layout for this fragment
        return  rootView;
    }
}
