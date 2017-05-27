package auribises.com.visitorbook.Activites;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import auribises.com.visitorbook.R;

public class TeacherhomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacherhome);
    }
    public void onClickHandler(View view) {
        if(view.getId()==R.id.buttonTeacher1) {
            Intent i = new Intent(TeacherhomeActivity.this,TeacherActivity.class);
            startActivity(i);
        }else {
            if (view.getId() == R.id.buttonTeacher_visitors) {
                Intent i = new Intent(TeacherhomeActivity.this, Teacher1Activity.class);
                startActivity(i);
            }
        }}}