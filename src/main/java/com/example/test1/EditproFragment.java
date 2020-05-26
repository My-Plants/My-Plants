package com.example.test1;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static android.app.Activity.RESULT_OK;


public class EditproFragment extends Fragment {
    Button save_btn;
    EditText e_name;
    EditText e_email;
    EditText e_state;
    ProfileFragment proFragment;

    FragmentManager fmanager;
    FragmentTransaction ftrans;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_editpro,
                container, false);

        fmanager = getFragmentManager();
        ftrans = fmanager.beginTransaction();
        proFragment = new ProfileFragment();

        save_btn = rootView.findViewById(R.id.save_btn);
        e_name = rootView.findViewById(R.id.proe_name);
        e_email = rootView.findViewById(R.id.proe_email);
        e_state = rootView.findViewById(R.id.proe_state);

        //When the 'save_btn' pressed, get text from TextViews and put them in a proFragment's bundle, and replace fragment to ProfileFragment
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = e_name.getText().toString();
                String email = e_email.getText().toString();
                String state = e_state.getText().toString();

                Bundle bundle = new Bundle();
                bundle.putString("p_name",name);
                bundle.putString("p_email",email);
                bundle.putString("p_state",state);

                proFragment.setArguments(bundle);

                ftrans.replace(R.id.container, proFragment).commit();
            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }

}
