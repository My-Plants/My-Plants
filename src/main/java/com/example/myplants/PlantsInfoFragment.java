package com.example.myplants;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class PlantsInfoFragment extends Fragment {
    PlantslistFragment plistFragment;
    ArrayList<String> plantList;
    private List<String> list;          // 데이터를 넣은 리스트변수
    private ListView listView;          // 검색을 보여줄 리스트변수
    private EditText editSearch;        // 검색어를 입력할 Input 창
    private SearchAdapter adapter;      // 리스트뷰에 연결할 아답터
    private Bundle bundle;

    FragmentManager fmanager;
    FragmentTransaction ftrans;

    Button plist_btn;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_plants_info,
                container, false);
        fmanager = getFragmentManager();
        ftrans = fmanager.beginTransaction();

        plist_btn = rootView.findViewById(R.id.plantinfoback);
        plistFragment = new PlantslistFragment();
        plist_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ftrans.replace(R.id.container, plistFragment).commit();
            }
        });
        return rootView;
    }
}
