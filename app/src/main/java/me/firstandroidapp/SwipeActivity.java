package me.firstandroidapp;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.io.IOException;

public class SwipeActivity extends AppCompatActivity {
    FragmentPagerAdapter viewpagerAdapter;
    Toolbar toolbar;
    private static final String TAG = SwipeActivity.class.getSimpleName();

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.toolbar_title));
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        ViewPager viewpager = (ViewPager) findViewById(R.id.pager);
        viewpagerAdapter = new PagerAdapter(getSupportFragmentManager());
        viewpager.setAdapter(viewpagerAdapter);
        viewpager.setCurrentItem(1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_swipe, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.gmap_button:
                // Call MapsActivity - a Google Maps API implementation without navigation
                //Intent intent = new Intent(this, MapsActivity.class);
                //this.startActivity(intent);   // Call MapsActivity - a Google Maps API implementation without navigation
                final AlertDialog.Builder adb = new AlertDialog.Builder(this);
                try {
                    final Intent openMapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=Sacramento State University&daddr=Sacramento State University"));
                    openMapIntent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                    startActivity(openMapIntent);   // Call built-in Google Maps App - full navigation support without ability to customize
                } catch (ActivityNotFoundException e) {
                    AlertDialog alert = adb.create();
                    alert.setMessage("Google Maps must be installed on this device.");
                    alert.show();
                    Log.e(TAG, "Google Maps is not installed.", e);
                }
                break;
            case R.id.action_signOut:
                final Intent signOutIntent = new Intent(this, LoginActivity.class);
                startActivity(signOutIntent);
                this.finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
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

    public void launchMapIntent() throws IOException {
        try {
            final Intent openMapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=Sacramento State University&daddr=Sacramento State University"));
            openMapIntent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
            startActivity(openMapIntent);   // Call built-in Google Maps App - full navigation support without ability to customize
        } catch (ExceptionInInitializerError e) {
            Log.e(TAG, "Google Maps is not installed.", e);
            throw new ExceptionInInitializerError("Google Maps must be installed on this device.");
        }
        finally {
            this.finish();
        }
    }
}