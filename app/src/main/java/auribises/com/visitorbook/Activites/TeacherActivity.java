package auribises.com.visitorbook.Activites;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import auribises.com.visitorbook.Class.Teacher;
import auribises.com.visitorbook.R;
import auribises.com.visitorbook.Util;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class TeacherActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{

    @InjectView(R.id.editTextName)
    EditText eTxtName;

    @InjectView(R.id.editTextPhone)
    EditText eTxtPhone;

    @InjectView(R.id.editTextEmail)
    EditText eTxtEmail;

    @InjectView(R.id.editTextAddress)
    EditText eTxtAddress;

    @InjectView(R.id.editTextPurpose)
    EditText eTxtPurpose;

    @InjectView(R.id.editTextDate)
    EditText eTxtDate;

    @InjectView(R.id.editTextTime)
    EditText eTxtTime;

    @InjectView(R.id.editTextRoom)
    EditText eTxtRoom;

    @InjectView(R.id.radioButtonMale)
    RadioButton rbMale;

    @InjectView(R.id.radioButtonFemale)
    RadioButton rbFemale;

    ArrayAdapter<String> adapter;

    @InjectView(R.id.buttonAppointment)
    Button btnSubmit;

    Teacher teacher,rcvTeacher;

    ContentResolver resolver;

    boolean updateMode;

    RequestQueue requestQueue;

    ProgressDialog progressDialog;

    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;

    DatePickerDialog datePickerDialog;

    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

            eTxtDate.setText(i+"/"+(i1+1)+"/"+i2);

        }
    };

    TimePickerDialog timePickerDialog;
    TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i1) {
            eTxtTime.setText(i+" : "+i1);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        ButterKnife.inject(this);
        eTxtName = (EditText)findViewById(R.id.editTextName);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);

        teacher = new Teacher();

        rbMale.setOnCheckedChangeListener(this);
        rbFemale.setOnCheckedChangeListener(this);

        resolver = getContentResolver();

        requestQueue = Volley.newRequestQueue(this);

        Intent rcv = getIntent();
        updateMode = rcv.hasExtra("keyTeacher");


        if(updateMode){
            rcvTeacher = (Teacher) rcv.getSerializableExtra("keyTeacher");
            eTxtName.setText(rcvTeacher.getName());
            eTxtPhone.setText(rcvTeacher.getPhone());
            eTxtEmail.setText(rcvTeacher.getEmail());
            eTxtAddress.setText(rcvTeacher.getAddress());
            eTxtPurpose.setText(rcvTeacher.getPurpose());
            eTxtDate.setText(rcvTeacher.getDate());
            eTxtTime.setText(rcvTeacher.getTime());
            eTxtRoom.setText(rcvTeacher.getRoom());

            if(rcvTeacher.getGender().equals("Male")){
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
        Log.i("insertIntoCloud",teacher.toString() );
        return (networkInfo!=null && networkInfo.isConnected());

    }

    public void clickHandler(View view){
        if(view.getId() == R.id.buttonAppointment){

                teacher.setName(eTxtName.getText().toString().trim());
                teacher.setPhone(eTxtPhone.getText().toString().trim());
                teacher.setEmail(eTxtEmail.getText().toString().trim());
                teacher.setAddress(eTxtAddress.getText().toString().trim());
                teacher.setPurpose(eTxtPurpose.getText().toString().trim());
                teacher.setDate(eTxtDate.getText().toString().trim());
                teacher.setTime(eTxtTime.getText().toString().trim());
                teacher.setRoom(eTxtRoom.getText().toString().trim());

            if(validateFields()) {
                if (isNetworkConected())
                    insertIntoCloud();

            }else
                    Toast.makeText(this, "Please connect to Internet", Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(this, "Please correct Input", Toast.LENGTH_LONG).show();
            }
        }


    void insertIntoCloud(){

        String url="";

        if(!updateMode){
            url = Util.INSERT_TEACHER_PHP;
        }else{
            url = Util.UPDATE_TEACHER_PHP;
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
                        Toast.makeText(TeacherActivity.this,message,Toast.LENGTH_LONG).show();

                        if(updateMode){
                            finish();
                        }


                    }else{
                        Toast.makeText(TeacherActivity.this,message,Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                }catch (Exception e){
                    e.printStackTrace();
                    Log.i("test",e.toString());
                    progressDialog.dismiss();
                    Toast.makeText(TeacherActivity.this,"Some Exception",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.i("test",error.toString());
                Toast.makeText(TeacherActivity.this,"Some Error"+error.getMessage(),Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
//                Log.i("test",teacher.toString()+"\n"+rcvTeacher.toString() );
                if(updateMode)
                    map.put("id",String.valueOf(rcvTeacher.getId()));
                map.put("name",teacher.getName());
                map.put("phone",teacher.getPhone());
                map.put("email",teacher.getEmail());
                map.put("gender",teacher.getGender());
                map.put("address",teacher.getAddress());
                map.put("purpose",teacher.getPurpose());
                map.put("date",teacher.getDate());
                map.put("time",teacher.getTime());
                map.put("room",teacher.getRoom());
                return map;
            }
        };

        requestQueue.add(request); // execute the request, send it ti server

        clearFields();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        int id = compoundButton.getId();

        if(b) {
            if (id == R.id.radioButtonMale) {
                teacher.setGender("Male");
            } else {
                teacher.setGender("Female");
            }
        }
    }

    void insertIntoDB(){

        ContentValues values = new ContentValues();

        values.put(Util.COL_NAMETEACHER,teacher.getName());
        values.put(Util.COL_PHONETEACHER,teacher.getPhone());
        values.put(Util.COL_EMAILTEACHER,teacher.getEmail());
        values.put(Util.COL_GENDERTEACHER,teacher.getGender());
        values.put(Util.COL_ADDRESSTEACHER,teacher.getAddress());
        values.put(Util.COL_PURPOSETEACHER,teacher.getPurpose());
        values.put(Util.COL_DATETEACHER,teacher.getDate());
        values.put(Util.COL_TIMETEACHER,teacher.getTime());
        values.put(Util.COL_ROOMTEACHER,teacher.getRoom());

        if(!updateMode){
            Uri dummy = resolver.insert(Util.TEACHER_URI,values);
            Toast.makeText(this,teacher.getName()+ " Registered Successfully "+dummy.getLastPathSegment(),Toast.LENGTH_LONG).show();

            Log.i("Insert",teacher.toString());

            clearFields();
        }else{
            String where = Util.COL_IDTEACHER + " = "+rcvTeacher.getId();
            int i = resolver.update(Util.TEACHER_URI,values,where,null);
            if(i>0){
                Toast.makeText(this,"Updation Successful",Toast.LENGTH_LONG).show();
                finish();
            }
        }


    }

    void clearFields(){
        eTxtName.setText("");
        eTxtPhone.setText("");
        eTxtEmail.setText("");
        eTxtAddress.setText("");
        eTxtPurpose.setText("");
        eTxtDate.setText("");
        eTxtTime.setText("");
        eTxtRoom.setText("");
        rbMale.setChecked(false);
        rbFemale.setChecked(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(0,101,0,"All Visitors");


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == 101){
            Intent i = new Intent(TeacherActivity.this,AllTeachersActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    boolean validateFields(){
        boolean flag = true;

        if(teacher.getName().isEmpty()){
            flag = false;
            eTxtName.setError("Please Enter Name");
        }

        if(teacher.getPhone().isEmpty()){
            flag = false;
            eTxtPhone.setError("Please Enter Phone");
        }else{
            if(teacher.getPhone().length()<10){
                flag = false;
                eTxtPhone.setError("Please Enter 10 digits Phone Number");
            }
        }

        if(teacher.getEmail().isEmpty()){
            flag = false;
            eTxtEmail.setError("Please Enter Email");
        }else{
            if(!(teacher.getEmail().contains("@") && teacher.getEmail().contains("."))){
                flag = false;
                eTxtEmail.setError("Please Enter correct Email");
            }
        }

        return flag;

    }

    public void showDatePicker(View view){

        Calendar calendar = Calendar.getInstance();
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        int mm = calendar.get(Calendar.MONTH);
        int yy = calendar.get(Calendar.YEAR);

        datePickerDialog = new DatePickerDialog(this,dateSetListener,yy,mm,dd);
        datePickerDialog.show();

    }

    public void showTimePicker(View view){
        Calendar calendar = Calendar.getInstance();
        int hh = calendar.get(Calendar.HOUR_OF_DAY);
        int mm = calendar.get(Calendar.MINUTE);

        timePickerDialog = new TimePickerDialog(this,timeSetListener,hh,mm,true);
        timePickerDialog.show();
    }
}


