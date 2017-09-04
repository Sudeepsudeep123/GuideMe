package com.example.aadmin.guidemethree;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by aadmin on 17/06/06.
 */

public class RegisterRequest extends StringRequest
{
    private static final String REGISTER_REQUEST_URL="https://vivid-realinement.000webhostapp.com/register.php";
    private Map<String ,String> params;

    public RegisterRequest(String name, String username, String phone, String password, String city_name, String area, String cordinates, Response.Listener<String> listener)
    {
        super(Method.POST,REGISTER_REQUEST_URL, listener,null);
        params = new HashMap<>();
        params.put("name",name);
        params.put("username",username);
        params.put("phone",phone);
        params.put("password",password);
        params.put("city_name",city_name);
        params.put("area",area);
        params.put("cordinates",cordinates);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}

