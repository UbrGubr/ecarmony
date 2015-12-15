package me.firstandroidapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.firstandroidapp.app.AppConfig;
import me.firstandroidapp.app.AppController;
import me.firstandroidapp.app.SessionManager;

public class ScheduleActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    static final int DIALOG_ID = 0;
    static final int DATE_DIALOG_ID = 1;
    static boolean ARRIVE_DIALOG = false;
    static int ARRIVE_TIME = 0;
    static int DEPART_TIME = 0;
    static int YEAR = 0;
    static int MONTH = 0;
    static int DAY = 0;
    static String NAME;

    TextView arriveTime;
    TextView departTime;
    TextView date;

    Button searchButton;

    int hour_x;
    int minute_x;

    private ProgressDialog pDialog;

    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        session = new SessionManager(getApplicationContext());
        NAME = AppController.getString(getApplicationContext(), "name");

        arriveTime = (TextView) findViewById(R.id.schedule_arrive_time);
        arriveTime.setOnClickListener(this);

        departTime = (TextView) findViewById(R.id.schedule_depart_time);
        departTime.setOnClickListener(this);

        date = (TextView) findViewById(R.id.schedule_date);
        date.setOnClickListener(this);

        final Calendar cal = Calendar.getInstance();
        YEAR = cal.get(Calendar.YEAR);
        MONTH = cal.get(Calendar.MONTH);
        DAY = cal.get(Calendar.DAY_OF_MONTH);

        date.setText((MONTH + 1) + "/" + DAY + "/" + YEAR);

        searchButton = (Button) findViewById(R.id.schedule_search_button);
        searchButton.setOnClickListener(this);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        // Display pop-up window with 80% height and 70% width
        getWindow().setLayout((int) (width * .9), (int) (height * .7));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_schedule, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.schedule_arrive_time:
                ARRIVE_DIALOG = true;
                showDialog(DIALOG_ID);
                break;
            case R.id.schedule_depart_time:
                ARRIVE_DIALOG = false;
                showDialog(DIALOG_ID);
                break;
            case R.id.schedule_date:
                showDialog(DATE_DIALOG_ID);
                break;
            case R.id.schedule_search_button:
                submitSchedule(NAME, ARRIVE_TIME, DEPART_TIME, MONTH, DAY, YEAR);
                //checkSchedules(ARRIVE_TIME, DEPART_TIME, MONTH, DAY, YEAR);
                //check for compatible schedules
                //show schedules to user
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_ID) {
            return new TimePickerDialog(ScheduleActivity.this, kTimePickerListener, hour_x, minute_x, false);
        }
        if (id == DATE_DIALOG_ID) {
            return new DatePickerDialog(ScheduleActivity.this, kDatePickerListener, YEAR, MONTH, DAY);
        }
        return null;
    }

    protected TimePickerDialog.OnTimeSetListener kTimePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            if(ARRIVE_DIALOG) {
                ARRIVE_TIME = hourOfDay;
                if (hourOfDay == 0) {
                    arriveTime.setText((hourOfDay + 12) + ":" + (minute < 10 ? "0" + minute : minute) + (hourOfDay < 12 ? " AM" : " PM"));
                } else {
                    arriveTime.setText((hourOfDay <= 12 ? hourOfDay : hourOfDay - 12) + ":" + (minute < 10 ? "0" + minute : minute) + (hourOfDay < 12 ? " AM" : " PM"));
                }
            }
            else {
                DEPART_TIME = hourOfDay;
                if (hourOfDay == 0) {
                    departTime.setText((hourOfDay + 12) + ":" + (minute < 10 ? "0" + minute : minute) + (hourOfDay < 12 ? " AM" : " PM"));
                } else {
                    departTime.setText((hourOfDay <= 12 ? hourOfDay : hourOfDay - 12) + ":" + (minute < 10 ? "0" + minute : minute) + (hourOfDay < 12 ? " AM" : " PM"));
                }
            }
        }
    };

    protected DatePickerDialog.OnDateSetListener kDatePickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            MONTH = monthOfYear + 1;
            DAY = dayOfMonth;
            YEAR = year;
            date.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);
        }
    };

    private void submitSchedule(final String name, final int arriveTime, final int departTime,
                              final int month, final int day, final int year) {
        // Tag used to cancel the request
        String tag_string_req = "req_schedule";

        pDialog.setMessage("Searching ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_REGISTER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if(!error) {
                        // User successfully stored in MySQL
                        // Now display results to user
                        /*
                            $response["error"] = FALSE;
                            $response["name"] = $schedule["name"];
                            $response["schedule"]["arrive"] = $schedule["arrive"];
                            $response["schedule"]["depart"] = $schedule["depart"];
                            $response["schedule"]["month"] = $schedule["month"];
                            $response["schedule"]["day"] = $schedule["day"];
                            $response["schedule"]["year"] = $schedule["year"];
                        */
                        //Log.d("ScheduleActivity", "We made it");
                        //results.setText(jObj.toString(2));
                        displaySchedules();
                    } else {
                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("tag", "schedule");
                params.put("name", name);
                params.put("arrive", Integer.toString(arriveTime));
                params.put("depart", Integer.toString(departTime));
                params.put("month", Integer.toString(month));
                params.put("day", Integer.toString(day));
                params.put("year", Integer.toString(year));

                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public void displaySchedules() {
        this.setContentView(R.layout.schedule_results_layout);
        //String[] schedules = getResources().getStringArray(R.array.schedules);
        ListView list = (ListView) findViewById(R.id.schedule_list);
        //list.setAdapter(new ArrayAdapter<String>(this, R.layout.schedule_results_layout, R.id.label, schedules));
        list.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"patrickjimison@csus.edu"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Request to Carpool!");
        intent.putExtra(Intent.EXTRA_TEXT, "Patrick Jimison has requested to carpool with you!\n\nDetails\nArrival: 12:00PM\nDeparture: 2:00PM\nDate: 12/16/15\n\n Accept   Decline");
        try {
            startActivity(Intent.createChooser(intent, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(ScheduleActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
