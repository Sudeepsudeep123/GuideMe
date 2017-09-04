package com.example.aadmin.guidemethree;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class CustomerLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login);
        final EditText etcname = (EditText) findViewById(R.id.etcname);
        final EditText etcpassword = (EditText) findViewById(R.id.etcpassword);
        final Button btn_clogin = (Button) findViewById(R.id.btn_clogin);
        final Button RegisterHere = (Button) findViewById(R.id.bRegister);



        RegisterHere.setOnClickListener(new View.OnClickListener() //register here links to RegisterActivity by this code
        {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(CustomerLoginActivity.this,CustomerRegisterActivity.class);
                CustomerLoginActivity.this.startActivity(registerIntent);

            }
        });

        btn_clogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = etcname.getText().toString();
                final String password = etcpassword.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success)
                            {
                                String name =jsonResponse.getString("name");
                                String password = jsonResponse.getString("password");

                                Intent intent = new Intent(CustomerLoginActivity.this, CustomerUserAreaActivity.class);
                                intent.putExtra("name", name);
                                intent.putExtra("password", password);
                                // intent.putExtra("address", address);
                                CustomerLoginActivity.this.startActivity(intent);



                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(CustomerLoginActivity.this);
                                builder.setMessage("Login  Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                CustomerLoginRequest loginRequest = new CustomerLoginRequest(name,password,responseListener);
                RequestQueue queue = Volley.newRequestQueue(CustomerLoginActivity.this);
                queue.add(loginRequest);
            }
        });
    }
}
