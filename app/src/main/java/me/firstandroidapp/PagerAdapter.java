package me.firstandroidapp;

import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by pjjimiso on 10/20/2015.
 */
public class PagerAdapter extends FragmentPagerAdapter{

    public PagerAdapter(android.support.v4.app.FragmentManager fm){
        super(fm);
    }

    @Override
    public android.support.v4.app.Fragment getItem(int arg0){
        switch(arg0) {
            case 0:
                return new FragmentOne();
            case 1:
                return new FragmentTwo();
            case 2:
                return new FragmentThree();
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
