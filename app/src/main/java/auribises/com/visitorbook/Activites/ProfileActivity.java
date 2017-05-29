package auribises.com.visitorbook.Activites;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import auribises.com.visitorbook.Class.AdminLogin;
import auribises.com.visitorbook.Class.RegisterAdmin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import auribises.com.visitorbook.Class.RegisterAdmin;
import auribises.com.visitorbook.R;
import auribises.com.visitorbook.Util;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class ProfileActivity extends AppCompatActivity {

    @InjectView(R.id.editTextName)
    EditText eTxtName;

    @InjectView(R.id.editTextPhone)
    EditText eTxtPhone;


    @InjectView(R.id.editTextEmail)
    EditText eTxtEmail;

    @InjectView(R.id.editTextBirthdate)
    EditText eTxtBirthdate;

    @InjectView(R.id.editTextAddress)
    EditText eTxtAddress;

    @InjectView(R.id.editTextQualification)
    EditText eTxtQualification;

    @InjectView(R.id.editTextExperience)
    EditText eTxtExperience;

    @InjectView(R.id.passworda)
    EditText eTxtPassword;

    @InjectView(R.id.radioButtonMale)
    RadioButton rbMale;

    @InjectView(R.id.radioButtonFemale)
    RadioButton rbFemale;

    @InjectView(R.id.buttonRegister)
    Button btnSubmit;

    RequestQueue requestQueue;
    String name,phone,email,birthdate,gender,address,qualification,experience,password;
    int id=0;
    boolean flag=true;
    RegisterAdmin registerAdmin;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    JSONArray jsonArray;
    ProgressDialog progressDialog;
    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.inject(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Intent rcv = getIntent();

        requestQueue = Volley.newRequestQueue(this);
        registerAdmin=new RegisterAdmin();

        sharedPreferences = getSharedPreferences(Util.sharedPreferences,MODE_PRIVATE);
        registerAdmin.setId(sharedPreferences.getInt("id",0));
        registerAdmin.setName(sharedPreferences.getString("name",""));
        registerAdmin.setPhone(sharedPreferences.getString("phone",""));
        registerAdmin.setEmail(sharedPreferences.getString("email",""));
        registerAdmin.setBirthdate(sharedPreferences.getString("birthdate",""));
        registerAdmin.setGender(sharedPreferences.getString("gender",""));
        registerAdmin.setAddress(sharedPreferences.getString("address",""));
        registerAdmin.setQualification(sharedPreferences.getString("qualification",""));
        registerAdmin.setExperience(sharedPreferences.getString("experience",""));
        registerAdmin.setPassword(sharedPreferences.getString("password",""));


        setFields();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        requestQueue = Volley.newRequestQueue(this);



        //retrieveFromStudent();
    }
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    public boolean onCreateOptionsMenu(Menu menu){
        menu.add(0,101,0,"update_profile");
        return super.onCreateOptionsMenu(menu);
    }


    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == 101){
            showAlertDialog();
        }

        return super.onOptionsItemSelected(item);
    }

    public  void OnClickProfile(View view){
        registerAdmin.setName(eTxtName.getText().toString().trim());
        registerAdmin.setPhone(eTxtPhone.getText().toString().trim());
        registerAdmin.setEmail(eTxtEmail.getText().toString().trim());
        registerAdmin.setBirthdate(eTxtBirthdate.getText().toString().trim());
//        registerAdmin.setGender(eTxtGender.getText().toString().trim());
        registerAdmin.setAddress(eTxtAddress.getText().toString().trim());
        registerAdmin.setQualification(eTxtQualification.getText().toString().trim());
        registerAdmin.setExperience(eTxtExperience.getText().toString().trim());
        registerAdmin.setPassword(eTxtPassword.getText().toString().trim());
        if(view.getId()==R.id.buttonRegister){
            if(validate()) {
                if (isNetworkConected())
                    updateData();
                else
                    Toast.makeText(this, "Please connect to Internet", Toast.LENGTH_LONG).show();
            }
        }
        }


    void showAlertDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Logout");
        builder.setMessage("Do you wish to Logout");
        builder.setCancelable(false); // If user will press the back key dialog will not be dismissed
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(ProfileActivity.this,AdminloginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("Cancel",null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void setFields(){
        Log.i("test","----"+registerAdmin.toString());
        eTxtName.setText(registerAdmin.getName());
        Log.i("test",eTxtName.toString());
        eTxtPhone.setText(registerAdmin.getPhone());
        Log.i("test",eTxtPhone.toString());
        eTxtEmail.setText(registerAdmin.getEmail());
        Log.i("test",eTxtEmail.toString());
        eTxtBirthdate.setText(registerAdmin.getBirthdate());
        Log.i("test",eTxtBirthdate.toString());
        eTxtAddress.setText(registerAdmin.getAddress());
        Log.i("test",eTxtAddress.toString());
        eTxtQualification.setText(registerAdmin.getQualification());
        Log.i("test",eTxtQualification.toString());
        eTxtExperience.setText(registerAdmin.getExperience());
        Log.i("test",eTxtExperience.toString());
        eTxtPassword.setText(registerAdmin.getPassword());
        Log.i("test",eTxtPassword.toString());
    }

    void updateData(){


        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, Util.UPDATEPROFILE_PHP, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {

                try{
                    Log.i("test",response.toString());
                    JSONObject jsonObject = new JSONObject(response);
                    int success = jsonObject.getInt("success");
                    String message = jsonObject.getString("message");

                    if(success == 1){
                        Toast.makeText(ProfileActivity.this,message,Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(ProfileActivity.this,message,Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                }catch (Exception e){
                    Log.i("test",e.toString());
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(ProfileActivity.this,"Some Exception",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("test",error.toString());
                progressDialog.dismiss();
                Toast.makeText(ProfileActivity.this,"Some error occured, please try again in some time.",Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();

                map.put("id",String.valueOf(registerAdmin.getId()));
                map.put("name",registerAdmin.getName());
                map.put("phone",registerAdmin.getPhone());
                map.put("email",registerAdmin.getEmail());
                map.put("birthdate",registerAdmin.getBirthdate());

                map.put("address",registerAdmin.getAddress());
                map.put("qualification",registerAdmin.getQualification());
                map.put("experience",registerAdmin.getExperience());
                map.put("password",registerAdmin.getPassword());
                Log.i("test",registerAdmin.toString());

                return map;
            }
        };
        requestQueue.add(request);

    }

    boolean isNetworkConected(){
        connectivityManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo!=null && networkInfo.isConnected());
    }
    public boolean validate() {
        boolean valid = true;
        name=eTxtName.getText().toString().trim();
        phone=eTxtPhone.getText().toString().trim();
       email=eTxtEmail.getText().toString().trim();
        birthdate=eTxtBirthdate.getText().toString().trim();
        address=eTxtAddress.getText().toString().trim();
        qualification=eTxtQualification.getText().toString().trim();
        experience=eTxtExperience.getText().toString().trim();
        password=eTxtPassword.getText().toString().trim();
        if (name.isEmpty()) {
            eTxtName.setError("Please fill this field");
            valid = false;
        } else {eTxtName.setError(null);
        }

        /*if (password.isEmpty() || password.length() < 4 || password.length() > 20) {
            passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passwordText.setError(null);
        }*/
        if(phone.isEmpty()){
            eTxtPhone.setError("Please enter phone!");
        }else{
            if(phone.length()<10){
                flag = false;
                eTxtPhone.setError("Please enter 10 digit phone number!");
            }
            if (email.isEmpty()) {
                eTxtEmail.setError("Please fill this field");
                valid = false;
            } else {eTxtEmail.setError(null);
            }
            if (birthdate.isEmpty()) {
                eTxtBirthdate.setError("Please fill this field");
                valid = false;
            } else {eTxtBirthdate.setError(null);
            }
            if (address.isEmpty()) {
                eTxtAddress.setError("Please fill this field");
                valid = false;
            } else {eTxtAddress.setError(null);
            }
            if (qualification.isEmpty()) {
                eTxtQualification.setError("Please fill this field");
                valid = false;
            } else {eTxtQualification.setError(null);
            }
            if (experience.isEmpty()) {
               eTxtExperience.setError("Please fill this field");
                valid = false;
            } else {eTxtExperience.setError(null);
            }
            if (password.isEmpty()) {
                eTxtPassword.setError("Please fill this field");
                valid = false;
            } else {eTxtPassword.setError(null);
            }
        }

        return valid;
    }


}
