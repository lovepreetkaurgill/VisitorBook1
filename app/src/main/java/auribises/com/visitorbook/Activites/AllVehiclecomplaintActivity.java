package auribises.com.visitorbook.Activites;

import android.app.ProgressDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



import auribises.com.visitorbook.Adapters.VehiclecomplaintAdapter;
import auribises.com.visitorbook.Class.Vehiclecomplaint;
import auribises.com.visitorbook.R;
import auribises.com.visitorbook.Util;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class AllVehiclecomplaintActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    @InjectView(R.id.listView)
    ListView listView;

    @InjectView(R.id.editTextSearch)
    EditText eTxtSearch;

    ArrayList<Vehiclecomplaint> vehiclecomplaintList;

    VehiclecomplaintAdapter adapter;

    Vehiclecomplaint vehiclecomplaint;
    int pos;

    RequestQueue requestQueue;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_vehicle);

        ButterKnife.inject(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);

        requestQueue = Volley.newRequestQueue(this);



        eTxtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String str = charSequence.toString();
                if(adapter!=null){
                    adapter.filter(str);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        retrieveFromCloud();
    }
    void Vehicle() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Logout ");
        builder.setMessage("Do you wish to Logout?");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Intent j= new Intent(AllVehiclecomplaintActivity.this, HomeActivity.class);
                startActivity(j);
                retrieveFromCloud();

            }

        });
        builder.setNegativeButton("Cancel", null);
        builder.create().show();
    }

    @Override
    public void onBackPressed() {
        Vehicle();
    }




    void retrieveFromCloud(){

        progressDialog.show();

        vehiclecomplaintList = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Util.RETRIEVE_VEHICLE_PHP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.i("test",response.toString());
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("vehicle");

                    int id=0;
                    String n="",p="",e="",g="",v="",vn="";
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jObj = jsonArray.getJSONObject(i);

                        id = jObj.getInt("id");
                        n = jObj.getString("Name");
                        p = jObj.getString("Phone");
                        e = jObj.getString("Email");
                        g = jObj.getString("Gender");
                        v = jObj.getString("Vehicle");
                        vn = jObj.getString("Vehiclenumber");

                        vehiclecomplaintList.add(new Vehiclecomplaint(id,n,p,e,g,v,vn));
                    }


                    adapter = new VehiclecomplaintAdapter(AllVehiclecomplaintActivity.this,R.layout.vehiclecomplaint_list_item, vehiclecomplaintList);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(AllVehiclecomplaintActivity.this);

                    progressDialog.dismiss();

                }catch (Exception e){
                    Log.i("test",e.toString());
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(AllVehiclecomplaintActivity.this,"Some Exception",Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("test",error.toString());
                progressDialog.dismiss();
                Toast.makeText(AllVehiclecomplaintActivity.this,"Some Error",Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(stringRequest); // Execute the Request
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        pos = i;
        vehiclecomplaint = vehiclecomplaintList.get(i);
        showOptions();
    }

    void showOptions(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String[] items ={"View","Update","Delete"};
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                switch (i){
                    case 0:
                        showVehicleComplaint();
                        break;

                    case 1:
                        Intent intent = new Intent(AllVehiclecomplaintActivity.this,VehicleActivity.class);
                        intent.putExtra("keyVehicle", vehiclecomplaint);
                        startActivity(intent);
                        break;

                    case 2:
                        deleteVehicleComplaint();
                        break;
                }

            }
        });


        builder.create().show();
    }

    void showVehicleComplaint(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Details of "+ vehiclecomplaint.getName());
        builder.setMessage(vehiclecomplaint.toString());
        builder.setPositiveButton("Done",null);
        builder.create().show();
    }

    void deleteVehicleComplaint(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete "+ vehiclecomplaint.getName());
        builder.setMessage("Do you wish to Delete?");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                deleteFromCloud();

            }
        });
        builder.setNegativeButton("Cancel",null);
        builder.create().show();
    }

    void deleteFromCloud(){
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, Util.DELETE_VEHICLE_PHP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try{
                    JSONObject jsonObject = new JSONObject(response);
                    int success = jsonObject.getInt("success");
                    String message = jsonObject.getString("message");

                    if(success == 1){
                        vehiclecomplaintList.remove(pos);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(AllVehiclecomplaintActivity.this,message,Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(AllVehiclecomplaintActivity.this,message,Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                }catch (Exception e){
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(AllVehiclecomplaintActivity.this,"Some Exception",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(AllVehiclecomplaintActivity.this,"Some Volley Error: "+error.getMessage(),Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("id",String.valueOf(vehiclecomplaint.getId()));
                return map;
            }
        };

        requestQueue.add(request);

    }

}
