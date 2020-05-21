package androidtown.org.tp_weather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    }

    // 네트워크 작업은 AsyncTask 를 사용해야 한다
    public class WeatherConnection extends AsyncTask<String, String, String>{

        // 백그라운드에서 작업하게 한다
        @Override
        protected String doInBackground(String... params) {

            // Jsoup을 이용한 날씨데이터 Pasing하기.
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
}
