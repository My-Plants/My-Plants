package com.example.myplants;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import jxl.Sheet;


public class CleanRecoFragment extends Fragment {
    Button back_btn;
    RecoFragment recoFrag;
    FragmentManager fmanager;
    FragmentTransaction ftrans;

    Sheet sheet;
    ArrayList<String> cleanList= new ArrayList<>();
    private ListView listView_C;
    private SearchAdapter adapter;
    private List<String> list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_clean_reco,null);
        sheet = ((MainActivity) MainActivity.context_main).sheet;
        listView_C = rootView.findViewById(R.id.CListView);
        list = new ArrayList<String>();


        fmanager = getFragmentManager();
        ftrans = fmanager.beginTransaction();
        back_btn = rootView.findViewById(R.id.backBtn);
        recoFrag = new RecoFragment();
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ftrans.replace(R.id.container, recoFrag).commit();
            }
        });

        if (sheet != null) {
            int colTotal = sheet.getColumns();    // 전체 컬럼
            int rowIndexStart = 1;                  // row 인덱스 시작
            int rowTotal = sheet.getColumn(colTotal - 1).length;
            for(int i = rowIndexStart; i<rowTotal; i++) {
                if (i == 1)
                    continue;
                String p_name = sheet.getCell(0, i).getContents();
                String p_level = sheet.getCell(6, i).getContents();
                /*if(p_level.equals("1")){
                    level1List.add(p_name);
                    Log.i("levle1", "Level1 : "+p_name);
                }*/
            }
            }
            //Log.i("levle1", level1List.toString());
        return rootView;
    }
}
