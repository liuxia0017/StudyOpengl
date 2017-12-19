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
import android.view.Menu;
import android.view.MenuItem;

import lx.study.com.studyopengl.View.image.GrayImageRender;
import lx.study.com.studyopengl.View.image.ImageBaseRender;
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem menuItem1 = menu.add(0, 0, 0, "只处理一半");
        menuItem1.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(0, 1, 1, "原图");
        menu.add(0, 2, 1, "灰度");

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 0:   mGlView.setHalf(!mGlView.getIsHalf());  break;
            case 1:   mGlView.setImageRender(new ImageBaseRender(this,"imageprocess/ver.glsl","imageprocess/frag.glsl") {
                @Override
                protected void onDrawCreatedSet(int mProgram) {

                }

                @Override
                protected void onDrawSet() {

                }
            });   break;
            case 2:   mGlView.setImageRender(new GrayImageRender(this));   break;
        }
        return super.onOptionsItemSelected(item);
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
