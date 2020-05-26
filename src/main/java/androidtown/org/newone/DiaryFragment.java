package androidtown.org.newone;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Calendar;
import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

public class DiaryFragment extends Fragment {
    DatePicker datePicker;  //  datePicker - 날짜를 선택하는 달력
    TextView viewDatePick;  //  viewDatePick - 선택한 날짜를 보여주는 textView
    EditText edtDiary;   //  edtDiary - 선택한 날짜의 일기를 쓰거나 기존에 저장된 일기가 있다면 보여주고 수정하는 영역
    Button btnSave;   //  btnSave - 선택한 날짜의 일기 저장 및 수정(덮어쓰기) 버튼
    SharedPreferences mPref;
    ImageView imageView;
    String fileName;   //  fileName - 돌고 도는 선택된 날짜의 파일 이름
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_diary,
                container, false);

        // 뷰에 있는 위젯들 리턴 받아두기
        datePicker = (DatePicker) rootView.findViewById(R.id.datePicker);
        viewDatePick = (TextView) rootView.findViewById(R.id.viewDatePick);
        edtDiary = (EditText) rootView.findViewById(R.id.edtDiary);
        btnSave = (Button) rootView.findViewById(R.id.btnSave);
        // 오늘 날짜를 받게해주는 Calender 친구들
        Calendar c = Calendar.getInstance();
        int cYear = c.get(Calendar.YEAR);
        int cMonth = c.get(Calendar.MONTH);
        int cDay = c.get(Calendar.DAY_OF_MONTH);

        // 첫 시작 시에는 오늘 날짜 일기 읽어주기
        checkedDay(cYear, cMonth, cDay,getContext());

        // datePick 기능 만들기
        // datePicker.init(연도,달,일)
        datePicker.init(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // 이미 선택한 날짜에 일기가 있는지 없는지 체크해야할 시간이다
                checkedDay(year, monthOfYear, dayOfMonth,getContext());
            }
        });

        // 저장/수정 버튼 누르면 실행되는 리스너
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDiary(fileName,getContext());
            }
        });
        Button button = rootView.findViewById(R.id.btnGallery);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);
            }
        });

        return rootView;
    }


    // 일기 파일 읽기
    private void checkedDay(int year, int monthOfYear, int dayOfMonth, Context context) {

        // 받은 날짜로 날짜 보여주는
        viewDatePick.setText(year + " - " + monthOfYear + " - " + dayOfMonth);


        fileName = year + "" + monthOfYear + "" + dayOfMonth + ".txt";

        FileInputStream fis = null;
        try {
            fis = context.openFileInput(fileName);

            byte[] fileData = new byte[fis.available()];
            fis.read(fileData);
            fis.close();
            mPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
            String i_image = mPref.getString(fileName, null);
            String str = new String(fileData, "EUC-KR");
            ImageView imageView = getActivity().findViewById(R.id.imageView1);
            Bitmap img2 = StringToBitMap(i_image);

            // 읽어서 토스트 메시지로 보여줌
            Toast.makeText(getContext(), "일기 써둔 날", Toast.LENGTH_SHORT).show();
            edtDiary.setText(str);
            imageView.setImageBitmap(img2);
            btnSave.setText("수정하기");
        } catch (Exception e) {

            Toast.makeText(getContext(), "일기 없는 날", Toast.LENGTH_SHORT).show();
            edtDiary.setText("");
            imageView.setImageResource(0);
            btnSave.setText("새 일기 저장");

            e.printStackTrace();
        }

    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {
                // 선택한 이미지에서 비트맵 생성
                InputStream in = getActivity().getContentResolver().openInputStream(data.getData());
                Bitmap img = BitmapFactory.decodeStream(in);
                in.close();
                imageView = getActivity().findViewById(R.id.imageView1);
                // 이미지 표시
                imageView.setImageBitmap(img);
                //save image
                String s_image = BitMapToString(img);

                SharedPreferences.Editor editor = mPref.edit();
                editor.putString(fileName, s_image);
                editor.commit();


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void saveDiary(String readDay,Context context) {

        FileOutputStream fos = null;

        try {
            fos = context.openFileOutput(readDay, MODE_PRIVATE);
            String content = edtDiary.getText().toString();

            // String.getBytes() = 스트링을 배열형으로 변환?
            fos.write(content.getBytes());
            //fos.flush();
            fos.close();

            // getApplicationContext() = 현재 클래스.this ?
            Toast.makeText(getContext(), "일기 저장됨", Toast.LENGTH_SHORT).show();

        } catch (Exception e) { // Exception - 에러 종류 제일 상위 // FileNotFoundException , IOException
            e.printStackTrace();
            Toast.makeText(getContext(), "오류오류", Toast.LENGTH_SHORT).show();
        }
    }

    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

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
}
