package auribises.com.visitorbook.Activites;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import auribises.com.visitorbook.Class.Login;
import auribises.com.visitorbook.R;
import auribises.com.visitorbook.Util;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class TeacherloginActivity extends AppCompatActivity {

    @InjectView(R.id.usernamet)
    EditText editTextname;

    @InjectView(R.id.passwordt)
    EditText editTextpass;

    @InjectView(R.id.button)
    Button btn;

    @InjectView(R.id.forgotpasswordt)
    TextView forgotpassword;

    @InjectView(R.id.changepasswordt)
    TextView changepassword;

    @InjectView(R.id.login)
    TextView txtlogin;

    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;

    String input_username,input_password;
    private boolean loggedIn = false;
    JSONArray jsonArray;
    JSONObject jsonObjectLog;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    Login login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacherlogin);
        ButterKnife.inject(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);

        login=new Login();
        requestQueue = Volley.newRequestQueue(this);
    }

    boolean isNetworkConnected(){

        connectivityManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo!=null && networkInfo.isConnected());
    }

    public void onClickTeacher(View view) {
        if (view.getId() == R.id.button) {
            login.setUsername(editTextname.getText().toString().trim());
            login.setPassword(editTextpass.getText().toString().trim());
            if (validation()) {
                if (isNetworkConnected()) {
                    login();
                } else
                    Toast.makeText(this, "Please connect to Internet", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Please correct Input", Toast.LENGTH_LONG).show();
            }
        }else{
                if(view.getId()==R.id.forgotpasswordt){
                    Intent i = new Intent(TeacherloginActivity.this,TeacherForgetPasswordActivity.class);
                    startActivity(i);
                    //finish();
                }else{
                    Intent i = new Intent(TeacherloginActivity.this,TeacherChangePasswordActivity.class);
                    startActivity(i);
                    //finish();

                }
        }
    }

    void login(){

        input_username = editTextname.getText().toString().trim();
        input_password = editTextpass.getText().toString().trim();

        progressDialog.show();
        StringRequest request=new StringRequest(Request.Method.POST, Util.TEACHERLOGIN_PHP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                int i = 0;
                try {
                    Log.i("test",response.toString());
                    JSONObject jsonObject = new JSONObject(response);
                    i = jsonObject.getInt("success");
                    jsonObjectLog = new JSONObject(response);
                    jsonArray = jsonObjectLog.getJSONArray("user");

                }catch (Exception e){
                    Log.i("test",e.toString());
                    e.printStackTrace();
                }

                if(i==1){

                    SharedPreferences sharedPreferences = TeacherloginActivity.this.getSharedPreferences("loginSp", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("loggedin", true);
                    editor.putString("username", input_username);
                    editor.commit();
                    Toast.makeText(getApplicationContext(),"Login Success!",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(TeacherloginActivity.this, TeacherhomeActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(TeacherloginActivity.this,"Login Failure!",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("test",error.toString());
                progressDialog.dismiss();
                Toast.makeText(TeacherloginActivity.this,"Error: "+error.getMessage(),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map <String,String>map=new HashMap<>();

                map.put("email",input_username);
                map.put("password",input_password);

                Log.i("username",input_username);
                Log.i("password",input_password);
                return map;
            }
        };
        requestQueue.add(request);
        clearFields();
    }

    void clearFields(){
        editTextname.setText("");
        editTextpass.setText("");
    }

    boolean  validation(){
        boolean flag =true;
        if(login.getUsername().isEmpty()){
            flag=false;
            editTextname.setError("Please Enter Username");
        }
        if(login.getPassword().isEmpty()  ){
            flag=false;
            editTextpass.setError("Please Enter Password");
        }

        return flag;
    }
}
