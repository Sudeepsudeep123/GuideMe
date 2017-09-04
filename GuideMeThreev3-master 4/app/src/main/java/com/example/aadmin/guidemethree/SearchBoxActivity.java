package com.example.aadmin.guidemethree;


import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class SearchBoxActivity extends AppCompatActivity {
    Spinner spinnerPlace, spinnerType;
    ArrayAdapter<CharSequence> adapter;
    Button btnsearch;

    ArrayList<String> spacecrafts=new ArrayList<>();
    public static final String MyPref = "MyPref";
    String strPlace, strType;
    String rawJson = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_box);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        spinnerPlace = (Spinner) findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.Places, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPlace.setAdapter(adapter);


        spinnerType = (Spinner) findViewById(R.id.spinner2);
        adapter = ArrayAdapter.createFromResource(this, R.array.ShopType, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapter);

        btnsearch= (Button) findViewById(R.id.btnsubmit);
        btnsearch.setVisibility(View.INVISIBLE);

        spinnerType.setVisibility(View.INVISIBLE);


        spinnerPlace.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spinnerPlace.getSelectedItemPosition()>0) {
                    spinnerType.setVisibility(View.VISIBLE);
                }
                else
                    spinnerType.setVisibility(View.INVISIBLE);
                btnsearch.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spinnerType.getSelectedItemPosition()>0) {
                    //   flag = 1;
                    // if(flag == 1){
                    btnsearch.setVisibility(View.VISIBLE);
                    //}
                }
                else
                    btnsearch.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String  strPlace = spinnerPlace.getSelectedItem().toString();
//                String  strType = spinnerType.getSelectedItem().toString();
//                    Shops shop=new Shops();
//                shop.execute(strPlace,strType);


                BackgroundWorker bw = new BackgroundWorker();
                bw.execute();

//                Intent intent = new Intent(MainActivity.this, Shops.class);
//                intent.putExtra("data",spacecrafts);
//                startActivity(intent);
            }
        });
    }

    public class BackgroundWorker extends AsyncTask<String, Void, String> {
        ListView lv;
        AlertDialog alertDialog;
        String result;
        ConnectivityManager cManager= (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo nInfo=cManager.getActiveNetworkInfo();

        //type = params[0];
        String login_url = "https://vivid-realinement.000webhostapp.com/displaylist.php";


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Spinner spinnerplace = (Spinner) findViewById(R.id.spinner);
            Spinner spinnertype = (Spinner) findViewById(R.id.spinner2);
            strPlace = spinnerplace.getSelectedItem().toString();
            strType = spinnertype.getSelectedItem().toString();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Intent i =new Intent(SearchBoxActivity.this,Shops.class);
            i.putExtra("jsonData",rawJson);
            startActivity(i);

            //            if (s.equals("success")) {
//
//
////                //startActivity(new Intent(Shops.this, YourPage.class));
//                Toast.makeText(Shops.this, "This is my Toast message!",
//                        Toast.LENGTH_LONG).show();
//            } else {
////                AlertDialog alert = new AlertDialog.Builder(getContext().create());
////                alert.setTitle("Message");
////                alert.setMessage("Invalid");
////                alert.show();
//            }
          }


        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String postdata = URLEncoder.encode("spinnerPlace", "UTF-8") + "=" + URLEncoder.encode(strPlace, "UTF-8") + "&" +
                        URLEncoder.encode("spinnerType", "UTF-8") + "=" + URLEncoder.encode(strType, "UTF-8");
                bufferedWriter.write(postdata);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                result = "";
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                //res = result;
                rawJson = result;
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }


    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

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
}
