package androidtown.org.myplants;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import jxl.Sheet;
import jxl.Workbook;

public class MainActivity extends AppCompatActivity {
    PlantslistFragment plistFragment;
    MyPlantListFragment myplistFragment;
    ShopFragment shopFragment;
    DiaryFragment diaryFragment;
    SetFragment setFragment;
    RecoFragment recoFragment;
    MainFragment mainFragment;
    SubmainFragment subFragment;
    Sheet sheet;
    public ArrayList<String> plantList = new ArrayList<>();
    public File directory;
    public static Context context_main;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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


       directory = getExternalFilesDir(null);
        Log.i("test", directory.toString());

        context_main = this;

        mainFragment = new MainFragment();
        subFragment = new SubmainFragment();
        plistFragment = new PlantslistFragment();
        myplistFragment=new MyPlantListFragment();
        diaryFragment = new DiaryFragment();
        recoFragment = new RecoFragment();
        setFragment = new SetFragment();
        shopFragment = new ShopFragment();
        getSupportFragmentManager().beginTransaction().add(R.id. container , mainFragment).commit();

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
                getSupportFragmentManager().beginTransaction().replace(R.id.container , myplistFragment).commit();
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
