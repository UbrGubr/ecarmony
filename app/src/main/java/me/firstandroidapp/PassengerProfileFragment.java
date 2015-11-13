package me.firstandroidapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by pjjimiso on 10/20/2015.
 */
public class PassengerProfileFragment extends android.support.v4.app.Fragment {

    private String title;
    private int page;

    public static PassengerProfileFragment newInstance(int page, String title){
        PassengerProfileFragment passengerFragment = new PassengerProfileFragment();
        Bundle args = new Bundle();
        args.putInt("1", page);
        args.putString("Passenger", title);
        passengerFragment.setArguments(args);
        return passengerFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("1", 0);
        title = getArguments().getString("Passenger");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.passenger_profile_layout, container, false);
        //TextView tvLabel = (TextView) view.findViewById(R.id.tvLabel);
        //tvLabel.setText(page + " -- " + title);
        //return inflater.inflate(R.layout.passenger_profile_layout, container, false);
        return view;
    }
}
