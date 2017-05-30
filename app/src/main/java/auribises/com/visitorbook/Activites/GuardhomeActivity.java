package auribises.com.visitorbook.Activites;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import auribises.com.visitorbook.R;
import butterknife.InjectView;

public class GuardhomeActivity extends AppCompatActivity {

    @InjectView(R.id.buttonAdminEntryGuard)
    Button eTxtAdmin;

    @InjectView(R.id.buttonTeacherEntryGuard)
    Button eTxtTeacher;

    @InjectView(R.id.buttonVehicle)
    Button eTxtVehicle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardhome);
    }

    public void onClickHandler(View view) {
        if (view.getId() == R.id.buttonAdminEntryGuard) {
            Intent i = new Intent(GuardhomeActivity.this, AdminEntryActivity.class);
            startActivity(i);
        } else {
            if (view.getId() == R.id.buttonTeacherEntryGuard) {
                Intent i = new Intent(GuardhomeActivity.this, VisitorEntryActivity.class);
                startActivity(i);
            }

            if (view.getId() == R.id.buttonVehicle) {
                Intent i = new Intent(GuardhomeActivity.this, VehicleActivity.class);
                startActivity(i);
            }
        }
    }

}