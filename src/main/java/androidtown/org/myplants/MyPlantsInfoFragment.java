
package androidtown.org.myplants;


import android.content.ContentValues;
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
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;

import java.io.BufferedInputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class MyPlantsInfoFragment extends Fragment {
    SaveExcel exl;
    MyPlantListFragment myPlantListFragment;

    FragmentManager fmanager;
    FragmentTransaction ftrans;
    jxl.Sheet sheet;

    SQLiteDatabase db;
    MySQLiteOpenHelper helper;

    String name_t;
    String picture_t;
    int size_t;
    int level_t;
    String feature_t;
    String watering_t;
    String nickname_t;
    String date_t;

    TextView name;
    TextView size;
    TextView level;
    TextView feature;
    TextView watering;
    TextView nickname;
    TextView date;
    ImageView photo;

    //돌아가기 버튼
    Button plist_btn;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_myplantsinfo,
                container, false); //create a view for the fragment
        EditText eNickname=(EditText) rootView.findViewById(R.id.nickname);
        name=(TextView) rootView.findViewById(R.id.myplant_name);
        size=(TextView) rootView.findViewById(R.id.myplant_size);
        level=(TextView) rootView.findViewById(R.id.myplant_level);
        feature=(TextView) rootView.findViewById(R.id.myplant_feature);
        watering=(TextView) rootView.findViewById(R.id.myplant_watering);
        nickname=(TextView) rootView.findViewById(R.id.myplant_nickname);
        date=(TextView) rootView.findViewById(R.id.myplant_date);
        photo=(ImageView) rootView.findViewById(R.id.myplant_photo);


        fmanager = getFragmentManager();
        ftrans = fmanager.beginTransaction();

        myPlantListFragment = new MyPlantListFragment();

        helper = new MySQLiteOpenHelper((MainActivity) MainActivity.context_main, "person.db", null, 1);

        Bundle bundle = this.getArguments();
        nickname_t = bundle.getString("nickname");


        db = helper.getReadableDatabase();
        Cursor c = db.query("myPlantList", null, null, null, null, null, null);

        while (c.moveToNext()) {
            String nickCheck = c.getString(c.getColumnIndex("nickname"));
            if(nickCheck.equalsIgnoreCase(nickname_t)) {
                name_t = c.getString(c.getColumnIndex("name"));
                size_t = c.getInt(c.getColumnIndex("size"));
                level_t = c.getInt(c.getColumnIndex("level"));
                feature_t = c.getString(c.getColumnIndex("feature"));
                watering_t = c.getString(c.getColumnIndex("watering"));
                picture_t = c.getString(c.getColumnIndex("picture"));
                //date_t = c.getString(c.getColumnIndex("date"));
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

        name.setText(name_t);
        size.setText(Integer.toString(size_t));
        level.setText(Integer.toString(level_t));
        feature.setText(feature_t);
        watering.setText(watering_t);
        nickname.setText(nickname_t);
        //date.setText(date_t);


        //돌아가기 버튼
        plist_btn = rootView.findViewById(R.id.myplantinfoback);
        plist_btn.setOnClickListener(v ->
                ftrans.replace(R.id.container, myPlantListFragment).commit()
        );

        return rootView;
    }


    public void getInfo() {
        db = helper.getReadableDatabase();
        Cursor c = db.query("myPlantList", null, null, null, null, null, null);

        while (c.moveToNext()) {
            // get data from db
            String name = c.getString(c.getColumnIndex("name"));
            int size = c.getInt(c.getColumnIndex("size"));
            int level = c.getInt(c.getColumnIndex("level"));
            String feature = c.getString(c.getColumnIndex("feature"));
            String watering = c.getString(c.getColumnIndex("watering"));
            String nickname = c.getString(c.getColumnIndex("nickname"));
            String picture = c.getString(c.getColumnIndex("picture"));

            Log.i("db1", name + " " + size + " " + level +" " + feature + " "
                    + watering + " " + nickname + " " + picture);
            // move to next data
        }
    }

}
