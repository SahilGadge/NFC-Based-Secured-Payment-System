package com.the43appmart.aniruddha.nfcpay;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

public class SignUp extends AppCompatActivity {
    Button btnRegister, btnUploadImage, btnSelectImage;
    ImageView imageView;

    EditText FirstName, LastName, Email, Contact, Password, ConfirmPass, edtQues2Ans, edtQues1Ans;
    Spinner spnSecQues1, spnSecQues2;
    String[] Question1 = {"Select Security Question 1", "Whats your nick Name?",
            "Whats your primary school Name?",
            "Whats your birth place"};
    String[] Question2 = {"Select Security Question 2", "Whats your nick Name?",
            "Whats your primary school Name?",
            "Whats your birth place"};
    private int PICK_IMAGE_REQUEST = 1;
    Activity mActivity;
    private Bitmap bitmap;
    Context mContext;
    private String UPLOAD_URL = "http://43appmart.com/SalesExecutive/ImageUpload/upload.php";
    private String KEY_IMAGE = "image";
    private String KEY_NAME = "name";
    private String encodedImage;
    private String Sample="Sample";
    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_sign_up );
        getSupportActionBar().setTitle( "New User Registration" );
        FirstName = (EditText) findViewById( R.id.edtFirstName );
        LastName = (EditText) findViewById( R.id.edtLastName );
        Email = (EditText) findViewById( R.id.edtEmail );
        Contact = (EditText) findViewById( R.id.edtContact );
        Password = (EditText) findViewById( R.id.edtPassword );
        ConfirmPass = (EditText) findViewById( R.id.edtConfirmPass );
        spnSecQues1 = (Spinner) findViewById( R.id.spnSecQues1 );
        spnSecQues2 = (Spinner) findViewById( R.id.spnSecQues2 );
        edtQues1Ans = (EditText) findViewById( R.id.edtQues1Ans );
        edtQues2Ans = (EditText) findViewById( R.id.edtQues2Ans );
        imageView = (ImageView) findViewById( R.id.imageView );
        btnSelectImage = (Button) findViewById( R.id.btnSelectImage );
        btnSelectImage.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();

            }
        } );
        ArrayAdapter ques1 = new ArrayAdapter( this, android.R.layout.simple_spinner_item, Question1 );
        ques1.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spnSecQues1.setAdapter( ques1 );


        ArrayAdapter ques2 = new ArrayAdapter( this, android.R.layout.simple_spinner_item, Question2 );
        ques1.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spnSecQues2.setAdapter( ques2 );

        btnRegister = (Button) findViewById( R.id.btnRegister );
        btnRegister.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkValidation()) {
                    if (ConfirmPass.getText().toString().equals( Password.getText().toString() )) {
                        SendData();
                    } else {
                        Toast.makeText( SignUp.this, "Password does Not match", Toast.LENGTH_SHORT ).show();
                    }
                } else {
                    Toast.makeText( SignUp.this, "Check Content", Toast.LENGTH_SHORT ).show();
                }
            }
        } );
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType( "image/*" );
        intent.setAction( Intent.ACTION_GET_CONTENT );
        startActivityForResult( Intent.createChooser( intent, "Select Picture" ), PICK_IMAGE_REQUEST );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult( requestCode, resultCode, data );

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == mActivity.RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
//                imageView.setVisibility( View.VISIBLE );
                bitmap = MediaStore.Images.Media.getBitmap( getContentResolver(), filePath );
//                Setting the Bitmap to ImageView
                imageView.setImageBitmap( bitmap );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void SendData() {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject( response );
                    boolean success = jsonResponse.getBoolean( "success" );
                    if (success) {
                        UploadImage();
                        Toast.makeText( SignUp.this, "Please wait..your request will processed shortly", Toast.LENGTH_SHORT ).show();
//                        Intent intent = new Intent( SignUp.this, Login.class );
//                        SignUp.this.startActivity( intent );
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder( SignUp.this );
                        builder.setMessage( "Registration Failed...Already registered with this E-mail Id or Contact No..." )
                                .setNegativeButton( "Retry", null )
                                .create()
                                .show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        final String name = FirstName.getText().toString() + " " + LastName.getText().toString();
        final String mail = Email.getText().toString();
        final String contact = Contact.getText().toString();
        final String password = Password.getText().toString();
        final String ques1 = spnSecQues1.getSelectedItem().toString();
        final String ans1 = edtQues1Ans.getText().toString();
        final String ques2 = spnSecQues2.getSelectedItem().toString();
        final String ans2 = edtQues2Ans.getText().toString();

        RegisterUser registerRequest = new RegisterUser( name, mail, contact, password, ques1, ans1, ques2, ans2, responseListener );
        RequestQueue queue = Volley.newRequestQueue( SignUp.this );
        queue.add( registerRequest );
    }

    private void UploadImage() {
        //Showing the progress dialog
         loading = ProgressDialog.show(SignUp.this, "Uploading...", "Please wait...", false, false);

        StringRequest stringRequest = new StringRequest( Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        //Showing toast message of the response
                        Toast.makeText(SignUp.this, s, Toast.LENGTH_LONG).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast
//                        Toast.makeText(mActivity, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
                String image = getStringImage(bitmap);
                //Getting Image Name
//                String name1 = name.getText().toString().trim();
                String name1 = Sample;

                //Creating parameters
                Map<String, String> params = new Hashtable<String, String>();

                //Adding parameters
                params.put(KEY_IMAGE, image);
                params.put(KEY_NAME, name1);

                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(SignUp.this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private boolean checkValidation() {
        boolean ret = true;
        if (!Validation.isName( FirstName, true )) ret = false;
        if (!Validation.isName( LastName, true )) ret = false;
        if (!Validation.isEmailAddress( Email, true )) ret = false;
        if (!Validation.isPhoneNumber( Contact, true )) ret = false;
        if (!Validation.hasText( Password )) ret = false;
        if (!Validation.hasText( ConfirmPass )) ret = false;
        return ret;
    }
}
