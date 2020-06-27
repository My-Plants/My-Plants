package com.example.myplants;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

public class MyPlantListFragment extends Fragment {
    PlantslistFragment plistFragment;
    MyPlantsInfoFragment myPlantInfoFrag;
    private ArrayAdapter<String> arrayAdapter;
    private Bundle bundle;

    SQLiteDatabase db;
    MySQLiteOpenHelper helper;

    FragmentManager fmanager;
    FragmentTransaction ftrans;
    jxl.Sheet sheet;

    ListView listView;
    ArrayList<String> mItems = new ArrayList<String>();

    PlantsInfoFragment pinfoFragment;
    SubmainFragment main;
    private Button back;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_myplants,
                container, false); //create a view for the fragment


        main = new SubmainFragment();
        back = rootView.findViewById(R.id.backBtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ftrans.replace(R.id.container, main).commit();
            }
        });

        helper = new MySQLiteOpenHelper((MainActivity) MainActivity.context_main, "person.db", null, 1);
        db = helper.getReadableDatabase();
        Cursor c = db.query("myPlantList", null, null, null, null, null, null);
        int check = 0;
        while (c.moveToNext()) {
            String nickCheck = c.getString(c.getColumnIndex("nickname"));
            mItems.add(nickCheck);
        }
        listView = (ListView)rootView.findViewById(R.id.myPlantListView);
        arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, mItems);

        listView.setAdapter(arrayAdapter);

        ImageButton plus= (ImageButton) rootView.findViewById(R.id.plus);
        fmanager = getFragmentManager();
        ftrans = fmanager.beginTransaction();;

        plistFragment = new PlantslistFragment();
        myPlantInfoFrag = new MyPlantsInfoFragment();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int position, long id) {

                String selected_item = mItems.get(position);
                bundle = new Bundle();
                bundle.putString("nickname", selected_item);
                myPlantInfoFrag.setArguments(bundle);

                ftrans.replace(R.id.container, myPlantInfoFrag).commit();
            }
        });


        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ftrans.replace(R.id.container,plistFragment).commit();
            }
        });

        return rootView;
    }

}