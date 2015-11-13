package me.firstandroidapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by pjjimiso on 10/20/2015.
 */
public class DriverProfileFragment extends android.support.v4.app.Fragment {
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
        //TextView tvLabel = (TextView) view.findViewById(R.id.tvLabel);
        //tvLabel.setText(page + " -- " + title);
        //return inflater.inflate(R.layout.passenger_profile_layout, container, false);
        return view;
    }
}
