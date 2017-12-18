package lx.study.com.studyopengl.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.Matrix;

import javax.microedition.khronos.opengles.GL10;

import lx.study.com.studyopengl.util.MatrixState;


/**
 * Created by lx on 2017/12/17.
 */

public class ImageRender extends EGLRender {
    private Bitmap mBitmap;
    private float uXY;

    public ImageRender(Context context) {
        super(context);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        int w = mBitmap.getWidth();
        int h = mBitmap.getHeight();
        float sWH = w / (float) h;
        float sWidthHeight = width / (float) height;
        uXY = sWidthHeight;
        if (sWH > sWidthHeight) {
            Matrix.orthoM(MatrixState.mProjMatrix, 0, -1, 1, -1 / sWidthHeight * sWH, 1 / sWidthHeight * sWH, 3, 5);
        } else {
            Matrix.orthoM(MatrixState.mProjMatrix, 0, -1, 1, -sWH / sWidthHeight, sWH / sWidthHeight, 3, 5);
        }
        //设置相机位置
        Matrix.setLookAtM(MatrixState.getCameraMatrix(), 0, 0, 0, 5.0f, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        //计算变换矩阵

    }


}
