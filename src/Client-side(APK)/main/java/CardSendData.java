package com.the43appmart.aniruddha.nfcpay;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CardSendData extends StringRequest {
    private static final String REGISTER_REQUEST_URL ="http://43appmart.com/NFCPay/AddCard.php";
    private Map<String, String> params;

    public CardSendData(String id,String cardbrand, String holdername, String cardnumber, String cardcvv, String expiry,  Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("userid",id);
        params.put("cardbrand",cardbrand);
        params.put("holdername",holdername);
        params.put("cardnumber",cardnumber);
        params.put("cardcvv",cardcvv);
        params.put("expiry",expiry);
    }
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
