package me.firstandroidapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by pjjimiso on 10/20/2015.
 */
public class SwipeScreenFragment extends android.support.v4.app.Fragment {
    private String title;
    private int page;

    public static SwipeScreenFragment newInstance(int page, String title){
        SwipeScreenFragment swipeFragment = new SwipeScreenFragment();
        Bundle args = new Bundle();
        args.putInt("2", page);
        args.putString("Choose Profile", title);
        swipeFragment.setArguments(args);
        return swipeFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("2", 0);
        title = getArguments().getString("Choose Profile");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.swipe_screen_layout, container, false);
        //TextView tvLabel = (TextView) view.findViewById(R.id.tvLabel);
        //tvLabel.setText(page + " -- " + title);
        //return inflater.inflate(R.layout.passenger_profile_layout, container, false);
        return view;
    }
}
