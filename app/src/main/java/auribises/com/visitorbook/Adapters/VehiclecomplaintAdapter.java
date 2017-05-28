package auribises.com.visitorbook.Adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import auribises.com.visitorbook.Class.Vehicle;
import auribises.com.visitorbook.Class.Vehiclecomplaint;
import auribises.com.visitorbook.R;


public class VehiclecomplaintAdapter extends ArrayAdapter<Vehiclecomplaint> {

    Context context;
    int resource;
    ArrayList<Vehiclecomplaint> vehiclecomplaintList,tempList;

    public VehiclecomplaintAdapter(Context context, int resource, ArrayList<Vehiclecomplaint> objects) {
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
        vehiclecomplaintList = objects;
        tempList = new ArrayList<>();
        tempList.addAll(vehiclecomplaintList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(resource,parent,false);

        TextView txtName = (TextView)view.findViewById(R.id.textViewName);
        TextView txtGender = (TextView)view.findViewById(R.id.textViewGender);

        Vehiclecomplaint vehiclecomplaint = vehiclecomplaintList.get(position);
        txtName.setText(vehiclecomplaint.getName());
        txtGender.setText(String.valueOf(vehiclecomplaint.getId()));

        Log.i("Test", vehiclecomplaint.toString());

        return view;
    }

    public void filter(String str){

        vehiclecomplaintList.clear();

        if(str.length()==0){
            vehiclecomplaintList.addAll(tempList);
        }else{
            for(Vehiclecomplaint s : tempList){
                if(s.getName().toLowerCase().contains(str.toLowerCase())){
                    vehiclecomplaintList.add(s);
                }
            }
        }

        notifyDataSetChanged();
    }
}

