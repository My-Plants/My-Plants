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


public class SizeRecoFragment extends Fragment {
    Button size1; //small
    Button size2; //medium
    Button size3; //big

    Button back_btn;
    RecoFragment recoFrag;
    FragmentManager fmanager;
    FragmentTransaction ftrans;

    Sheet sheet;
    ArrayList<String> smallList= new ArrayList<>();
    ArrayList<String> midList= new ArrayList<>();
    ArrayList<String> bigList= new ArrayList<>();

    private ListView listView_S1;
    private ListView listView_S2;
    private ListView listView_S3;
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
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_size_reco,null);

        sheet = ((MainActivity) MainActivity.context_main).sheet;
        listView_S1 = rootView.findViewById(R.id.S1ListView);
        listView_S2 = rootView.findViewById(R.id.S2ListView);
        listView_S3 = rootView.findViewById(R.id.S3ListView);
        list1 = new ArrayList<String>();
        list2 = new ArrayList<String>();
        list3 = new ArrayList<String>();
        //when button 'size1' is pressed, show small size plants
        size1 = rootView.findViewById(R.id.small);
        size1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listView_S1.getVisibility() == View.GONE){
                    listView_S1.setVisibility(View.VISIBLE);
                }else{
                    listView_S1.setVisibility(View.GONE);
                }
            }
        });
        //when button 'size2' is pressed, show middle size plants
        size2 = rootView.findViewById(R.id.mid);
        size2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listView_S2.getVisibility() == View.GONE){
                    listView_S2.setVisibility(View.VISIBLE);
                }else{
                    listView_S2.setVisibility(View.GONE);
                }
            }
        });
        //when button 'size3' is pressed, show large size plants
        size3 = rootView.findViewById(R.id.big);
        size3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listView_S3.getVisibility() == View.GONE){
                    listView_S3.setVisibility(View.VISIBLE);
                }else{
                    listView_S3.setVisibility(View.GONE);
                }
            }
        });

        fmanager = getFragmentManager();
        ftrans = fmanager.beginTransaction();
        back_btn = rootView.findViewById(R.id.backBtn);
        recoFrag = new RecoFragment();
        //back button
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ftrans.replace(R.id.container, recoFrag).commit();
            }
        });
        //Read all data from sheet, find each size plants and store them to each list
        if (sheet != null) {
            int colTotal = sheet.getColumns();    // 전체 컬럼
            int rowIndexStart = 1;                  // row 인덱스 시작
            int rowTotal = sheet.getColumn(colTotal - 1).length;
            for(int i = rowIndexStart; i<rowTotal; i++){
                if(i == 1)
                    continue;
                String p_name = sheet.getCell(0,i).getContents();
                String p_level = sheet.getCell(4,i).getContents();

                if(p_level.equals("1")){
                    smallList.add(p_name);
                    Log.i("size1", "size1 : "+p_name);
                }else if(p_level.equals("2")){
                    midList.add(p_name);
                    Log.i("size2", "size2 : "+p_name);
                }else if(p_level.equals("3")){
                    bigList.add(p_name);
                    Log.i("size3", "size3 : "+p_name);
                }
            }
            //add each size list to each list array
            //create adapters and set them to listView
            list1.addAll(smallList);
            adapter1 = new SearchAdapter(list1, MainActivity.context_main);
            listView_S1.setAdapter(adapter1);
            list2.addAll(midList);
            adapter2 = new SearchAdapter(list2, MainActivity.context_main);
            listView_S2.setAdapter(adapter2);
            list3.addAll(bigList);
            adapter3 = new SearchAdapter(list3, MainActivity.context_main);
            listView_S3.setAdapter(adapter3);

            //if each item clicked, move page to the item(plant)'s information page
            pinfoFragment = new PlantsInfoFragment();
            listView_S1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView,
                                        View view, int position, long id) {
                    String selected_item = smallList.get(position);
                    bundle = new Bundle();
                    bundle.putString("selecPlant", selected_item);
                    pinfoFragment.setArguments(bundle);

                    ftrans.replace(R.id.container, pinfoFragment).commit();
                }
            });
            listView_S2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView,
                                        View view, int position, long id) {

                    String selected_item = midList.get(position);
                    bundle = new Bundle();
                    bundle.putString("selecPlant", selected_item);
                    pinfoFragment.setArguments(bundle);

                    ftrans.replace(R.id.container, pinfoFragment).commit();
                }
            });
            listView_S3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView,
                                        View view, int position, long id) {

                    String selected_item = bigList.get(position);
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
