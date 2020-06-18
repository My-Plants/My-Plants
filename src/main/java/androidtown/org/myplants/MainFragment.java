package androidtown.org.myplants;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class MainFragment extends Fragment {

    SubmainFragment subFrag ;
    ImageButton submain_btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_main,null);

        //When the 'submain_btn'(ImageButton) pressed, replace fragment to SubmainFragment
        subFrag = new SubmainFragment();
        submain_btn = rootView.findViewById(R.id.subMain_btn);
        submain_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fmanager = getFragmentManager();
                FragmentTransaction ftrans = fmanager.beginTransaction();
                ftrans.replace(R.id.container, subFrag).commit();
            }
        });
        // Inflate the layout for this fragment
        return rootView;
    }

}
