package com.example.myplants;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
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
import java.util.ArrayList;
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
    Switch sw;

    public ArrayList<String> plantList = new ArrayList<>();
    public static Context context_main;
    public Sheet sheet;

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

        //createNotification();
        //Notification 앱 실행시 푸쉬알림
        String sfName = "Noti";
        preferences = getSharedPreferences(sfName, MODE_PRIVATE);
            String s = preferences.getString("Notification", "no value");
            if(s.contains("Receive")) {
                createNotification();
            }

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
        //weather TextView string 으로 불러옴
        TextView WeatherTitle = (TextView) findViewById(R.id.textView);
        String t = WeatherTitle.getText().toString();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default");
        if(t.contains("흐림")) {
            builder.setSmallIcon(R.drawable.plant2);
            builder.setContentTitle("Case 1");
            builder.setContentText("흐림!!");
        }
        else if(t.contains("맑음")) {
            builder.setSmallIcon(R.drawable.plant2);
            builder.setContentTitle("Case 2");
            builder.setContentText("맑음!!");
        }
        else {
            builder.setSmallIcon(R.drawable.plant2);
            builder.setContentTitle("Case 3");
            builder.setContentText("무난한 날씨!!");
        }

        // 사용자가 탭을 클릭하면 자동 제거
        builder.setAutoCancel(true);

        // 알림 표시
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(new NotificationChannel("default", "기본 채널", NotificationManager.IMPORTANCE_DEFAULT));
        }

        // id값은 정의해야하는 각 알림의 고유한 int값
        notificationManager.notify(1, builder.build());
    }
    /*private void removeNotification() {
        // Notification 제거
        NotificationManagerCompat.from(this).cancel(1);
    }*/

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
                getSupportFragmentManager().beginTransaction().replace(R.id.container , plistFragment).commit();
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
