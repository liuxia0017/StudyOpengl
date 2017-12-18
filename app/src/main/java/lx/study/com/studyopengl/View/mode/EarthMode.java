package lx.study.com.studyopengl.View.mode;

import android.content.Context;
import android.opengl.GLES20;
import android.util.Log;

import java.util.ArrayList;

import lx.study.com.studyopengl.R;
import lx.study.com.studyopengl.View.mode.base.EGLMode;
import lx.study.com.studyopengl.util.MatrixState;

/**
 * Created by lx on 2017/12/16.
 */

public class EarthMode extends EGLMode {
     private int mMVPMatrixHandler;
//    uniform mat4 uMVPMatrix;
    private int mMMatrixHandler;
//    uniform mat4 uMMarix;
    private int mCameraMatrixHandler;
//    uniform vec3  uCamera; //相机位置
    private int mLightLocationHandler;
//    uniform vec3 uLightLocation;//光位置
    private int mNormorlHandler;
//    attribute vec3 aNormal;//法向量
    private int mPostionHandler;
//    attribute vec3 aPostion;//顶点位置
    private int mTextureCoordHandler;
//    attribute vec2 aTexCoor;//顶点纹理坐标
//uniform sampler2D sTextureDay;//纹理内容数据
    private int mTextureDay;
//    uniform sampler2D sTextureNight;//纹理内容数据
    private int mTextureNight;


    private int eAngle;

    public EarthMode(Context c, String verFile, String fragFile) {
        super(c, verFile, fragFile);
    }

    @Override
    public void initData() {
        ArrayList<Float> pos = new ArrayList<>();
                final float UNIT_SIZE = 0.5f;
                final float angleSpan = 2f;//将球进行单位切分的角度
                float r =2.0f;

                for (int vAngle = 90; vAngle >-90 ; vAngle-=angleSpan) { //垂直面   xY
                    for (int hAngle = 360; hAngle >0 ; hAngle-=angleSpan) {  //横面  xZ

                        //纵向横向各到一个角度后计算对应的此点在球面上的坐标
                        double xozLength = r * UNIT_SIZE * Math.cos(Math.toRadians(vAngle));
                        float x1 = (float) (xozLength * Math.cos(Math.toRadians(hAngle)));
                        float z1 = (float) (xozLength * Math.sin(Math.toRadians(hAngle)));
                        float y1 = (float) (r * UNIT_SIZE * Math.sin(Math.toRadians(vAngle)));

                        xozLength = r * UNIT_SIZE * Math.cos(Math.toRadians(vAngle - angleSpan));
                        float x2 = (float) (xozLength * Math.cos(Math.toRadians(hAngle)));
                        float z2 = (float) (xozLength * Math.sin(Math.toRadians(hAngle)));
                        float y2 = (float) (r * UNIT_SIZE * Math.sin(Math.toRadians(vAngle - angleSpan)));

                        xozLength = r * UNIT_SIZE * Math.cos(Math.toRadians(vAngle - angleSpan));
                        float x3 = (float) (xozLength * Math.cos(Math.toRadians(hAngle - angleSpan)));
                        float z3 = (float) (xozLength * Math.sin(Math.toRadians(hAngle - angleSpan)));
                        float y3 = (float) (r * UNIT_SIZE * Math.sin(Math.toRadians(vAngle - angleSpan)));

                        xozLength = r * UNIT_SIZE * Math.cos(Math.toRadians(vAngle));
                        float x4 = (float) (xozLength * Math.cos(Math.toRadians(hAngle - angleSpan)));
                        float z4 = (float) (xozLength * Math.sin(Math.toRadians(hAngle - angleSpan)));
                        float y4 = (float) (r * UNIT_SIZE * Math.sin(Math.toRadians(vAngle)));
                        //构建第一三角形
                        pos.add(x1);
                        pos.add(y1);
                        pos.add(z1);

                        pos.add(x2);
                        pos.add(y2);
                        pos.add(z2);

                        pos.add(x4);
                        pos.add(y4);
                        pos.add(z4);

                        pos.add(x4);
                        pos.add(y4);
                        pos.add(z4);

                        pos.add(x2);
                        pos.add(y2);
                        pos.add(z2);

                        pos.add(x3);
                        pos.add(y3);
                        pos.add(z3);
            }
        }
        mVerCount = pos.size()/3;
        Log.e("earth","vCount="+mVerCount);
        float [] vers = new float[pos.size()];
        for (int j = 0; j <pos.size() ; j++) {
            vers[j] = pos.get(j);
        }
        mVerBuffer = mDefualShaderUtil.getFloatBuffer(vers);

        float [] texcoodrs = generateTexCoor((int)(360/angleSpan),(int)(180/angleSpan));

        mTexCoorBuffer = mDefualShaderUtil.getFloatBuffer(texcoodrs);
    }


    //自动切分纹理产生纹理数组的方法
    public float[] generateTexCoor(int bw, int bh) {
        float[] result = new float[bw * bh * 6 * 2];
        float sizew = 1.0f / bw;//列数
        float sizeh = 1.0f / bh;//行数
        int c = 0;
        for (int i = 0; i < bh; i++) {
            for (int j = 0; j < bw; j++) {
                //每行列一个矩形，由两个三角形构成，共六个点，12个纹理坐标
                float s = j * sizew;
                float t = i * sizeh;
                result[c++] = s;
                result[c++] = t;
                result[c++] = s;
                result[c++] = t + sizeh;
                result[c++] = s + sizew;
                result[c++] = t;
                result[c++] = s + sizew;
                result[c++] = t;
                result[c++] = s;
                result[c++] = t + sizeh;
                result[c++] = s + sizew;
                result[c++] = t + sizeh;
            }
        }
        return result;
    }



    @Override
    public void initShader() {
        ////    uniform mat4 uMVPMatrix;
        mMVPMatrixHandler = GLES20.glGetUniformLocation(mProgram,"uMVPMatrix");
////    uniform mat4 uMMarix;
        mMMatrixHandler = GLES20.glGetUniformLocation(mProgram,"uMMatrix");
////    uniform vec3  uCamera; //相机位置
        mCameraMatrixHandler = GLES20.glGetUniformLocation(mProgram,"uCamera");
////    uniform vec3 uLightLocation;//光位置
        mLightLocationHandler = GLES20.glGetUniformLocation(mProgram,"uLightLocation");
//        private int mNormorlHandler;
////    attribute vec3 aNormal;//法向量
        mNormorlHandler = GLES20.glGetAttribLocation(mProgram,"aNormal");

//        private int mPostionHandler;
////    attribute vec3 aPostion;//顶点位置
        mPostionHandler = GLES20.glGetAttribLocation(mProgram,"aPostion");

//        private int mTextureCoordHandler;
////    attribute vec2 aTexCoor;//顶点纹理坐标
        mTextureCoordHandler = GLES20.glGetAttribLocation(mProgram,"aTexCoor");

//        //uniform sampler2D sTextureDay;//纹理内容数据
//        private int mTextureDay;
////    uniform sampler2D sTextureNight;//纹理内容数据
//        private int mTextureNight;
        mTextureDay = GLES20.glGetUniformLocation(mProgram,"sTextureDay");
        mTextureNight = GLES20.glGetUniformLocation(mProgram,"sTextureNight");

    }

    @Override
    protected int[] createTextureIds() {
        int[] textureIds =new int[2];
        textureIds[0] = initTextureId(R.drawable.earth);
        textureIds[1] = initTextureId(R.drawable.earthn);

        Log.e("textureIds","textureIds="+textureIds[0]+"-"+textureIds[1]);
        return textureIds;
    }

    @Override
    protected void drawSelf(int[] textureId) {

        MatrixState.pushMatrix();
        //地球自转
        MatrixState.rotate(eAngle, 0, 1, 0);

        GLES20.glUniformMatrix4fv(mMVPMatrixHandler,1,false,MatrixState.getFinalMatrix(),0);
        GLES20.glUniformMatrix4fv(mMMatrixHandler,1,false,MatrixState.getMMMatrix(),0);

        GLES20.glUniform3fv(mCameraMatrixHandler,1,MatrixState.cameraFB);
        GLES20.glUniform3fv(mLightLocationHandler,1,MatrixState.lightPositionFBSun);

        GLES20.glVertexAttribPointer(mPostionHandler,3,GLES20.GL_FLOAT,false,3*4,mVerBuffer);
        GLES20.glVertexAttribPointer(mTextureCoordHandler,2,GLES20.GL_FLOAT,false,2*4,mTexCoorBuffer);
        GLES20.glVertexAttribPointer(mNormorlHandler,4,GLES20.GL_FLOAT,false,3*4,mVerBuffer);


        GLES20.glEnableVertexAttribArray(mPostionHandler);
        GLES20.glEnableVertexAttribArray(mTextureCoordHandler);
        GLES20.glEnableVertexAttribArray(mNormorlHandler);


        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,textureId[0]);

        GLES20.glActiveTexture(GLES20.GL_TEXTURE1);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,textureId[1]);

        GLES20.glUniform1i(mTextureDay, 0);
        GLES20.glUniform1i(mTextureNight, 1);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, mVerCount);

        MatrixState.popMatrix();

    }

    public void startRotat(){
        new Thread(new Runnable() {
            @Override
            public void run() {

                while (startRotat){
                    eAngle+=2;
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

}
