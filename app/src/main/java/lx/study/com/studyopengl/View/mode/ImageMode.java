package lx.study.com.studyopengl.View.mode;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.util.Log;

import java.io.IOException;

import lx.study.com.studyopengl.R;
import lx.study.com.studyopengl.View.mode.base.EGLMode;
import lx.study.com.studyopengl.util.MatrixState;


/**
 * Created by lx on 2017/12/17.
 */

public class ImageMode extends EGLMode {

    private int mMVPMatrixHandler;
    private int mPositionHandler;
//    private int mColorHandler;
    private int mTexCoorHandler;
    private int mSamplerHandler;


    public ImageMode(Context c, String verFile, String fragFile) {
        super(c, verFile, fragFile);

    }


    @Override
    public void initData() {
       float r= (float) 0.5625;
       float w = 1.8f;
       float h = w/r;
        float[] vers = {
                -w,h,0,
                w,h,0,
                -w,-h,0,
                w,-h,0
        };
        mVerCount = 4;
        mVerBuffer = mDefualShaderUtil.getFloatBuffer(vers);

        float [] textures = {
                0,0,
                1,0,
                0,1,
                1,1

        };
        mTexCoorBuffer = mDefualShaderUtil.getFloatBuffer(textures);
    }

    @Override
    public void initShader() {
        mMVPMatrixHandler = GLES20.glGetUniformLocation(mProgram,"uMVPMatrix");
        mPositionHandler = GLES20.glGetAttribLocation(mProgram,"aPosition");
        mTexCoorHandler = GLES20.glGetAttribLocation(mProgram,"aTexCoor");
        mSamplerHandler =GLES20.glGetUniformLocation(mProgram,"sTexture");
    }

    @Override
    protected int[] createTextureIds() {
        int[] ids ={ initTextureId(R.drawable.girl)};
        return ids;
    }

    @Override
    protected void drawSelf(int[] textureId) {
        MatrixState.pushMatrix();

        GLES20.glUniformMatrix4fv(mMVPMatrixHandler,1,false,MatrixState.getFinalMatrix(),0);

        GLES20.glVertexAttribPointer(mPositionHandler,3,GLES20.GL_FLOAT,false,0,mVerBuffer);
        GLES20.glVertexAttribPointer(mTexCoorHandler,2,GLES20.GL_FLOAT,false,0,mTexCoorBuffer);

        GLES20.glEnableVertexAttribArray(mPositionHandler);
        GLES20.glEnableVertexAttribArray(mTexCoorHandler);

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,textureId[0]);
        GLES20.glUniform1i(mSamplerHandler,0);


        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP,0,mVerCount);

        MatrixState.popMatrix();

    }
}
