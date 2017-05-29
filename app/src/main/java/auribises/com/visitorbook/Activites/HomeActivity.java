package auribises.com.visitorbook.Activites;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import auribises.com.visitorbook.MainActivity;
import auribises.com.visitorbook.R;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class HomeActivity extends AppCompatActivity {

    @InjectView(R.id.buttonAdmin)
    Button eTxtButtonAdmin;

    @InjectView(R.id.buttonTeacher)
    Button eTxtButtonTeacher;

    @InjectView(R.id.buttonGuard)
    Button eTxtButtonGuard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.inject(this);
    }

    public void onClickHandler(View view) {
        if (view.getId() == R.id.buttonTeacher) {
            Intent i = new Intent(HomeActivity.this, TeacherloginActivity.class);
            startActivity(i);
        } else {
            if (view.getId() == R.id.buttonGuard) {
                Intent i = new Intent(HomeActivity.this, GuardloginActivity.class);
                startActivity(i);
            }

            if (view.getId() == R.id.buttonAdmin) {
                Intent i = new Intent(HomeActivity.this, AdminloginActivity.class);
                startActivity(i);
            }
        }
    }
}




