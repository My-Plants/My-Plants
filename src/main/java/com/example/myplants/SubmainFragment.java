package com.example.myplants;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.preference.PreferenceManager;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


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

    SharedPreferences mPref;
    ImageView pro_img;
    String pro_name;  // user's name
    String pro_state; // user's status message
    TextView txtName;
    TextView txtState;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_submain,null);
        fmanager = getFragmentManager();
        ftrans = fmanager.beginTransaction();

        mPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());

        txtName = rootView.findViewById(R.id.show_name);
        txtState = rootView.findViewById(R.id.show_state);
        pro_name = mPref.getString("prof_name", null);
        pro_state = mPref.getString("prof_state", null);
        if(txtName != null)
            txtName.setText("Hello!  " + pro_name + " :)!");
        txtState.setText(pro_state);
        String i_image = mPref.getString("prof_img", null);
        if (i_image != null) {
            pro_img = rootView.findViewById(R.id.pro_img);
            Bitmap img = StringToBitMap(i_image);
            pro_img.setImageBitmap(img);
        }





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

    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
}
