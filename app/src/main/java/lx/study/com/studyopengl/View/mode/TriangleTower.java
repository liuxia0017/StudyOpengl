package lx.study.com.studyopengl.View.mode;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import lx.study.com.studyopengl.R;
import lx.study.com.studyopengl.View.mode.base.EGLMode;
import lx.study.com.studyopengl.util.MatrixState;


/**
 * Created by lx on 2017/12/13.
 */

public class TriangleTower extends EGLMode {
//    uniform mat4 uMVPMatrix;
//    attribute vec3 aPosition;
//    attribute vec2 aTexCoor;

    private int uMVPMatrix;
    private int aPosition;
    private int aTextCoor;
    public float xAngle;
    public float yAngle;
    public float zAngle;
    private int texuresID;



    public TriangleTower(Context c, String verFile, String fragFile) {
        super(c, verFile, fragFile);
    }

    @Override
    protected int[] createTextureIds() {
        int[] texures = new int[1];
        GLES20.glGenTextures(1,//产生纹理id的数量
                texures,//纹理id数组
                0);//默认o
        texuresID = texures[0];
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texuresID);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
                GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_MIRRORED_REPEAT);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
                GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_MIRRORED_REPEAT);

        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.a);
        //加载纹理
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D,//纹理类型
                0,//纹理层次，0表示基本的图像层，（可以理解为 直接贴图）
                bitmap, //纹理图像
                0);//纹理的边框尺寸
        bitmap.recycle();
        return texures;
    }



    @Override
    public void initData() {
        float [] vers = new float[]{
//                 0.1f, 1.1f, -1.5f,
//                -1.1f, -1.1f, 0f,
//                 1.1f , -1.1f, 0f,
//
//                0.1f, 1.1f, -1.5f,
//                -1.1f, -1.1f, 0f,
//                0.5f, -1.1f, -3f,
//
//                0.1f, 1.1f, -1.5f,
//                1.1f , -1.1f, 0f,
//                0.5f, -1.1f, -3f,
//
//                -1.1f, -1.1f, 0f,
//                1.1f , -1.1f, 0f,
//                0.5f, -1.1f, -3f

                0.0f,2f,0.0f,
                1f,0f,1f,
                -1f,0f,1f,

                0.0f,2f,0.0f,
                -1f,0f,-1f,
                -1f,0f,1f,

                0.0f,2f,0.0f,
                -1f,0f,-1f,
                1f,0f,-1f,

                0.0f,2f,0.0f,
                1f,0f,1f,
                1f,0f,-1f,

                1f,0f,1f,
                1f,0f,-1f,
                -1f,0f,-1f,

                1f,0f,-1f,
                -1f,0f,-1f,
                -1f,0f,-1f

        };
        mVerBuffer =  mDefualShaderUtil.getFloatBuffer(vers);

        float[] textures = new float[]{
                0.5f,0f,
                0f,1f,
                1f,1f,
                0.5f,0f,
                0f,1f,
                1f,1f,
                0.5f,0f,
                0f,1f,
                1f,1f,
                0.5f,0f,
                0f,1f,
                1f,1f,
//                0.5f,0f,
//                0f,1f,
//                1f,1f,
                };
        mTexCoorBuffer = mDefualShaderUtil.getFloatBuffer(textures);
    }

    @Override
    public void initShader() {
        uMVPMatrix = GLES20.glGetUniformLocation(mProgram,"uMVPMatrix");
        aPosition = GLES20.glGetAttribLocation(mProgram,"aPosition");
        aTextCoor = GLES20.glGetAttribLocation(mProgram,"aTexCoor");
    }

    @Override
    protected void drawSelf(int[] textureId) {
        MatrixState.pushMatrix();
        //位移操作
        MatrixState.transtate(0, 0, -3);
//        MatrixState.rotate(xAngle, 1,1, 1);
        MatrixState.rotate(yAngle, 0, 1, 0);
//        MatrixState.rotate(zAngle, 0, 0, 1);

        GLES20.glUniformMatrix4fv(uMVPMatrix,1,false,MatrixState.getFinalMatrix(),0);

        GLES20.glVertexAttribPointer(aPosition,3,GLES20.GL_FLOAT,false,0,mVerBuffer);
        GLES20.glVertexAttribPointer(aTextCoor,2,GLES20.GL_FLOAT,false,0,mTexCoorBuffer);

        GLES20.glEnableVertexAttribArray(aPosition);
        GLES20.glEnableVertexAttribArray(aTextCoor);

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId[0]);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN,0,18);
        MatrixState.popMatrix();
    }

    public void startRotat(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(startRotat){
                    xAngle+=2;
                    yAngle+=2;
                    zAngle +=2;
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                        Thread.interrupted();
                    }
                }

            }
        }).start();
    }
}
