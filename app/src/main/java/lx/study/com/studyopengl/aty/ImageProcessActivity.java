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

import lx.study.com.studyopengl.View.image.ColorImageRender;
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
        menu.add(0, 3, 1, "冷色调");
        menu.add(0, 4, 1, "暖色调");
        menu.add(0, 5, 1, "模糊");
        menu.add(0, 6, 1, "马赛克");
        menu.add(0, 7, 1, "放大");
        menu.add(0, 8, 1, "缩小");
        menu.add(0, 9, 1, "扭曲");
        menu.add(0, 10, 1, "旋转");
        menu.add(0, 11, 1, "浮雕");
        menu.add(0, 12, 1, "对比度");
        menu.add(0, 13, 1, "膨胀");
        menu.add(0, 14, 1, "腐蚀");

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 0:
                mGlView.setHalf(!mGlView.getIsHalf());
                item.setTitle(mGlView.getIsHalf()?"处理全部":"处理一半");
                break;
            case 1:   mGlView.setImageRender(new ColorImageRender(this,0));   break;
            case 2:   mGlView.setImageRender(new ColorImageRender(this,1));   break;
            case 3:   mGlView.setImageRender(new ColorImageRender(this,2));   break;
            case 4:   mGlView.setImageRender(new ColorImageRender(this,3));   break;
            case 5:   mGlView.setImageRender(new ColorImageRender(this,4));   break;
            case 6:   mGlView.setImageRender(new ColorImageRender(this,5));   break;
            case 7:   mGlView.setImageRender(new ColorImageRender(this,6));   break;
            case 8:   mGlView.setImageRender(new ColorImageRender(this,7));   break;
            case 9:   mGlView.setImageRender(new ColorImageRender(this,8));   break;//扭曲
            case 10:  mGlView.setImageRender(new ColorImageRender(this,9));   break;
            case 11:  mGlView.setImageRender(new ColorImageRender(this,10));   break;
            case 12:  mGlView.setImageRender(new ColorImageRender(this,11));   break;
            case 13:  mGlView.setImageRender(new ColorImageRender(this,12));   break;
            case 14:  mGlView.setImageRender(new ColorImageRender(this,13));   break;


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
