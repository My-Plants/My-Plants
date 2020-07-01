package com.example.myplants;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class RecoFragment extends Fragment {

    Button level_btn;
    Button size_btn;
    Button clean_btn;
    Button back_btn;
    LevelRecoFragment levelFrag = new LevelRecoFragment();
    SizeRecoFragment sizeFrag = new SizeRecoFragment();
    CleanRecoFragment cleanFrag = new CleanRecoFragment();
    SubmainFragment mainFrag = new SubmainFragment();
    FragmentManager fmanager;
    FragmentTransaction ftrans;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_reco,null);
        fmanager = getFragmentManager();
        ftrans = fmanager.beginTransaction();
        level_btn = rootView.findViewById(R.id.reco1);

        size_btn = rootView.findViewById(R.id.reco2);

        clean_btn = rootView.findViewById(R.id.reco3);

        back_btn = rootView.findViewById(R.id.backBtn);
        //replace fragment with each level plants recommend fragment
        level_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ftrans.replace(R.id.container, levelFrag).commit();
            }
        });
        //replace fragment with each size plants recommend fragment
        size_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ftrans.replace(R.id.container, sizeFrag).commit();
            }
        });
        //replace fragment with air purification plants recommend fragment
        clean_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ftrans.replace(R.id.container, cleanFrag).commit();
            }
        });
        //Back button
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ftrans.replace(R.id.container, mainFrag).commit();
            }
        });

        return  rootView;
    }
}
