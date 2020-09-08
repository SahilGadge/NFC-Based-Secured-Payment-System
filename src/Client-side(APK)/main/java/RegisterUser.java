package com.the43appmart.aniruddha.nfcpay;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterUser extends StringRequest {
    private static final String REGISTER_REQUEST_URL ="http://43appmart.com/NFCPay/RegisterUser.php";
    private Map<String, String> params;

    public RegisterUser( String name, String mail, String contact, String password, String ques1, String ans1, String ques2, String ans2, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("name",name);
        params.put("email",mail);
        params.put("contact",contact);
        params.put("password",password);
        params.put("ques1",ques1);
        params.put("ques2",ques2);
        params.put("ans1",ans1);
        params.put("ans2",ans2);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
