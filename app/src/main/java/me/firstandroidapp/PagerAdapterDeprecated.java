package me.firstandroidapp;

import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by pjjimiso on 10/20/2015.
 */
public class PagerAdapterDeprecated extends FragmentPagerAdapter{

    public PagerAdapterDeprecated(android.support.v4.app.FragmentManager fm){
        super(fm);
    }

    @Override
    public android.support.v4.app.Fragment getItem(int arg0){
        switch(arg0) {
            case 0:
                return new PassengerProfileFragment();
            case 1:
                return new SwipeScreenFragment();
            case 2:
                return new DriverProfileFragment();
            default:
                break;
        }
        return null;
    }

    @Override
    public int getCount(){
        return 3;
    }
}
