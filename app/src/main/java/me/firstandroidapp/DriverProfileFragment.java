package me.firstandroidapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

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
        View view = inflater.inflate(R.layout.driver_profile_layout, container, false);

        View rootView = inflater.inflate(R.layout.driver_profile_layout, container,
                false);

        String[] values = new String[] {
                "To: 10/17, 3 riders, 10:00 AM","To: 10/18, 1 rider, 11:30 AM",
                "To: 10/19, 3 riders, 10:00 AM","To: 10/20, 1 rider, 11:30 AM",
                "From: 10/17, 3 riders, 3:00 PM","From: 10/18, 2 riders, 5:30 PM",
                "From: 10/19, 3 riders, 3:00 PM","From: 10/20, 2 riders, 5:30 PM"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
        return rootView;
        //return view;
    }
}
