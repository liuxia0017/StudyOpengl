package lx.study.com.studyopengl.aty;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.File;

import lx.study.com.studyopengl.R;


public class MainActivity extends AppCompatActivity {


    private final int takePhoto  =111;
    private final int chooicePhoto  =222;
    private String fileName;
    public String filePath;

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


                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setItems(new String[]{"拍照", "从图库选择"}, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(which==0){
                                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                                    String sdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
                                    filePath = sdPath+"/"+"img";
                                    File f = new File(filePath);
                                    if(f.exists()){
                                        if(!f.isDirectory()){
                                            f.delete();
                                            f.mkdirs();
                                        }
                                    }else{
                                        f.mkdirs();
                                    }
                                    fileName = "img"+System.currentTimeMillis()+".jpg";
                                    intent.putExtra(MediaStore.EXTRA_OUTPUT,
                                                       Uri.fromFile(new File(filePath, fileName)));
                                    startActivityForResult(intent, takePhoto);
                                }else if(which == 1){
                                    Intent intent = new Intent();

                                    intent.setType("image/*");

                                    intent.setAction(Intent.ACTION_GET_CONTENT);

                                    startActivityForResult(intent, chooicePhoto);
                                }
                            }
                        })
                        .setCancelable(true)
                        .setTitle("选择图片")
                        .show();

                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Intent it = null;
        Bitmap bm = null;
        Bundle b = null;
        Uri uri = null;
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case takePhoto:
                    File f = new File(filePath+"/"+fileName);
                    uri =Uri.fromFile(f);
                    break;
                case chooicePhoto:
                    uri = data.getData();
//                    Log.e("uri", uri.toString());
//
                    break;
            }
            it = new Intent(this,ImageProcessActivity.class);
            it.putExtra("uri",uri.toString());
            startActivity(it);
        }
    }

}
