package com.example.myplants;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
    ArrayList<String> cleanList = new ArrayList<>();
    private ListView listView_C;
    private SearchAdapter adapter;
    private List<String> list;

    private Bundle bundle;
    PlantsInfoFragment pinfoFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_clean_reco, null);
        //get data sheet from main activity
        sheet = ((MainActivity) MainActivity.context_main).sheet;
        listView_C = rootView.findViewById(R.id.CListView);
        list = new ArrayList<String>();

        fmanager = getFragmentManager();
        ftrans = fmanager.beginTransaction();
        back_btn = rootView.findViewById(R.id.backBtn);
        recoFrag = new RecoFragment();
        //Back button
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ftrans.replace(R.id.container, recoFrag).commit();
            }
        });

        //Read all data from sheet, find '공기 정화' plants and store them to cleanList
        if (sheet != null) {
            int colTotal = sheet.getColumns();    // 전체 컬럼
            int rowIndexStart = 1;                // row 인덱스 시작
            int rowTotal = sheet.getColumn(colTotal - 1).length;
            for (int i = rowIndexStart; i < rowTotal; i++) {
                if (i == 1)
                    continue;
                String p_name = sheet.getCell(0, i).getContents();
                String p_state = sheet.getCell(6, i).getContents();

                if (p_state.substring(0).equals("공기정화.")) {
                    cleanList.add(p_name);
                }
            }
            //add cleanList to list array
            //create an adapter and set adapter to listView
            list.addAll(cleanList);
            adapter = new SearchAdapter(list, MainActivity.context_main);
            listView_C.setAdapter(adapter);

            pinfoFragment = new PlantsInfoFragment();
            //if each item clicked, move page to the item(plant)'s information page
            listView_C.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView,
                                        View view, int position, long id) {

                    String selected_item = list.get(position);
                    bundle = new Bundle();
                    bundle.putString("selecPlant", selected_item);
                    pinfoFragment.setArguments(bundle);

                    ftrans.replace(R.id.container, pinfoFragment).commit();
                }
            });
        }
        return rootView;
    }
}

