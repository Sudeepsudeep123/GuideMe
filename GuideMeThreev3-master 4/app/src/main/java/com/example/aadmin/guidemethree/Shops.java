package com.example.aadmin.guidemethree;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Shops extends AppCompatActivity {
    String name, city_name, type,id,username;
    JSONArray ja;
    ArrayList<String> spacecrafts;

    public Shops(){
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shops);
        ListView lv = (ListView) findViewById(R.id.lv);
        Intent intent = getIntent();
        String data = intent.getStringExtra("jsonData");
        spacecrafts = new ArrayList<>();
        parseData(data);
//        for(String s : spacecrafts){
//            Log.e("Data",s);
//        }

        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, spacecrafts);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(), spacecrafts.get(position), Toast.LENGTH_SHORT).show();
                Intent i =new Intent(Shops.this,Profile.class);
                JSONObject selectedObject=null;
                try {
                    selectedObject=ja.getJSONObject(position);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Bundle bundle = new Bundle();

                try {
                    bundle.putString("name", selectedObject.getString("name"));
                    bundle.putString("city_name", selectedObject.getString("city_name"));
                    bundle.putString("type", selectedObject.getString("type"));
                    bundle.putString("username", selectedObject.getString("username"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                i.putExtras(bundle);
                startActivity(i);

            }
        });
    }

    public void parseData(String rawJson) {

        try {
            JSONObject rootObj = new JSONObject(rawJson);

            ja = rootObj.getJSONArray("server");
            JSONObject jo;
            spacecrafts.clear();
            for (int i = 0; i < ja.length(); i++) {
                jo = ja.getJSONObject(i);

                //id = jo.getString("user_id");
                name = jo.getString("name");
                city_name = jo.getString("city_name");
                type = jo.getString("type");
                username = jo.getString("username");

                //spacecrafts.add(id);
                spacecrafts.add(name);
                //spacecrafts.add(address);
                //spacecrafts.add(type);

                //new Shops(name,address,type);
            }
            //return true;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //return false;
    }
}