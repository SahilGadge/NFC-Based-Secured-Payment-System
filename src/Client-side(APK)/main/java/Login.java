package com.the43appmart.aniruddha.nfcpay;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {
    Button btnLogin;
    TextView SignUp;
    EditText UserName, Password;
    private SharedPreferences saveUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );
        getSupportActionBar().hide();
        UserName = (EditText) findViewById( R.id.edtUsername );
        Password = (EditText) findViewById( R.id.edtPassword );

        btnLogin = (Button) findViewById( R.id.btnLogin );
        SignUp = (TextView) findViewById( R.id.txtSignUp );
        btnLogin.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkValidation()) {
                    Toast.makeText( Login.this, "Server Under Development", Toast.LENGTH_SHORT ).show();
                    LoginReq();
                }
            }
        } );
        SignUp.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( Login.this, SignUp.class );
                startActivity( intent );
            }
        } );
    }

    private void LoginReq() {
        final String password = Password.getText().toString();
        final String email = UserName.getText().toString();

        // Response received from the server
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject( response );
                    boolean success = jsonResponse.getBoolean( "success" );

                    if (success) {

                        String id = jsonResponse.getString("id");
                        String name = jsonResponse.getString("Name");
                        String E_mail = jsonResponse.getString("Email");


                        Toast.makeText(Login.this, "Welcome " + name, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        Login.this.startActivity(intent);
                        saveUser = PreferenceManager
                                .getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = saveUser.edit();
                        editor.putString("strUserName", name);
                        editor.putString("strEmail", E_mail);
                        editor.putString("strId", id);
                        editor.commit();
                        finish();

                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder( Login.this );
                        builder.setMessage( "Invalid E-mail id or Password" )
                                .setNegativeButton( "Try Again..!", null )
                                .create()
                                .show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        LoginRequest loginRequestStudent = new LoginRequest( email, password, responseListener );
        RequestQueue queue = Volley.newRequestQueue( Login.this );
        queue.add( loginRequestStudent );

    }

    private boolean checkValidation() {
        boolean ret = true;
        if (!Validation.isEmailAddress( UserName, true )) ret = false;
        if (!Validation.hasText( Password )) ret = false;
        return ret;
    }
}
