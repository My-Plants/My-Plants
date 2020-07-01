package com.example.myplants;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import jxl.Sheet;
import jxl.Workbook;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    PlantslistFragment plistFragment;
    ShopFragment shopFragment;
    DiaryFragment diaryFragment;
    SetFragment setFragment;
    RecoFragment recoFragment;
    MainFragment mainFragment;
    SubmainFragment subFragment;
    SharedPreferences preferences;
    MyPlantListFragment myPlantListFragment;
    SQLiteDatabase db;
    MySQLiteOpenHelper helper;
    String name_t;
    String size_t;
    String level_t;
    String picture_t;
    String feature_t;
    String watering_t;
    String nickname_t;
    String date_t="";
    String temperature_t;
    String caution_t;

    long calDateDays;
    Integer watering_;


    public ArrayList<String> plantList = new ArrayList<>();
    public static Context context_main;
    public Sheet sheet;

    //날짜 나타내려고하는 포맷 설정
    private SimpleDateFormat mFormat = new SimpleDateFormat("yyyyMMdd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Weather
        TextView textView = (TextView)findViewById(R.id.textView);
        WeatherConnection weatherConnection = new WeatherConnection();
        AsyncTask<String, String, String> result = weatherConnection.execute("","");

        System.out.println("RESULT");

        try{
            String msg = result.get();
            System.out.println(msg);

            textView.setText(msg.toString());

        }catch (Exception e){

        }

        //Enable or disable push notification depending on switch state
        String sfName = "Noti";
        preferences = getSharedPreferences(sfName, MODE_PRIVATE);
        String s = preferences.getString("Notification", "no value");
        if(s.contains("Receive")) {
            createNotification();
        }

        //sqlite
        //helper = new MySQLiteOpenHelper((MainActivity) MainActivity.context_main, "person.db", null, 1);
//        insert("산세베리아", 0, "2020-06-21");
        //       select();

        try {
            InputStream is = getBaseContext().getResources().getAssets().open("myPlantsData.xls");
            Workbook wb = Workbook.getWorkbook(is);

            if(wb != null) {
                sheet = wb.getSheet(0);   // 시트 불러오기
                if(sheet != null) {
                    int colTotal = sheet.getColumns();    // 전체 컬럼
                    int rowIndexStart = 1;                  // row 인덱스 시작
                    int rowTotal = sheet.getColumn(colTotal-1).length;

                    StringBuilder sb;
                    for(int row=rowIndexStart;row<rowTotal;row++) {
                        sb = new StringBuilder();
                        for(int col=0;col<colTotal;col++) {
                            String contents = sheet.getCell(col, row).getContents();
                            sb.append("col"+col+" : "+contents+" , ");
                            if(col == 0){
                                if(row == 1)
                                    continue;
                                plantList.add(contents);
                            }
                        }
                        Log.i("test", sb.toString());
                    }

                }
            }
        } catch (
                IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.i("Plant names / main", plantList.toString());
        context_main = this;

        mainFragment = new MainFragment();
        subFragment = new SubmainFragment();
        plistFragment = new PlantslistFragment();
        diaryFragment = new DiaryFragment();
        recoFragment = new RecoFragment();
        setFragment = new SetFragment();
        shopFragment = new ShopFragment();
        myPlantListFragment = new MyPlantListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id. container , mainFragment).commit();

    }

    //Network operations should use AsyncTask
    public class WeatherConnection extends AsyncTask<String, String, String>{
        //Let them work in the background
        @Override
        protected String doInBackground(String... params) {
            //Pacing Weather Data Using Jsoup
            try{
                String path = "https://weather.naver.com/rgn/cityWetrCity.nhn?cityRgnCd=CT001014";

                Document doc = (Document) Jsoup.connect(path).get();

                Elements elements = doc.select("em");
                System.out.println(elements);
                Element targetElement = elements.get(2);

                String text = targetElement.text();
                System.out.println(text);

                return text;
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }

    //Notification
    private void createNotification() {
        //Called weather textView string
        TextView WeatherTitle = (TextView) findViewById(R.id.textView);
        String t = WeatherTitle.getText().toString();

        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default");
        if(t.contains("흐림")) {
            builder.setSmallIcon(R.drawable.plant2);
            builder.setContentTitle("My plants");
            builder.setContentText("오늘 흐림:( 날씨도 시들시들 영양충전 어때요?");
        }
        else if(t.contains("맑음")) {
            builder.setSmallIcon(R.drawable.plant2);
            builder.setContentTitle("My plants");
            builder.setContentText("오늘 맑음:) 광합성하기 딱 좋은 날씨네요!");
        }
        else if(t.contains("비")) {
            builder.setSmallIcon(R.drawable.plant2);
            builder.setContentTitle("My plants");
            builder.setContentText("오늘 비:( 식물들이 비에 맞고있진않나요..?");
        }
        else {
            builder.setSmallIcon(R.drawable.plant2);
            builder.setContentTitle("My plants");
            builder.setContentText("잠깐! 여러분의 식물은 잘 지내고 있나요?");
        }

        //Read nicknames and dates from sql for watering notifications
        helper = new MySQLiteOpenHelper(this, "person.db", null, 1);
        db = helper.getReadableDatabase();
        Cursor c = db.query("myPlantList", null, null, null, null, null, null);

        NotificationCompat.Builder builder2 = new NotificationCompat.Builder(this, "default");

        long now = System.currentTimeMillis(); //Get current time
        Date date = new Date(now); //Save to current time date, today date
        String time = mFormat.format(date); //Save date as String

        String watering_text = "";
        while (c.moveToNext()){
            nickname_t=c.getString(c.getColumnIndex("nickname"));
            name_t = c.getString(c.getColumnIndex("name"));
            size_t = c.getString(c.getColumnIndex("size"));
            level_t = c.getString(c.getColumnIndex("level"));
            feature_t = c.getString(c.getColumnIndex("feature"));
            watering_t = c.getString(c.getColumnIndex("watering"));
            date_t = c.getString(c.getColumnIndex("date"));
            temperature_t=c.getString(c.getColumnIndex("temperature"));
            caution_t=c.getString(c.getColumnIndex("caution"));
            watering_ = Integer.parseInt(watering_t);
            try{
                SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
                //Convert two dates to Date type via parse()
                Date FirstDate = format.parse(time);
                Log.d("date",date_t);
                Date SecondDate = format.parse(date_t);

                // Calculate two dates converted to Date -> Initialize long type variable with its return value
                // Calculation result : -950400000. Return to long type
                //Date.getTime() : Returns how many seconds have passed since 00:00:00 in 1970 based on the date.
                long calDate = FirstDate.getTime() - SecondDate.getTime();
                Log.d("water","  1234 "+FirstDate.getTime()+"  1234 "+SecondDate.getTime());
                // Distributing 24*60*60*1000 (differences according to each time value) will result in days.
                calDateDays = calDate / ( 24*60*60*1000);
                calDateDays = Math.abs(calDateDays);
                Log.d("water","1234"+calDateDays);
                if(calDateDays > 0)
                    if((calDateDays%watering_) == 0) {
                        builder2.setSmallIcon(R.drawable.plant2);
                        builder2.setContentTitle("My plants : 물 주는 날이에요!");
                        //If expand, see the name of the plant names " + "" +
                        watering_text = watering_text + "\uD83C\uDF31 "+nickname_t + "\n";
                    }
                    else{
                    }
            }catch (ParseException e) {
            }
        }

        // Remove automatically when user clicks tab
        builder.setAutoCancel(true);
        builder2.setAutoCancel(true);

        // Show Notifications
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(new NotificationChannel("default", "기본 채널", NotificationManager.IMPORTANCE_DEFAULT));
        }

        // id value is a unique int value for each alert that must be defined
        notificationManager.notify(1, builder.build());
        if(!watering_text.equals("")) {
            builder2.setStyle(new NotificationCompat.BigTextStyle().bigText(watering_text));
            notificationManager.notify(2, builder2.build());
        }
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item){
        int checkId = item.getItemId();
        switch(checkId){
            case R.id.m_submain:
                getSupportFragmentManager().beginTransaction().replace(R.id.container , subFragment).commit();
                break;
            case R.id.m_pList:
                getSupportFragmentManager().beginTransaction().replace(R.id.container , myPlantListFragment).commit();
                break;
            case R.id.m_reco:
                getSupportFragmentManager().beginTransaction().replace(R.id.container , recoFragment).commit();
                break;
            case R.id.m_diary:
                getSupportFragmentManager().beginTransaction().replace(R.id.container , diaryFragment).commit();
                break;
            case R.id.m_shop:
                getSupportFragmentManager().beginTransaction().replace(R.id.container , shopFragment).commit();
                break;
            case R.id.m_setting:
                getSupportFragmentManager().beginTransaction().replace(R.id.container , setFragment).commit();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
