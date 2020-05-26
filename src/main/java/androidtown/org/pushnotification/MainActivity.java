package androidtown.org.pushnotification;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //push message
        FirebaseMessaging.getInstance().subscribeToTopic("plant");
        FirebaseInstanceId.getInstance().getToken();
    }
}
