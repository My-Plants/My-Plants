
package com.example.myplants;


import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;

import java.io.BufferedInputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;

public class PlantsInfoFragment extends Fragment {
    PlantslistFragment plistFragment;

    MyPlantListFragment myPlantListFragment;

    FragmentManager fmanager;
    FragmentTransaction ftrans;
    jxl.Sheet sheet;

    SQLiteDatabase db;
    MySQLiteOpenHelper helper;

    String name_t;
    String picture_t;
    String size_t;
    String level_t;
    String date_t="";
    String feature_t;
    String watering_t;
    String caution_t="";
    String temperature_t="";

    TextView name;
    TextView size;
    TextView level;
    TextView feature;
    TextView watering;
    ImageView photo;
    ImageButton plus;

    EditText eNickname;
    ArrayList<Item> mItems = new ArrayList<Item>();

    //돌아가기 버튼
    Button plist_btn;

    SharedPreferences mPref;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_plantsinfo,
                container, false); //create a view for the fragment
        eNickname=(EditText) rootView.findViewById(R.id.nickname);
        name=(TextView) rootView.findViewById(R.id.plant_name);
        size=(TextView) rootView.findViewById(R.id.plant_size);
        level=(TextView) rootView.findViewById(R.id.plant_level);
        feature=(TextView) rootView.findViewById(R.id.plant_feature);
        watering=(TextView) rootView.findViewById(R.id.plant_watering);
        photo=(ImageView) rootView.findViewById(R.id.plant_photo);
        plus= (ImageButton) rootView.findViewById(R.id.plus);

        fmanager = getFragmentManager();
        ftrans = fmanager.beginTransaction();

        plistFragment = new PlantslistFragment();
        myPlantListFragment = new MyPlantListFragment();

        sheet = ((MainActivity) MainActivity.context_main).sheet;

        if(sheet != null) {
            int colTotal = sheet.getColumns();    // 전체 컬럼
            int rowIndexStart = 1;                  // row 인덱스 시작
            int rowTotal = sheet.getColumn(colTotal-1).length;

            Bundle bundle = this.getArguments();
            String plantName = bundle.getString("selecPlant");
            // Log.i("test", plantName);

            StringBuilder sb;
            for(int row=rowIndexStart;row<rowTotal;row++) {
                sb = new StringBuilder();
                String var = sheet.getCell(0, row).getContents();
                if(var.equals(plantName)){
                    name_t = sheet.getCell(0, row).getContents();
                    picture_t = sheet.getCell(1, row).getContents();
                    watering_t = sheet.getCell(2, row).getContents();
                    temperature_t=sheet.getCell(3,row).getContents();
                    size_t = sheet.getCell(4, row).getContents();
                    feature_t = sheet.getCell(6, row).getContents();
                    caution_t = sheet.getCell(7, row).getContents();
                    level_t = sheet.getCell(8, row).getContents();
                }
            }
        }

        String url1='"'+picture_t+'"';
        Glide.with(this).load(picture_t).into(photo);

        try {
            URL url = new URL(url1);
            URLConnection conn = url.openConnection();
            conn.connect();
            BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
            Bitmap bm = BitmapFactory.decodeStream(bis);
            bis.close();
            photo.setImageBitmap(bm);
        } catch (Exception e) {
        }

        Calendar c = Calendar.getInstance();
        int cYear = c.get(Calendar.YEAR);
        int cMonth = c.get(Calendar.MONTH)+1; //since it starts with 0,  add 1
        int cDay = c.get(Calendar.DAY_OF_MONTH);

        date_t=""+cYear + String.format("%02d",cMonth) + String.format("%02d",cDay);

        name.setText(name_t);
        if(size_t.equals("1")){
            size_t = "Small";
        }else if(size_t.equals("2")){
            size_t = "Medium";
        }else if(size_t.equals("3")){
            size_t = "Large";
        }
        size.setText(size_t);
        if(level_t.equals("1")){
            level_t = "Easy";
        }else if(level_t.equals("2")){
            level_t = "Normal";
        }else if(level_t.equals("3")){
            level_t = "Hard";
        }
        level.setText(level_t);
        feature.setText(feature_t);
        watering.setText(watering_t + "일에 한번");


        helper = new MySQLiteOpenHelper((MainActivity) MainActivity.context_main, "person.db", null, 1);

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nickname = eNickname.getText().toString();
                insert(name_t, size_t, level_t, feature_t, watering_t, nickname, date_t,caution_t,temperature_t);
                select();
                ftrans.replace(R.id.container, myPlantListFragment).commit();
            }
        });

        //돌아가기 버튼
        plist_btn = rootView.findViewById(R.id.backBtn);
        plist_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ftrans.replace(R.id.container, plistFragment).commit();
            }
        });

        return rootView;
    }

    // insert function
    public void insert(String name, String size, String level, String feature,
                       String watering, String nickname, String date,String caution, String temperature) {
        db = helper.getWritableDatabase();
        // create value set
        Cursor c = db.query("myPlantList", null, null, null, null, null, null);
        int check;
        check = 0;
        while (c.moveToNext()) {
            String nickCheck = c.getString(c.getColumnIndex("nickname"));
            if(nickCheck.equalsIgnoreCase(nickname)){
                check = 1;
            }
        }
        if(check == 0) {
            ContentValues values = new ContentValues();
            // put data into value set
            values.put("name", name);
            values.put("size", size);
            values.put("level", level);
            values.put("feature", feature);
            values.put("watering", watering);
            values.put("nickname", nickname);
            values.put("date",date);
            values.put("caution",caution);
            values.put("temperature",temperature);


            // insert value into db
            db.insert("myPlantList", null, values);
            Toast.makeText(getActivity(),"저장되었습니다",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getActivity(),"이미 등록된 닉네임입니다",Toast.LENGTH_SHORT).show();
        }
    }

    // delete function
    public void delete (String nickname) {
        db = helper.getWritableDatabase();
        // search specific data with name and delete data
        db.delete("myPlantList", "nickname=?", new String[]{nickname});
        // print
        Log.i("db1", nickname + "정상적으로 삭제 되었습니다.");
    }

    public void select() {
        db = helper.getReadableDatabase();
        Cursor c = db.query("myPlantList", null, null, null, null, null, null);

        while (c.moveToNext()) {
            // get data from db
            String name = c.getString(c.getColumnIndex("name"));
            String size = c.getString(c.getColumnIndex("size"));
            String level = c.getString(c.getColumnIndex("level"));
            String feature = c.getString(c.getColumnIndex("feature"));
            String watering = c.getString(c.getColumnIndex("watering"));
            String nickname = c.getString(c.getColumnIndex("nickname"));
            String date=c.getString(c.getColumnIndex("date"));
            String temperature=c.getString(c.getColumnIndex("temperature"));
            String caution=c.getString(c.getColumnIndex("caution"));

            Log.i("db1", name + " " + size + " " + level +" " + feature + " "
                    + watering + " " + nickname + " "+date+" "+caution+" "+temperature);
            // move to next data
        }
    }
}