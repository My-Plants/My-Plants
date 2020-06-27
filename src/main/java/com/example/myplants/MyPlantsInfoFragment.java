package com.example.myplants;

import static android.app.Activity.RESULT_OK;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class MyPlantsInfoFragment extends Fragment {

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
    String feature_t;
    String watering_t;
    String nickname_t;
    String date_t;
    String temperature_t;
    String caution_t;
    ImageButton button;
    SharedPreferences mPref;

    TextView name;
    TextView size;
    TextView level;
    TextView feature;
    TextView watering;
    TextView nickname;
    TextView date;
    TextView temperature;
    TextView caution;
    ImageView photo;
    Button delete_btn;
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
        temperature=rootView.findViewById(R.id.myplant_temperature);
        caution=rootView.findViewById(R.id.myplant_caution);
        photo=(ImageView) rootView.findViewById(R.id.my_photo);
        button=(ImageButton) rootView.findViewById(R.id.btnGallery2); //Find the

        mPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());

        //When clicking the gallery icon, open gallery, select photo and save the image in imageview
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);
            }
        });


        fmanager = getFragmentManager();
        ftrans = fmanager.beginTransaction();

        myPlantListFragment = new MyPlantListFragment();
        //Get bundle with the key nickname and store in nickname_t
        Bundle bundle = this.getArguments();
        nickname_t = bundle.getString("nickname");
        //Bring sql helper named person.db
        helper = new MySQLiteOpenHelper((MainActivity) MainActivity.context_main, "person.db", null, 1);



        db = helper.getReadableDatabase();
        //Query the database with cursor
        Cursor c = db.query("myPlantList", null, null, null, null, null, null);

        while (c.moveToNext()) { //Move cursor
            String nickCheck = c.getString(c.getColumnIndex("nickname")); //Get value of column "nickname"
            if(nickCheck.equalsIgnoreCase(nickname_t)) { //If nickCheck equals nickname_t, bring other informations and store them
                nickname_t=c.getString(c.getColumnIndex("nickname"));
                name_t = c.getString(c.getColumnIndex("name"));
                size_t = c.getString(c.getColumnIndex("size"));
                level_t = c.getString(c.getColumnIndex("level"));
                feature_t = c.getString(c.getColumnIndex("feature"));
                watering_t = c.getString(c.getColumnIndex("watering"));
                date_t = c.getString(c.getColumnIndex("date"));
                temperature_t=c.getString(c.getColumnIndex("temperature"));
                caution_t=c.getString(c.getColumnIndex("caution"));
            }
        }
        //Convert integer value into string value
        if(size_t.equals("1")){
            size_t="소형 식물";
        }
        else if (size_t.equals("2")){
            size_t="중형 식물";
        }
        else if (size_t.equals("3")){
            size_t="대형 식물";
        }
        if(level_t.equals("1")){
            level_t="쉬움";
        }
        else if(level_t.equals("2")){
            level_t="중간";
        }
        else if(level_t.equals("3")){
            level_t="어려움";
        }
        watering_t = watering_t + "일에 한 번";


        temperature_t= temperature_t.replace("^", "°C ~ ");
        temperature_t=temperature_t+"°C";
        //Set the Textview with informations
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
        watering.setText(watering_t);
        nickname.setText(nickname_t);
        caution.setText(caution_t);
        temperature.setText(temperature_t);
        date.setText(date_t);
        //Set the photo image with nickname_t
        String i_image = mPref.getString(nickname_t, null);
        if (i_image != null) {
            Bitmap img2 = StringToBitMap(i_image);
            photo.setImageBitmap(img2);
        }

        //When the 'button' pressed, update plant's picture by selecting new picture from gallery
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);
            }
        });
        delete_btn = rootView.findViewById(R.id.deleteBtn); //If delete button pressed
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(nickname_t); //call the delete method with parameter nickname_t
                ftrans.replace(R.id.container, myPlantListFragment).commit(); //replace the fragment
            }
        });


        //back button
        plist_btn = rootView.findViewById(R.id.backBtn);
        plist_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ftrans.replace(R.id.container, myPlantListFragment).commit();
            }
        });
        return rootView;
    }

    //Function which change Bitmap type variable to String type (return String)
    public String BitMapToString(Bitmap bitmap) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);

        return temp;

    }

    //Function which change String type variable to Bitmap type (return Bitmap)
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
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //plant image set
        if (resultCode == RESULT_OK) {
            try {
                // Create bitmap of selected picture
                InputStream in = getActivity().getContentResolver().openInputStream(data.getData());
                Bitmap img = BitmapFactory.decodeStream(in);
                in.close();
                // show the image by imageView
                photo.setImageBitmap(img);

                //save image by SharedPreferences(translate the bitmap variable to string value, and store it)
                String s_image = BitMapToString(img);
                SharedPreferences.Editor editor = mPref.edit();
                editor.putString(nickname_t, s_image);
                editor.commit();

            } catch (Exception e) {
                e.printStackTrace();
            }
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
}
