package com.the43appmart.aniruddha.nfcpay;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aniruddha on 27/01/2018.
 */

public class AddAmount extends Fragment {

    private SharedPreferences savecardDetails;
    TextView txtCardNumber, txtCardHolderName, txtExpiry;
    EditText edtAmount, edtAnswer;
    Button btnAddAmt;
    Spinner spnSelectQues;
    String[] Question1 = {"Select Security Question 1", "Whats your nick Name?",
            "Whats your primary school Name?",
            "Whats your birth place"};

    private SharedPreferences saveUser;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.add_amount, container, false );

        txtCardNumber = (TextView) view.findViewById( R.id.txtCardNumber );
        txtCardHolderName = (TextView) view.findViewById( R.id.txtCardHolderName );
        edtAmount = (EditText) view.findViewById( R.id.edtAmount );
        edtAnswer = (EditText) view.findViewById( R.id.edtAnswer111 );

        btnAddAmt = (Button) view.findViewById( R.id.btnAddAmt );
        txtExpiry = (TextView) view.findViewById( R.id.txtExpiry );

        savecardDetails = PreferenceManager.getDefaultSharedPreferences( getContext() );
        txtCardHolderName.setText( savecardDetails.getString( "strname", "" ) );
        txtCardNumber.setText( savecardDetails.getString( "strcardnumber", "" ) );
        txtExpiry.setText( savecardDetails.getString( "strexpiry", "" ) );

        spnSelectQues = (Spinner) view.findViewById( R.id.spnSelectQues111 );

        ArrayAdapter ques1 = new ArrayAdapter( getContext(), android.R.layout.simple_spinner_item, Question1 );
        ques1.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spnSelectQues.setAdapter( ques1 );
        btnAddAmt.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LOadQues();

            }
        } );

        return view;
    }

    private void LOadQues() {
        String q=spnSelectQues.getSelectedItem().toString();
        String a=edtAnswer.getText().toString();
        final String ques =q;
        final String ans =a;
        saveUser = PreferenceManager.getDefaultSharedPreferences(getContext());
        String userid = saveUser.getString("strId", "");
        // Response received from the server
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject( response );
                    boolean success = jsonResponse.getBoolean( "success" );

                    if (success) {

                        Toast.makeText( getContext(), "Answer n Question is correct", Toast.LENGTH_SHORT ).show();


                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder( getContext() );
                        builder.setMessage( "Invalid Question or Answer" )
                                .setNegativeButton( "Try Again..!", null )
                                .create()
                                .show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        CompareRequest loginRequestStudent = new CompareRequest( ques, ans,userid, responseListener );
        RequestQueue queue = Volley.newRequestQueue( getContext() );
        queue.add( loginRequestStudent );

    }
}
