package com.example.aadmin.guidemethree;


import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {
    SharedPreferences sf;
    //ConnectivityManager cManager;
    //NetworkInfo nInfo;

    private Button button;
    private TextView t;
    private LocationManager locationManager;
    private LocationListener listener;

    Spinner spinner, spinner1;
    ArrayAdapter<CharSequence> adapter, adapter1;
    String city_name, area;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        t=(TextView)findViewById(R.id.cordinates);

        button = (Button) findViewById(R.id.button);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);


        listener = new LocationListener() {

            @Override

            public void onLocationChanged(Location location) {
                TextView cleartext;
                cleartext = ((TextView) findViewById(R.id.cordinates));
                cleartext.setText("");

                t.append("\n " + location.getLatitude() + "N" + "," + +location.getLongitude() + "E");

            }

            @Override

            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override

            public void onProviderEnabled(String s) {

            }

            @Override

            public void onProviderDisabled(String s) {

                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);

                startActivity(i);

            }

        };

        configure_button();


        spinner=(Spinner)findViewById(R.id.spinner);
        spinner1=(Spinner)findViewById(R.id.spinner2);
        adapter=ArrayAdapter.createFromResource(this,R.array.city,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                city_name= (String) parent.getItemAtPosition(position);
                //   Toast.makeText(RegisterActivity.this, city_name, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        adapter1=ArrayAdapter.createFromResource(this,R.array.area,android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                area= (String) parent.getItemAtPosition(position);
                //   Toast.makeText(RegisterActivity.this, city_name, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        final EditText etPhone = (EditText) findViewById(R.id.etPhone);
        final EditText etName = (EditText) findViewById(R.id.etName);
        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final EditText etConfirmPassword=(EditText)findViewById(R.id.etConfirmPassword);

        final TextView etcordinates=(TextView)findViewById(R.id.cordinates);
        final Button bRegister = (Button) findViewById(R.id.bRegister);
        bRegister.setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        ConnectivityManager cManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                        NetworkInfo nInfo = cManager.getActiveNetworkInfo();
                        if (nInfo != null && nInfo.isConnected()) {
                            //Toast.makeText(RegisterActivity.this, "Saving...", Toast.LENGTH_SHORT).show();


                            final String name = etName.getText().toString();
                            final String username = etUsername.getText().toString();
                            final String phone = etPhone.getText().toString();
                            final String password = etPassword.getText().toString();
                            String confirmPassword=etConfirmPassword.getText().toString();
                            final String cordinates=etcordinates.getText().toString();
                           //Toast.makeText(RegisterActivity.this,cordinates,Toast.LENGTH_SHORT).show();
                            Toast.makeText(RegisterActivity.this, "Register successful", Toast.LENGTH_SHORT).show();

//                            Context context = getApplicationContext();
//                            CharSequence text = "YOUR ACCOUNT HAS BEEN REGISTERED";
//                            int duration = Toast.LENGTH_LONG;
//
//                            Toast toast = Toast.makeText(context, text, duration);
//                            toast.show();
                            // check if any of the fields are vaccant
                            if (name.equals("") || password.equals("") || username.equals("") || phone.equals("") ) //|| cordinates.equals("")
                            {
                                Toast.makeText(RegisterActivity.this, "Field Vaccants", Toast.LENGTH_SHORT).show();
                            } else {
                                if (password.length() < 6) {
                                    Toast.makeText(RegisterActivity.this, "Password should have atleast 6 characters", Toast.LENGTH_SHORT).show();
                                }else
                                    if(!password.equals(confirmPassword)){
                                        Toast.makeText(RegisterActivity.this, "Password does not match", Toast.LENGTH_LONG).show();
                                        return;

                                    }

                                else {
                                    if (phone.length() != 10) {
                                        Toast.makeText(RegisterActivity.this, "Please enter a valid number " + "\n" + "(Do not include country code)", Toast.LENGTH_LONG).show();
                                    } else {
                                        Response.Listener<String> responseListener =
                                                new Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(String response) {
                                                        try {
                                                            JSONObject jsonResponse = new JSONObject(response);
                                                            String success = jsonResponse.getString("success");
                                                            if (success.equals("success")) {
                                                                Toast.makeText(RegisterActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                                                RegisterActivity.this.startActivity(intent);
                                                            } else if (success.equals("username")) {
                                                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                                                builder.setMessage("Username already exists").setNegativeButton("Retry", null).create().show();
                                                            } else if (success.equals("phone")) {
                                                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                                                builder.setMessage("Phone number already Registered").setNegativeButton("Retry", null).create().show();
                                                            } else {
                                                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                                                builder.setMessage("Registration failed please try again later").setNegativeButton("Retry", null).create().show();
                                                            }
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                };
                                        RegisterRequest registerRequest = new RegisterRequest(name, username, phone, password,city_name, area, cordinates, responseListener);
                                        RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                                        queue.add(registerRequest);
                                    }
                                }
                            }
                        }
                        else
                        {
                            Toast.makeText(RegisterActivity.this,"Internet Access required to Register",Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }


    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {

            case 10:

                configure_button();

                break;

            default:

                break;

        }

    }

    void configure_button()
    {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}

                        , 10);

            }

            return;

        }

        // this code won't execute IF permissions are not allowed, because in the line above there is return statement.

        button.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {

                // t=(TextView) findViewById(R.id.textView);
                //noinspection MissingPermission

                locationManager.requestLocationUpdates("gps", 5000, 0, listener);


            }

        });

    }

}
