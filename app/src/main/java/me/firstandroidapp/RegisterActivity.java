package me.firstandroidapp;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import me.firstandroidapp.app.AppConfig;
import me.firstandroidapp.app.AppController;
import me.firstandroidapp.app.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gowthamandroiddeveloper Chandrasekar on 04-09-2015.
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    static boolean ACCEPTED = false;

    TextView tvLogin;
    TextInputLayout fullName;
    TextInputLayout addressRegister;
    TextInputLayout emailRegister;
    TextInputLayout passwordRegister;
    EditText etFullName;
    EditText etAddressRegister;
    EditText etEmailRegister;
    EditText etPasswordRegister;
    Button registerButton;
    CheckBox ageLimit;

    SessionManager session;

    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //initializing Views
        fullName = (TextInputLayout) findViewById(R.id.fullname_registerlayout);
        addressRegister = (TextInputLayout) findViewById(R.id.address_registerlayout);
        emailRegister = (TextInputLayout) findViewById(R.id.email_registerlayout);
        passwordRegister = (TextInputLayout) findViewById(R.id.password_registerlayout);
        etFullName = (EditText) findViewById(R.id.fullname_register);
        etAddressRegister = (EditText) findViewById(R.id.address_register);
        etEmailRegister = (EditText) findViewById(R.id.email_register);
        etPasswordRegister = (EditText) findViewById(R.id.password_register);
        tvLogin = (TextView) findViewById(R.id.tv_signin);
        registerButton = (Button) findViewById(R.id.register_button);
        ageLimit = (CheckBox) findViewById(R.id.age_limit);


        //setting toolbar
        //Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolBar);

        tvLogin.setOnClickListener(this);
        ageLimit.setOnCheckedChangeListener(this);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Session manager
        session = new SessionManager(getApplicationContext());

        // Check if user is already logged in
        if (session.isLoggedIn()) {
            // User is already logged in. Move to main activity
            Intent intent = new Intent(RegisterActivity.this,
                    SwipeActivity.class);
            startActivity(intent);
            finish();
        }

    }

    /*
    function to register user details in mysql database
     */
    private void registerUser(final String name, final String email,
                              final String password, final String address) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        pDialog.setMessage("Registering ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_REGISTER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // User successfully stored in MySQL
                        // Now store the user in sqlite
                        String uid = jObj.getString("uid");

                        JSONObject user = jObj.getJSONObject("user");
                        String name = user.getString("name");
                        String email = user.getString("email");
                        String created_at = user
                                .getString("created_at");
                        String address = user.getString("address");

                        AppController.setString(RegisterActivity.this, "uid", uid);
                        AppController.setString(RegisterActivity.this, "name", name);
                        AppController.setString(RegisterActivity.this, "email", email);
                        AppController.setString(RegisterActivity.this, "created_at", created_at);
                        AppController.setString(RegisterActivity.this, "address", address);

                        // Launch login activity
                        Intent intent = new Intent(
                                RegisterActivity.this,
                                LoginActivity.class);
                        startActivity(intent);
                        finish();
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
                params.put("tag", "register");
                params.put("name", name);
                params.put("email", email);
                params.put("password", password);
                params.put("address", address);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked) {
            registerButton.setOnClickListener(this);
            registerButton.setClickable(true);
            registerButton.setBackgroundColor(getResources().getColor(R.color.register_button_enabled));
        }
        else {
            registerButton.setClickable(false);
            registerButton.setBackgroundColor(getResources().getColor(R.color.register_button_disabled));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_signin:
                Intent intent = new Intent(getApplicationContext(),
                        LoginActivity.class);
                startActivity(intent);
                finish();
            case R.id.register_button:
                String name = etFullName.getText().toString();
                String email = etEmailRegister.getText().toString();
                String password = etPasswordRegister.getText().toString();
                String address = etAddressRegister.getText().toString();

                if (!name.isEmpty() && !address.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                    if(!email.contains("@csus.edu")) {
                        Snackbar.make(v, "Email must be a valid CSUS address!", Snackbar.LENGTH_LONG).show();
                    }
                    else {
                        if(acceptTerms()) {
                            registerUser(name, email, password, address);
                        }
                    }
                } else {
                    Snackbar.make(v, "Please fill out all fields!", Snackbar.LENGTH_LONG)
                            .show();
                }
                break;
        }
    }

    public boolean acceptTerms() {
        AlertDialog.Builder dlgEULA = new AlertDialog.Builder(this);
        dlgEULA.setMessage("I pitty the fool who doesn't accept these terms");
        dlgEULA.setTitle("Terms of Service");
        dlgEULA.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                ACCEPTED = true;
                dialog.cancel();
            }
        });
        dlgEULA.setNegativeButton("Decline", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                final Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        AlertDialog disc = dlgEULA.create();
        disc.show();

        return ACCEPTED;
    }
}