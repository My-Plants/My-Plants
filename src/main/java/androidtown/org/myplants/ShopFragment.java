package androidtown.org.myplants;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class ShopFragment extends Fragment {
    String pot_link = "https://search.shopping.naver.com/search/all.nhn?where=all&frm=NVSCTAB&query=%ED%99%94%EB%B6%84";
    String flower_link = "https://search.shopping.naver.com/search/all.nhn?query=%EA%BD%83%ED%99%94%EB%B6%84";
    String soil_link = "https://search.shopping.naver.com/search/all.nhn?query=%ED%9D%99&cat_id=&frm=NVSHATC";
    String nut_link = "https://search.shopping.naver.com/search/all.nhn?query=%EC%8B%9D%EB%AC%BC+%EC%98%81%EC%96%91%EC%A0%9C&cat_id=&frm=NVSHATC";
    String spr_link = "https://search.shopping.naver.com/search/all.nhn?query=%EB%AC%BC%EB%BF%8C%EB%A6%AC%EA%B0%9C&cat_id=&frm=NVSHATC";
    String acc_link = "https://search.shopping.naver.com/search/all.nhn?query=%EC%8B%9D%EB%AC%BC+%EC%9E%A5%EC%8B%9D&cat_id=&frm=NVSHATC";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_shop,
                container, false);

        Button pot_btn = (Button) rootView.findViewById(R.id.shop_pot);
        Button flower_btn = (Button) rootView.findViewById(R.id.shop_flower);
        Button soil_btn = (Button) rootView.findViewById(R.id.shop_soil);
        Button nut_btn = (Button) rootView.findViewById(R.id.shop_nut);
        Button spr_btn = (Button) rootView.findViewById(R.id.shop_spr);
        Button acc_btn = (Button) rootView.findViewById(R.id.shop_acc);

        pot_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create new Intent to access the web page(url_add)
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(pot_link));
                //Start the activity which access the web page
                startActivity(intent);
            }
        });
        soil_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create new Intent to access the web page(url_add)
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(soil_link));
                //Start the activity which access the web page
                startActivity(intent);
            }
        });
        flower_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create new Intent to access the web page(url_add)
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(flower_link));
                //Start the activity which access the web page
                startActivity(intent);
            }
        });
        spr_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create new Intent to access the web page(url_add)
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(spr_link));
                //Start the activity which access the web page
                startActivity(intent);
            }
        });
        nut_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create new Intent to access the web page(url_add)
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(nut_link));
                //Start the activity which access the web page
                startActivity(intent);
            }
        });
        acc_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create new Intent to access the web page(url_add)
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(acc_link));
                //Start the activity which access the web page
                startActivity(intent);
            }
        });
        // Inflate the layout for this fragment
        return rootView;
    }
}
