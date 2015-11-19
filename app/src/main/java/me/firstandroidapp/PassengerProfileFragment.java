package me.firstandroidapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


/**
 * Created by pjjimiso on 10/20/2015.
 */
public class PassengerProfileFragment extends android.support.v4.app.ListFragment {

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

        //View rootView = inflater.inflate(R.layout.passenger_profile_layout, container, false);

        String[] values = new String[] {
                "To: 10/17, 3 passengers, 10:00 AM","To: 10/18, 1 passenger, 11:30 AM",
                "To: 10/19, 3 passengers, 10:00 AM","To: 10/20, 1 passenger, 11:30 AM",
                "From: 10/17, 3 passengers, 3:00 PM","From: 10/18, 2 passengers, 5:30 PM",
                "From: 10/19, 3 passengers, 3:00 PM","From: 10/20, 2 passengers, 5:30 PM"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                R.layout.text_view_layout, R.id.text_view, values);
        setListAdapter(adapter);
        return view;
    }
}
