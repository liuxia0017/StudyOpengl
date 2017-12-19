package lx.study.com.studyopengl.aty;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import lx.study.com.studyopengl.View.image.ImageGLView;


/**
 * Created by lx on 2017/12/17.
 */

public class ImageProcessActivity extends AppCompatActivity {

    private ImageGLView mGlView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if(intent!=null){
            String uriStr = intent .getStringExtra("uri");
            Uri uri = Uri.parse(uriStr);
            ContentResolver cr = this.getContentResolver();
            try {
                Bitmap  bm= BitmapFactory.decodeStream(cr.openInputStream(uri));

                mGlView = new ImageGLView(this,bm);

                setContentView(mGlView);
             } catch (Exception e) {
                        Log.e("Exception", e.getMessage(),e);
             }

        }
    }



    @Override
    protected void onResume() {
        super.onResume();
        mGlView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
            mGlView.onPause();
    }
}
