package auribises.com.visitorbook.Activites;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Exchanger;

import auribises.com.visitorbook.MainActivity;
import auribises.com.visitorbook.R;
import auribises.com.visitorbook.Util;
import butterknife.ButterKnife;
import butterknife.InjectView;


public class AdminloginActivity extends AppCompatActivity implements View.OnClickListener{
    @InjectView(R.id.usernamea)
    EditText usernameText;

    @InjectView(R.id.passworda)
    EditText passwordText;

    @InjectView(R.id.button)
    Button loginButton;


    ContentResolver resolver;

    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    String input_username,input_password;
    private boolean loggedIn = false;
    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;

    JSONArray jsonArray;
    JSONObject jsonObjectLog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminlogin);
        ButterKnife.inject(this);
        loginButton.setOnClickListener(this);
        resolver=getContentResolver();
        requestQueue= Volley.newRequestQueue(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging In...");
        progressDialog.setCancelable(false);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if (id==R.id.button){
            if(validate()) {
                if (isNetworkConected())
                    login();
                Intent i = new Intent(AdminloginActivity.this, MainActivity.class);
                startActivity(i);
            }else
                    Toast.makeText(this, "Please connect to Internet", Toast.LENGTH_LONG).show();
            }
        }



    void login(){

        input_username = usernameText.getText().toString().trim();
        input_password = passwordText.getText().toString().trim();


        progressDialog.show();
        StringRequest request=new StringRequest(Request.Method.POST, Util.LOGIN_PHP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                int i = 0;
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    i = jsonObject.getInt("success");
                    jsonObjectLog = new JSONObject(response);
                    jsonArray = jsonObjectLog.getJSONArray("user");

                }catch (Exception e){
                    e.printStackTrace();
                }

                if(i==1){

                    SharedPreferences sharedPreferences = AdminloginActivity.this.getSharedPreferences("loginSp", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("loggedin", true);
                    editor.putString("username", input_username);
                    editor.commit();
                    Toast.makeText(getApplicationContext(), "Login Success!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AdminloginActivity.this, AdminloginActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(AdminloginActivity.this,"Login Failure!",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(AdminloginActivity.this,"Error: "+error.getMessage(),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map <String,String>map=new HashMap<>();

                map.put("phone",input_username);
                map.put("phone",input_password);

                Log.i("username",input_username);
                Log.i("password",input_password);
                return map;
            }
        };
        requestQueue.add(request);
        clearFields();
    }
    void clearFields(){
        usernameText.setText("");
        passwordText.setText("");
    }
    public   void onResume(){
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences("loginSp", Context.MODE_PRIVATE);
        loggedIn = sharedPreferences.getBoolean("loggedin",false);
        if(loggedIn){
            Intent intent = new Intent(AdminloginActivity.this,AdminloginActivity.class);
            startActivity(intent);
            finish();
        }
    }
    boolean isNetworkConected(){

        connectivityManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();


        return (networkInfo!=null && networkInfo.isConnected());

    }
    public boolean validate() {
        boolean valid = true;

        String username = usernameText.getText().toString();
        String password = passwordText.getText().toString();

        if (username.isEmpty() ) {
            usernameText.setError("enter a valid username");
            valid = false;
        } else {
            usernameText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 20) {
            passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        return valid;
    }

}
