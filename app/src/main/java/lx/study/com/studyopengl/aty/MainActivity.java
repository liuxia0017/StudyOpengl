package lx.study.com.studyopengl.aty;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import lx.study.com.studyopengl.R;


public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void myClick(View view){
        switch (view.getId()){
            case R.id.btn_1:

                startActivity(new Intent(this,PyramidsActivity.class));

                break;
            case R.id.btn_2:
                startActivity(new Intent(this,EarthActivity.class));

                break;
            case R.id.btn_3:
                startActivity(new Intent(this,ImageProcessActivity.class));

                break;

        }
    }
}
