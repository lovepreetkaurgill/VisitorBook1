package auribises.com.visitorbook.Activites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import auribises.com.visitorbook.Activites.AdminChangePasswordActivity;
import auribises.com.visitorbook.Activites.AdminForgetPasswordActivity;
import auribises.com.visitorbook.Class.AdminLogin;
import auribises.com.visitorbook.Class.Login;
import auribises.com.visitorbook.Class.RegisterAdmin;
import auribises.com.visitorbook.MainActivity;
import auribises.com.visitorbook.R;
import auribises.com.visitorbook.Util;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class AdminloginActivity extends AppCompatActivity {

    @InjectView(R.id.usernamea)
    EditText editTextname;

    @InjectView(R.id.passworda)
    EditText editTextpass;

    @InjectView(R.id.button)
    Button btn;

    @InjectView(R.id.login)
    TextView txtlogin;



    @InjectView(R.id.forgotpassworda)
    TextView txtforgetpass;

    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;

    String input_username, input_password;
    private boolean loggedIn = false;
    JSONArray jsonArray;
    JSONObject jsonObjectLog;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    AdminLogin adminlogin;
    int id = 0;
    String name,phone,email,birthdate,gender,address,qualification,experience,password;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminlogin);
        ButterKnife.inject(this);
        sharedPreferences = getSharedPreferences(Util.sharedPreferences,MODE_PRIVATE);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);

        adminlogin=new AdminLogin();
        requestQueue = Volley.newRequestQueue(this);
    }

    boolean isNetworkConnected() {

        connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    public void onClickAdmin(View view) {
        if (view.getId() == R.id.button) {
            adminlogin.setUsername(editTextname.getText().toString().trim());
            adminlogin.setPassword(editTextpass.getText().toString().trim());
            if (validation()) {
                if (isNetworkConnected()) {
                    adminlogin();
                } else
                    Toast.makeText(this, "Please connect to Internet", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Please correct Input", Toast.LENGTH_LONG).show();
            }
        } else {
            if (view.getId() == R.id.forgotpassworda) {
                Intent i = new Intent(AdminloginActivity.this, AdminForgetPasswordActivity.class);
                startActivity(i);
                finish();
            } else {
                Intent i = new Intent(AdminloginActivity.this, AdminChangePasswordActivity.class);
                startActivity(i);
                finish();
            }
        }
    }

    void adminlogin() {

        input_username = editTextname.getText().toString().trim();
        input_password = editTextpass.getText().toString().trim();


        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, Util.ADMINLOGIN_PHP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                JSONObject jsonObject;
                int i = 0;
                try {
                    Log.i("test",response.toString());
                    jsonObject = new JSONObject(response);
                    i = jsonObject.getInt("success");
                    jsonObjectLog = new JSONObject(response);
                    //jsonArray = jsonObjectLog.getJSONArray("students");
                    //editor = sharedPreferences.edit();
                    //String str = "a";
                    // editor.putString("name",str);
                    //editor.commit();
                    //String str2 = sharedPreferences.getString("name","");
                    if (i == 1) {
                        editor = sharedPreferences.edit();
                        editor.putInt("id", jsonObject.getInt("id"));
                        editor.putString("name", jsonObject.getString("name"));
                        editor.putString("phone",jsonObject.getString("phone") );
                        editor.putString("email", jsonObject.getString("email"));
                        editor.putString("birthdate",jsonObject.getString("birthdate") );
                        editor.putString("gender", jsonObject.getString("gender"));
                        editor.putString("address", jsonObject.getString("address"));
                        editor.putString("qualification", jsonObject.getString("qualification"));
                        editor.putString("experience", jsonObject.getString("experience"));
                        editor.commit();
                        Toast.makeText(AdminloginActivity.this, "Login Success!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AdminloginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(AdminloginActivity.this, "Login Failure!", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Log.i("test",e.toString());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("test",error.toString());
                progressDialog.dismiss();
                Toast.makeText(AdminloginActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();

                map.put("email", input_username);
                map.put("password", input_password);

                Log.i("username", input_username);
                Log.i("password", input_password);
                Log.i("test",adminlogin.toString());
                return map;
            }
        };
        requestQueue.add(request);
        clearFields();
    }

    void clearFields() {
        editTextname.setText("");
        editTextpass.setText("");
    }


    boolean validation() {
        boolean flag = true;
        if (adminlogin.getUsername().isEmpty()) {
            flag = false;
            editTextname.setError("Please Enter Username");
        }
        if (adminlogin.getPassword().isEmpty()) {
            flag = false;
            editTextpass.setError("Please Enter Password");
        }

        return flag;
    }
}



