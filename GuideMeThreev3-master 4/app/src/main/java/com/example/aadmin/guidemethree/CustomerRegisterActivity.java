package com.example.aadmin.guidemethree;

import android.content.Intent;
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

public class CustomerRegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_register);

        final EditText etcname = (EditText) findViewById(R.id.etcname);
        final EditText etcpassword = (EditText) findViewById(R.id.etcpassword);
        final EditText etcphone = (EditText) findViewById(R.id.etcphone);
        final Button btn_cregister = (Button) findViewById(R.id.btn_cregister);


        btn_cregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CustomerRegisterActivity.this, "This is my Toast message!",
                        Toast.LENGTH_LONG).show();
                final String cname = etcname.getText().toString();
                final String cpassword = etcpassword.getText().toString();
                final String cphone = etcphone.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                Intent intent = new Intent(CustomerRegisterActivity.this, CustomerLoginActivity.class);
                                CustomerRegisterActivity.this.startActivity(intent);

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(CustomerRegisterActivity.this);
                                builder.setMessage("Register Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                };
                CustomerRegisterRequest cregisterRequest = new CustomerRegisterRequest(cname, cpassword, cphone, responseListener);
                RequestQueue queue = Volley.newRequestQueue(CustomerRegisterActivity.this);
                queue.add(cregisterRequest);

            }
        });


    }

}

