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

public class LevelRecoFragment extends Fragment {
    Button level1;
    Button level2;
    Button level3;

    Button back_btn;
    RecoFragment recoFrag;
    FragmentManager fmanager;
    FragmentTransaction ftrans;

    Sheet sheet;
    ArrayList<String> level1List= new ArrayList<>();
    ArrayList<String> level2List= new ArrayList<>();
    ArrayList<String> level3List= new ArrayList<>();

    private ListView listView_L1;
    private ListView listView_L2;
    private ListView listView_L3;
    private SearchAdapter adapter1;
    private SearchAdapter adapter2;
    private SearchAdapter adapter3;

    private List<String> list1;
    private List<String> list2;
    private List<String> list3;

    private Bundle bundle;
    PlantsInfoFragment pinfoFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_level_reco, null);
        sheet = ((MainActivity) MainActivity.context_main).sheet;
        listView_L1 = rootView.findViewById(R.id.L1ListView);
        listView_L2 = rootView.findViewById(R.id.L2ListView);
        listView_L3 = rootView.findViewById(R.id.L3ListView);
        list1 = new ArrayList<String>();
        list2 = new ArrayList<String>();
        list3 = new ArrayList<String>();
        //when button 'level1' is pressed, show easy level plants
        level1 = rootView.findViewById(R.id.easy);
        level1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listView_L1.getVisibility() == View.GONE){
                    listView_L1.setVisibility(View.VISIBLE);
                }else{
                    listView_L1.setVisibility(View.GONE);
                }
            }
        });
        //when button 'level2' is pressed, show normal level plants
        level2 = rootView.findViewById(R.id.normal);
        level2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listView_L2.getVisibility() == View.GONE){
                    listView_L2.setVisibility(View.VISIBLE);
                }else{
                    listView_L2.setVisibility(View.GONE);
                }
            }
        });
        //when button 'level3' is pressed, show hard level plants
        level3 = rootView.findViewById(R.id.hard);
        level3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listView_L3.getVisibility() == View.GONE){
                    listView_L3.setVisibility(View.VISIBLE);
                }else{
                    listView_L3.setVisibility(View.GONE);
                }
            }
        });
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
        //Read all data from sheet, find each level plants and store them to each list
        if (sheet != null) {
            int colTotal = sheet.getColumns();    // 전체 컬럼
            int rowIndexStart = 1;                  // row 인덱스 시작
            int rowTotal = sheet.getColumn(colTotal - 1).length;
            for(int i = rowIndexStart; i<rowTotal; i++){
                if(i == 1)
                    continue;
                String p_name = sheet.getCell(0,i).getContents();
                String p_level = sheet.getCell(8,i).getContents();
                if(p_level.equals("1")){
                    level1List.add(p_name);
                    Log.i("levle1", "Level1 : "+p_name);
                }else if(p_level.equals("2")){
                    level2List.add(p_name);
                    Log.i("levle2", "Level2 : "+p_name);
                }else if(p_level.equals("3")){
                    level3List.add(p_name);
                    Log.i("levle3", "Level3 : "+p_name);
                }
            }
            //check
            //Log.i("levle1", level1List.toString());

            //add each level list to each list array
            //create adapters and set them to listView
            list1.addAll(level1List);
            adapter1 = new SearchAdapter(list1, MainActivity.context_main);
            listView_L1.setAdapter(adapter1);
            list2.addAll(level2List);
            adapter2 = new SearchAdapter(list2, MainActivity.context_main);
            listView_L2.setAdapter(adapter2);
            list3.addAll(level3List);
            adapter3 = new SearchAdapter(list3, MainActivity.context_main);
            listView_L3.setAdapter(adapter3);

            pinfoFragment = new PlantsInfoFragment();
            //if each item clicked, move page to the item(plant)'s information page
            listView_L1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView,
                                        View view, int position, long id) {

                    String selected_item = level1List.get(position);
                    bundle = new Bundle();
                    bundle.putString("selecPlant", selected_item);
                    pinfoFragment.setArguments(bundle);

                    ftrans.replace(R.id.container, pinfoFragment).commit();
                }
            });
            listView_L2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView,
                                        View view, int position, long id) {

                    String selected_item = level2List.get(position);
                    bundle = new Bundle();
                    bundle.putString("selecPlant", selected_item);
                    pinfoFragment.setArguments(bundle);

                    ftrans.replace(R.id.container, pinfoFragment).commit();
                }
            });
            listView_L3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView,
                                        View view, int position, long id) {

                    String selected_item = level3List.get(position);
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

