package com.example.myplants;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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

import android.widget.ImageView;
import android.widget.TextView;


import java.io.ByteArrayOutputStream;

import java.io.InputStream;

import static android.app.Activity.RESULT_OK;


public class ProfileFragment extends Fragment {
    ImageView imageView;
    Button button;
    SharedPreferences mPref;
    Button edit_btn;
    Button back_btn;
    Button show_btn;

    FragmentManager fmanager;
    FragmentTransaction ftrans;
    EditproFragment editproFragment;
    MyPlantListFragment myPlantListFragment;
    SetFragment setFrag;

    String pro_name;  // user's name
    String pro_email; // user's email
    String pro_state; // user's status message
    TextView txtName;
    TextView txtEmail;
    TextView txtState;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_profile,
                container, false);

        fmanager = getFragmentManager();
        ftrans = fmanager.beginTransaction();
        mPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());

        txtName = rootView.findViewById(R.id.pro_name);
        txtEmail = rootView.findViewById(R.id.pro_email);
        txtState = rootView.findViewById(R.id.pro_state);
        //get user's information by SharedPreferences(if there is no data about user information, default value is null) and set each TextView

        pro_name = mPref.getString("prof_name", null);
        pro_email = mPref.getString("prof_email", null);
        pro_state = mPref.getString("prof_state", null);
        txtName.setText(pro_name);
        txtEmail.setText(pro_email);
        txtState.setText(pro_state);

        //if there is saved profile image in SharedPreferences, set the profile image by the saved image
        String i_image = mPref.getString("prof_img", null);
        if (i_image != null) {
            imageView = rootView.findViewById(R.id.image);
            Bitmap img2 = StringToBitMap(i_image);
            imageView.setImageBitmap(img2);
        }

        //When the 'button' pressed, update profile picture by selecting new picture from gallery
        button = rootView.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);
            }
        });

        //When 'show_button' clicked, show user's plants list
        myPlantListFragment = new MyPlantListFragment();
        show_btn = rootView.findViewById(R.id.show_btn);
        show_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ftrans.replace(R.id.container,myPlantListFragment).commit();
            }
        });

        //When the'edit_btn' pressed, replace fragment to EditprofileFragment
        edit_btn = rootView.findViewById(R.id.edit_pro);
        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editproFragment = new EditproFragment();
                ftrans.replace(R.id.container, editproFragment).commit();
            }
        });

        //back button
        back_btn = rootView.findViewById(R.id.backBtn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFrag = new SetFragment();
                ftrans.replace(R.id.container, setFrag).commit();
            }
        });
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //profile image set
        if (resultCode == RESULT_OK) {
            try {
                // Create bitmap of selected picture
                InputStream in = getActivity().getContentResolver().openInputStream(data.getData());
                Bitmap img = BitmapFactory.decodeStream(in);
                in.close();
                imageView = getActivity().findViewById(R.id.image);
                // show the image by imageView
                imageView.setImageBitmap(img);

                //save image by SharedPreferences(translate the bitmap variable to string value, and store it)
                String s_image = BitMapToString(img);
                SharedPreferences.Editor editor = mPref.edit();
                editor.putString("prof_img", s_image);
                editor.commit();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
}