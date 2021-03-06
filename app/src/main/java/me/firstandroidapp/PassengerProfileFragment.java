package me.firstandroidapp;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import me.firstandroidapp.HeaderTextArrayAdapter.Item;
import me.firstandroidapp.HeaderTextArrayAdapter.Header;
import me.firstandroidapp.HeaderTextArrayAdapter.ListItem;


/**
 * Created by pjjimiso on 10/20/2015.
 */
public class PassengerProfileFragment extends android.support.v4.app.ListFragment implements View.OnClickListener {

    private String title;
    private int page;

    Button searchDrivers;

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

        searchDrivers = (Button) view.findViewById(R.id.search_riders);
        searchDrivers.setOnClickListener(this);

        List<Item> items = new ArrayList<Item>();
        items.add(new Header("To"));
        items.add(new ListItem("10/17 - 10:00AM", "3 passengers"));
        items.add(new ListItem("10/18 - 11:30 AM", "1 passenger"));
        items.add(new ListItem("10/19 - 10:00 AM", "3 passengers"));
        items.add(new ListItem("10/20 - 11:30 AM", "1 passenger"));
        items.add(new Header("From"));
        items.add(new ListItem("10/17 - 3:00 PM", "3 passengers"));
        items.add(new ListItem("10/18 - 5:30 PM", "2 passengers"));
        items.add(new ListItem("10/19 - 3:00 PM", "3 passengers"));
        items.add(new ListItem("10/20 - 5:30 PM", "2 passengers"));

        HeaderTextArrayAdapter adapter = new HeaderTextArrayAdapter(getActivity(), items);
        setListAdapter(adapter);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_riders:
                Intent intent = new Intent(getActivity(),
                        ScheduleActivity.class);
                startActivity(intent);
                break;
        }
    }
}
