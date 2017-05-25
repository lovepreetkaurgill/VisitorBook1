package auribises.com.visitorbook.Activites;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import auribises.com.visitorbook.R;
import butterknife.InjectView;

public class GuardhomeActivity extends AppCompatActivity {
@InjectView(R.id.buttonVisitorEntryGuard)
    Button eTxtVisitor;

    @InjectView(R.id.buttonVehicle)
    Button eTxtVehicle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardhome);
    }

    public void onClickHandler(View view) {
        if(view.getId()==R.id.buttonVisitorEntryGuard) {
            Intent i = new Intent(GuardhomeActivity.this,VisitorEntryActivity.class);
            startActivity(i);
        }else{
            if(view.getId()==R.id.buttonVehicle){
                Intent i = new Intent(GuardhomeActivity.this,VehicleActivity.class);
                startActivity(i);
            }
        }
    }
}

