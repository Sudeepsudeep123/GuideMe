package com.example.aadmin.guidemethree;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final Button bLogin = (Button) findViewById(R.id.bLogin);
        final Button registerLink = (Button) findViewById(R.id.bRegister);

        registerLink.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Intent registerIntent= new Intent(LoginActivity.this,RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);

            }
        });
        bLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();
                ConnectivityManager cManager= (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo nInfo=cManager.getActiveNetworkInfo();
                if(nInfo!=null && nInfo.isConnected())
                {
                    Toast.makeText(LoginActivity.this,"Connecting...",Toast.LENGTH_SHORT).show();
                }

                else
                {
                    Toast.makeText(LoginActivity.this,"Internet Access required to login",Toast.LENGTH_LONG).show();
                }


                Response.Listener<String> responseListener = new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success)
                            {

                                String name = jsonResponse.getString("name");
                                String phone = jsonResponse.getString("phone");
                                String city_name=jsonResponse.getString("city_name");
                                String area  = jsonResponse.getString("area");

                                Intent intent = new Intent(LoginActivity.this, UserAreaActivity.class);
                                intent.putExtra("name", name);
                                intent.putExtra("username", username);
                                intent.putExtra("phone", phone);
                                intent.putExtra("city_name",city_name);
                                intent.putExtra("area",area);

                                LoginActivity.this.startActivity(intent);

                            }
                            else
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("Invalid username and password.").setNegativeButton("Retry", null).create().show();
                            }

                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(username, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
                // Response received from the server


            }
        });
    }
}
