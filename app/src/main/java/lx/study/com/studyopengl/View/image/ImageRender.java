package lx.study.com.studyopengl.View.image;

import android.graphics.Bitmap;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by lx on 2017/12/18.
 */

public class ImageRender implements GLSurfaceView.Renderer{
    private ImageBaseRender imageBaseRender;

    public ImageRender(ImageBaseRender imageBaseRender){
        this.imageBaseRender = imageBaseRender;
    }

    public void setImageRender(ImageBaseRender render){
        this.imageBaseRender = render;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        imageBaseRender.onSurfaceCreated(gl,config);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

        imageBaseRender.onSurfaceChanged(gl,width,height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        imageBaseRender.onDrawFrame(gl);
    }

    public void setImage(Bitmap image) {
        imageBaseRender.setBitmap(image);
    }
}
