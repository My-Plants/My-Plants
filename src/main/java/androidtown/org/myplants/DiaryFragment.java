package androidtown.org.myplants;

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
import android.widget.ImageButton;
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
    DatePicker datePicker;  //  Select the date
    TextView viewDatePick;  //  textView showing the date
    EditText edtDiary;   //  Write a diary when blank or rewrite
    Button btnSave;   //  save the button
    SharedPreferences mPref;
    ImageView imageView;
    String fileName;   //  Make a filename with selected date
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_diary,
                container, false); //create a view for the fragment

        datePicker = (DatePicker) rootView.findViewById(R.id.datePicker);
        viewDatePick = (TextView) rootView.findViewById(R.id.viewDatePick);
        edtDiary = (EditText) rootView.findViewById(R.id.edtDiary);
        btnSave = (Button) rootView.findViewById(R.id.btnSave);
        // Create calender to get today's date
        Calendar c = Calendar.getInstance();
        int cYear = c.get(Calendar.YEAR);
        int cMonth = c.get(Calendar.MONTH)+1; //since it starts with 0,  add 1
        int cDay = c.get(Calendar.DAY_OF_MONTH);

        // When first started, read the date
        checkedDay(cYear, cMonth, cDay,getContext());

        // datePick Function implementation
        // datePicker.init(yr, month, day)
        datePicker.init(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // Check whether there's note in selected date
                checkedDay(year, monthOfYear+1, dayOfMonth,getContext());
            }
        });

        // When clicking the save button, save the diary with its file name
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDiary(fileName,getContext());
            }
        });
        //When clicking the gallery icon, open gallery, select photo and save the image in imageview
        ImageButton button = rootView.findViewById(R.id.btnGallery);
        button.setOnClickListener(new View.OnClickListener() { //When clicking the gallery button
            @Override
            public void onClick(View view) { //Set the intent type as image and start intent
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);
            }
        });
        return rootView;
    }


    //Read the diary file
    private void checkedDay(int year, int monthOfYear, int dayOfMonth, Context context) {
        ImageView imageView = getActivity().findViewById(R.id.imageView1);
        // Show by the selected date
        viewDatePick.setText(year + " - " + monthOfYear + " - " + dayOfMonth);

        //Save the file name with the selected date
        fileName = year + "" + monthOfYear + "" + dayOfMonth + ".txt";

        FileInputStream fis = null;
        try {
            fis = context.openFileInput(fileName);
            byte[] fileData = new byte[fis.available()];
            fis.read(fileData); //Read data from input file
            fis.close();
            mPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
            String i_image = mPref.getString(fileName, null); //get string
            String str = new String(fileData, "EUC-KR");
            Bitmap img2 = StringToBitMap(i_image); //set string to bitmap

            // Show toast telling that the date selected has the diary written
            Toast.makeText(getContext(), "Diary written", Toast.LENGTH_SHORT).show();
            edtDiary.setText(str); // set the text to received one
            imageView.setImageBitmap(img2); //set the image to received one
            btnSave.setText("Edit"); // set the button text to edit
        } catch (Exception e) {
            Toast.makeText(getContext(), "No diary existing", Toast.LENGTH_SHORT).show();
            edtDiary.setText("");
            btnSave.setText("Save new diary");
            e.printStackTrace();
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) { //when the result is okay
            try {
                // Create the bitmap with selected image
                InputStream in = getActivity().getContentResolver().openInputStream(data.getData()); //get the data
                Bitmap img = BitmapFactory.decodeStream(in); //decode the stream and save to bitmap
                in.close();
                imageView = getActivity().findViewById(R.id.imageView1);
                // set the image
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
    //Save the text of the diary
    private void saveDiary(String readDay,Context context) {
        FileOutputStream fos = null;

        try {
            fos = context.openFileOutput(readDay, MODE_PRIVATE);
            String content = edtDiary.getText().toString();
            fos.write(content.getBytes()); //Write the string into the file
            fos.close();
            Toast.makeText(getContext(), "Diary Saved", Toast.LENGTH_SHORT).show();

        } catch (Exception e) { //When Exception happens
            e.printStackTrace();
            Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
        }
    }

    public String BitMapToString(Bitmap bitmap) { //save bitmap to string
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public Bitmap StringToBitMap(String encodedString) { //save string to bitmap
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
