package com.example.aadmin.guidemethree;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by aadmin on 17/06/06.
 */

public class CustomerLoginRequest extends StringRequest
{
    private static final String LOGIN_REQUEST_URL = "https://vivid-realinement.000webhostapp.com/clogin.php";
    private Map<String, String> params;

    public CustomerLoginRequest(String cname, String cpassword, Response.Listener<String> listener)
    {
        super(Request.Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("cname", cname);
        params.put("cpassword", cpassword);
    }

    @Override
    public Map<String, String> getParams()
    {
        return params;
    }
}