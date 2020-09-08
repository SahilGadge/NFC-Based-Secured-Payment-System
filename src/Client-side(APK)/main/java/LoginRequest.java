package com.the43appmart.aniruddha.nfcpay;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Aniruddha on 19/12/2017.
 */

public class LoginRequest extends StringRequest {
    private static final String LOGIN_REQUEST_URL ="http://43appmart.com/NFCPay/LoginRequest.php";
    private Map<String, String> params;

    public LoginRequest(String email, String password, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("email",email);
        params.put("password",password);
    }


    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
