package androidtown.org.newone;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;


public class MainActivity extends AppCompatActivity {

    DiaryFragment diaryFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        diaryFragment = new DiaryFragment();

        getSupportFragmentManager().beginTransaction().add(R.id. container , diaryFragment).commit();
    }

}