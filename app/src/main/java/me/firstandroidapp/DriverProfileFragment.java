package me.firstandroidapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import java.util.ArrayList;
import java.util.List;
import me.firstandroidapp.HeaderTextArrayAdapter.Item;
import me.firstandroidapp.HeaderTextArrayAdapter.Header;
import me.firstandroidapp.HeaderTextArrayAdapter.ListItem;


/**
 * Created by pjjimiso on 10/20/2015.
 */
public class DriverProfileFragment extends android.support.v4.app.ListFragment {
    private String title;
    private int page;



    public static DriverProfileFragment newInstance(int page, String title){
        DriverProfileFragment driverFragment = new DriverProfileFragment();
        Bundle args = new Bundle();
        args.putInt("3", page);
        args.putString("Driver", title);
        driverFragment.setArguments(args);
        return driverFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("3", 0);
        title = getArguments().getString("Driver");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.driver_profile_layout, container,
                false);

        List<Item> items = new ArrayList<Item>();
        items.add(new Header("To"));
        items.add(new ListItem("10/17 - 10:00AM", "3 riders"));
        items.add(new ListItem("10/18 - 11:30AM", "1 rider"));
        items.add(new ListItem("10/19 - 10:00AM", "3 riders"));
        items.add(new ListItem("10/20 - 11:30AM", "1 rider"));
        items.add(new Header("From"));
        items.add(new ListItem("10/17 - 3:00PM", "3 riders"));
        items.add(new ListItem("10/18 - 5:30PM", "2 riders"));
        items.add(new ListItem("10/19 - 3:00PM", "3 riders"));
        items.add(new ListItem("10/20 - 5:30PM", "2 riders"));

        HeaderTextArrayAdapter adapter = new HeaderTextArrayAdapter(getActivity(), items);
        setListAdapter(adapter);

        return view;
    }
}
