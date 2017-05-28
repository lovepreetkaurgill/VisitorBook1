package auribises.com.visitorbook.Activites;

import android.app.ProgressDialog;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import auribises.com.visitorbook.Class.Vehiclecomplaint;
import auribises.com.visitorbook.R;
import auribises.com.visitorbook.Util;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class VehiclecomplaintActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{

    @InjectView(R.id.editTextName)
    EditText eTxtName;

    @InjectView(R.id.editTextPhone)
    EditText eTxtPhone;

    @InjectView(R.id.editTextEmail)
    EditText eTxtEmail;

    @InjectView(R.id.radioButtonMale)
    RadioButton rbMale;

    @InjectView(R.id.radioButtonFemale)
    RadioButton rbFemale;

    @InjectView(R.id.editTextVehicle)
    EditText eTxtVehicle;

    @InjectView(R.id.editTextVehicleNumber)
    EditText eTxtVehicleNumber;

    @InjectView(R.id.buttonSubmit)
    Button btnSubmit;

    Vehiclecomplaint vehiclecomplaint, rcvVehiclecomplaint;


    boolean updateMode;

    RequestQueue requestQueue;

    ProgressDialog progressDialog;

    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehiclecomplaint);

        ButterKnife.inject(this);

        preferences = getSharedPreferences(Util.PREFS_NAME,MODE_PRIVATE);
        editor = preferences.edit();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);

        vehiclecomplaint = new Vehiclecomplaint();

        rbMale.setOnCheckedChangeListener(this);
        rbFemale.setOnCheckedChangeListener(this);


        requestQueue = Volley.newRequestQueue(this);

        Intent rcv = getIntent();
        updateMode = rcv.hasExtra("keyVehicle");


        if(updateMode){
            rcvVehiclecomplaint = (Vehiclecomplaint) rcv.getSerializableExtra("keyVehicle");
            eTxtName.setText(rcvVehiclecomplaint.getName());
            eTxtPhone.setText(rcvVehiclecomplaint.getPhone());
            eTxtEmail.setText(rcvVehiclecomplaint.getEmail());
            eTxtVehicle.setText(rcvVehiclecomplaint.getVehicle());
            eTxtVehicleNumber.setText(rcvVehiclecomplaint.getVehiclenumber());

            if(rcvVehiclecomplaint.getGender().equals("Male")){
                rbMale.setChecked(true);
            }else{
                rbFemale.setChecked(true);
            }


            btnSubmit.setText("Update");
        }
    }

    boolean isNetworkConected(){

        connectivityManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();

        Log.i("insertIntoCloud",vehiclecomplaint.toString());
        return (networkInfo!=null && networkInfo.isConnected());

    }

    public void clickHandler(View view){
        if(view.getId() == R.id.buttonSubmit){
            insertIntoCloud();
            vehiclecomplaint.setName(eTxtName.getText().toString().trim());
            vehiclecomplaint.setPhone(eTxtPhone.getText().toString().trim());
            vehiclecomplaint.setEmail(eTxtEmail.getText().toString().trim());
            vehiclecomplaint.setVehicle(eTxtVehicle.getText().toString().trim());
            vehiclecomplaint.setVehiclenumber(eTxtVehicleNumber.getText().toString().trim());

            if(validateFields()) {
                if (isNetworkConected())
                    insertIntoCloud();
//                Intent i = new Intent(VehicleActivity.this, AllVehicleActivity.class);
//                startActivity(i);
            } else
                Toast.makeText(this, "Please connect to Internet", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this, "Please correct Input", Toast.LENGTH_LONG).show();
        }
    }


    void insertIntoCloud(){

        String url="";

        if(!updateMode){
            url = Util.INSERT_VEHICLE_PHP;
        }else{
            url = Util.UPDATE_VEHICLE_PHP;
        }

        progressDialog.show();



        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("test",response.toString());
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    int success = jsonObject.getInt("success");
                    String message = jsonObject.getString("message");

                    if(success == 1){
                        Toast.makeText(VehiclecomplaintActivity.this,message,Toast.LENGTH_LONG).show();

                        if(!updateMode){

                            editor.putString(Util.KEY_NAME, vehiclecomplaint.getName());
                            editor.putString(Util.KEY_PHONE, vehiclecomplaint.getPhone());
                            editor.putString(Util.KEY_EMAIL, vehiclecomplaint.getEmail());
                            editor.putString(Util.KEY_VEHICLE, vehiclecomplaint.getVehicle());
                            editor.putString(Util.KEY_VEHICLENUMBER, vehiclecomplaint.getVehiclenumber());

                            editor.commit();

//                            Intent home = new Intent(VehicleActivity.this,VehicleActivity.class);
//                            startActivity(home);
//                            finish();
                        }

                        if(updateMode)
                            finish();

                    }else{
                        Toast.makeText(VehiclecomplaintActivity.this,message,Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                }catch (Exception e){
                    e.printStackTrace();
                    Log.i("test",e.toString());
                    progressDialog.dismiss();
                    Toast.makeText(VehiclecomplaintActivity.this,"Some Exception",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.i("test",error.toString());
                Toast.makeText(VehiclecomplaintActivity.this,"Some Error"+error.getMessage(),Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                Log.i("test",vehiclecomplaint.toString());
                if(updateMode)
                    map.put("id",String.valueOf(rcvVehiclecomplaint.getId()));

                map.put("name", vehiclecomplaint.getName());
                map.put("phone", vehiclecomplaint.getPhone());
                map.put("email", vehiclecomplaint.getEmail());
                map.put("gender", vehiclecomplaint.getGender());
                map.put("vehicle", vehiclecomplaint.getVehicle());
                map.put("vehiclenumber", vehiclecomplaint.getVehiclenumber());

                return map;
            }
        };

        requestQueue.add(request);

        clearFields();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        int id = compoundButton.getId();

        if(b) {
            if (id == R.id.radioButtonMale) {
                vehiclecomplaint.setGender("Male");
            } else {
                vehiclecomplaint.setGender("Female");
            }
        }
    }



    void clearFields(){
        eTxtName.setText("");
        eTxtEmail.setText("");
        eTxtPhone.setText("");
        rbMale.setChecked(false);
        rbFemale.setChecked(false);
        eTxtVehicle.setText("");
        eTxtVehicleNumber.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(0,101,0,"All Vehicles");


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == 101){
            Intent i = new Intent(VehiclecomplaintActivity.this,AllVehicleActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    boolean validateFields(){
        boolean flag = true;

        if(vehiclecomplaint.getName().isEmpty()){
            flag = false;
            eTxtName.setError("Please Enter Name");
        }

        if(vehiclecomplaint.getPhone().isEmpty()){
            flag = false;
            eTxtPhone.setError("Please Enter Phone");
        }else{
            if(vehiclecomplaint.getPhone().length()<10){
                flag = false;
                eTxtPhone.setError("Please Enter 10 digits Phone Number");
            }
        }

        if(vehiclecomplaint.getEmail().isEmpty()){
            flag = false;
            eTxtEmail.setError("Please Enter Email");
        }else{
            if(!(vehiclecomplaint.getEmail().contains("@") && vehiclecomplaint.getEmail().contains("."))){
                flag = false;
                eTxtEmail.setError("Please Enter correct Email");
            }
        }
        if(vehiclecomplaint.getVehicle().isEmpty()){
            flag = false;
            eTxtVehicle.setError("Please Enter correct Vehicle");
        }
        if(vehiclecomplaint.getVehiclenumber().isEmpty()){
            flag = false;
            eTxtVehicleNumber.setError("Please Enter correct VehicleNumber");
        }


        return flag;

    }
}
