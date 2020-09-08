package com.the43appmart.aniruddha.nfcpay;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Aniruddha on 27/01/2018.
 */

class GetCardToSpn extends StringRequest {
    private static final String REQUEST_URL ="http://43appmart.com/NFCPay/GetCardNumber.php";
    private Map<String, String> params;

    public GetCardToSpn(String Id, Response.Listener<String> listener) {
        super(Method.POST, REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("id",Id);
    }
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
