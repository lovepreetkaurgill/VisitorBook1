package auribises.com.visitorbook.Activites;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import auribises.com.visitorbook.R;

public class AdminhomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminhome);
    }
    public void onClickHandler(View view) {
        if (view.getId() == R.id.buttonRegisteradminprofile) {
            Intent i = new Intent(AdminhomeActivity.this,RegisterAdminActivity.class);
            startActivity(i);
        } else {
            if (view.getId() == R.id.buttonUpdateadminprofile) {
                Intent i = new Intent(AdminhomeActivity.this, AdminhomeActivity.class);
                startActivity(i);
            }

            if (view.getId() == R.id.buttonAdminappointment) {
                Intent i = new Intent(AdminhomeActivity.this,AdminappointmentActivity.class);
                startActivity(i);
            }
        }
    }




}
