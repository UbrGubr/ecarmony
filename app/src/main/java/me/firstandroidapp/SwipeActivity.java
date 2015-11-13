package me.firstandroidapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class SwipeActivity extends AppCompatActivity {
    FragmentPagerAdapter viewpagerAdapter;
    Toolbar toolbar;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //toolbar.setNavigationIcon(R.id.

        ViewPager viewpager = (ViewPager) findViewById(R.id.pager);
        viewpagerAdapter = new PagerAdapter(getSupportFragmentManager());
        viewpager.setAdapter(viewpagerAdapter);
        viewpager.setCurrentItem(1);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.gmap_button:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public static class PagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 3;

        public PagerAdapter(FragmentManager fragmentManager){
            super(fragmentManager);
        }

        //Return total number of pages
        @Override
        public int getCount(){
            return NUM_ITEMS;
        }

        //Return the fragment to display for that page
        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            switch (position) {
                case 0: // This will show PassengerProfileFragment
                    return PassengerProfileFragment.newInstance(0, "Passenger Profile");
                case 1: // This will show SwipeScreenFragment
                    return SwipeScreenFragment.newInstance(1, "Choose Profile");
                case 2: // This will show DriverProfileFragment
                    return DriverProfileFragment.newInstance(2, "Driver Profile");
                default:
                    return null;
            }
        }

        //Return the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position){
            return "Page" + position;
        }
    }
}