package androidtown.org.myplants;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class PlantslistFragment extends Fragment {

    ArrayList<String> plantList;
    private List<String> list;          // List variance with data
    private ListView listView;          // Show search
    private EditText editSearch;        // Input for searching
    private SearchAdapter adapter;      // Search adapter
    private Bundle bundle;

    FragmentManager fmanager;
    FragmentTransaction ftrans;

    PlantsInfoFragment pinfoFragment;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_plantslist,
                container, false); //View the xml, fragment_plantslist
        fmanager = getFragmentManager();
        ftrans = fmanager.beginTransaction(); //begin transaction

        listView = (ListView)rootView.findViewById(R.id.plantListView);
        editSearch = (EditText)rootView.findViewById(R.id.editSearch);

        plantList = new ArrayList<>();
        plantList = ((MainActivity) MainActivity.context_main).plantList; //Get plant list context_main from main activity

        list = new ArrayList<String>();
        list.addAll(plantList);  //Add  plantlist in the list

        adapter = new SearchAdapter(list, MainActivity.context_main); //search from the list
        listView.setAdapter(adapter); //set adapter

        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) { //After text input
                //Call search method
                String text = editSearch.getText().toString();
                search(text);
            }
        });

        pinfoFragment = new PlantsInfoFragment();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int position, long id) { //If clicking the item

                String selected_item = plantList.get(position); //Get the item's position and store in the string
                bundle = new Bundle();
                bundle.putString("selecPlant", selected_item); //put the selected item's name
                pinfoFragment.setArguments(bundle); //set arguments in bundle

                ftrans.replace(R.id.container, pinfoFragment).commit(); //Replace with plant info fragment
            }
        });
        return rootView;
    }

    public void search(String charText) {

        // Clear input when searching at the first time
        list.clear();

        // If no input, show all the list
        if (charText.length() == 0) {
            list.addAll(plantList);
        }
        // When typing ...
        else
        {
            // Search all the data in list
            for(int i = 0;i < plantList.size(); i++)
            {
                // if it contains charText
                if (plantList.get(i).toLowerCase().contains(charText))
                {
                    // Add the searched data in list
                    list.add(plantList.get(i));
                }
            }
        }
        // Update adapter based on the change
        adapter.notifyDataSetChanged();
    }
}