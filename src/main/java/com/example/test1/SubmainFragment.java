package com.example.test1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class SubmainFragment extends Fragment {
    PlantslistFragment plistFragment;
    ShopFragment shopFragment;
    DiaryFragment diaryFragment;
    SetFragment setFragment;
    RecoFragment recoFragment;

    Button plist_btn;
    Button shop_btn;
    Button reco_btn;
    Button diary_btn;
    Button set_btn;

    FragmentManager fmanager;
    FragmentTransaction ftrans;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_submain,null);
        fmanager = getFragmentManager();
        ftrans = fmanager.beginTransaction();

        //When each button is pressed, replace the fragment to each suitable fragment
        plist_btn = rootView.findViewById(R.id.pList_btn);
        plistFragment = new PlantslistFragment();
        plist_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ftrans.replace(R.id.container, plistFragment).commit();
            }
        });

        shop_btn = rootView.findViewById(R.id.shop_btn);
        shopFragment = new ShopFragment();
        shop_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ftrans.replace(R.id.container, shopFragment).commit();
            }
        });
        reco_btn = rootView.findViewById(R.id.reco_btn);
        recoFragment = new RecoFragment();
        reco_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ftrans.replace(R.id.container, recoFragment).commit();
            }
        });
        diary_btn = rootView.findViewById(R.id.diary_btn);
        diaryFragment = new DiaryFragment();
        diary_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ftrans.replace(R.id.container, diaryFragment).commit();
            }
        });
        set_btn = rootView.findViewById(R.id.set_btn);
        setFragment = new SetFragment();
        set_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ftrans.replace(R.id.container, setFragment).commit();
            }
        });
        // Inflate the layout for this fragment
        return  rootView;
    }
}
