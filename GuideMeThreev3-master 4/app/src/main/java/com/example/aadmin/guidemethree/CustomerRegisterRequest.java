package com.example.aadmin.guidemethree;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by aadmin on 17/06/06.
 */

public class CustomerRegisterRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL="https://vivid-realinement.000webhostapp.com/cregister.php";
    private Map<String ,String> params;

    public CustomerRegisterRequest(String cname,String cpassword, String cphone, Response.Listener<String> listener)
    {
        super(Method.POST,REGISTER_REQUEST_URL, listener,null);
        params = new HashMap<>();
        params.put("cname",cname);
        params.put("cpassword",cpassword);
        params.put("cphone",cphone);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
