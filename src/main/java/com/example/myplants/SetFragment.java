package com.example.myplants;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;


public class SetFragment extends Fragment {

   Button pro_btn;
   Button info_btn;
   Button back_btn;
   Switch sw;
   ProfileFragment proFrag ;
   InfoFragment infoFrag;
   SubmainFragment mainFrag;
   FragmentManager fmanager;
   FragmentTransaction ftrans;
   private Context context;
   private SharedPreferences preferences;
   private SharedPreferences.Editor editor;

    public SetFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_set,null);
        fmanager = getFragmentManager();
        ftrans = fmanager.beginTransaction();

        context = container.getContext();

        pro_btn = rootView.findViewById(R.id.set_pro);
        proFrag = new ProfileFragment();

        //alarm_btn = rootView.findViewById(R.id.set_alarm);

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
        sw = (Switch) rootView.findViewById(R.id.set_noti);
        sw.toggle(); //Switch의 현재 설정된 상태 반대 옵션으로 변경해줌(OFF->ON)
        String sfName = "Noti";
        preferences = context.getSharedPreferences(sfName, Context.MODE_PRIVATE);
        String s = preferences.getString("Notification", "no value");
        if(s.contains("Not")) {
            sw.toggle();
        }
        //CheckState();
        sw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckState();
            }
        });

        // Inflate the layout for this fragment
        return  rootView;
    }
    public void CheckState(){
        if(sw.isChecked()){
            Toast.makeText(context, "Receive Notification", Toast.LENGTH_LONG).show();
            String sfName = "Noti";
            preferences = context.getSharedPreferences(sfName, Context.MODE_PRIVATE);
            editor = preferences.edit();
            editor.putString("Notification", "Receive");
            editor.commit();
        } else {
            Toast.makeText(context, "Not Receive Notification", Toast.LENGTH_LONG).show();
            String sfName = "Noti";
            preferences = context.getSharedPreferences(sfName, Context.MODE_PRIVATE);
            editor = preferences.edit();
            editor.putString("Notification", "Not");
            editor.commit();
        }
    }
}
