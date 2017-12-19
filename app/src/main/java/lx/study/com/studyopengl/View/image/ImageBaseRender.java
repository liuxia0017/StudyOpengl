package lx.study.com.studyopengl.View.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.opengl.Matrix;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import lx.study.com.studyopengl.util.ShaderUtil;

/**
 * Created by lx on 2017/12/18.
 */

public abstract class ImageBaseRender implements GLSurfaceView.Renderer {
    private final String versource;
    private final String fragsource;
    private Context mContext;

    private ShaderUtil mDefaultUitl;

    protected int mProgram;//着色器程序ID
//    attribute vec4 vPosition;
    protected int glAPosition;
//    attribute vec2 vCoordinate;
    protected int glACoordinate;
//    uniform mat4 vMatrix;
    protected int glUVMatrix;
//    uniform sampler2D vTexture;
    protected int glUVTextture;



    protected Bitmap mBitmap;
    private float uXY;
    private int glUisHalf;

    private float[] mViewMatrix=new float[16];
    private float[] mProjectMatrix=new float[16];
    private float[] mMVPMatrix=new float[16];
    private int textureId;


    private int isHalf = 0;
    public void setHalf(boolean ishalf){
        if(ishalf){
            isHalf = 1;
        }else {
            isHalf = 0;
        }
    }

    public boolean getIsHalf(){
        if(isHalf==0){
            return false;
        }else{
            return true;
        }
    }

    private final float[] sPos={
            -1.0f,1.0f,
            -1.0f,-1.0f,
            1.0f,1.0f,
            1.0f,-1.0f
    };

    private final float[] sCoord={
            0.0f,0.0f,
            0.0f,1.0f,
            1.0f,0.0f,
            1.0f,1.0f,
    };
    private final float[] sColor={
            0.0f,0.0f,0f,0f,
            0.0f,0.0f,0f,0f,
            0.0f,0.0f,0f,0f,
            0.0f,0.0f,0f,0f,
    };
    private FloatBuffer bPos; //顶点缓冲
    private FloatBuffer bCoord; //纹理顶点缓冲
    private FloatBuffer bColor; //纹理顶点缓冲
    public void setBitmap(Bitmap bm){
        this.mBitmap = bm;
    }

    public ImageBaseRender(Context c, String verFile, String fragFile){
        mContext = c;
        mDefaultUitl = new ShaderUtil();
        versource = mDefaultUitl.loadFromAssetsFile(verFile,c.getResources());
        fragsource = mDefaultUitl.loadFromAssetsFile(fragFile,c.getResources());
        ByteBuffer bb=ByteBuffer.allocateDirect(sPos.length*4);
        bb.order(ByteOrder.nativeOrder());
        bPos=bb.asFloatBuffer();
        bPos.put(sPos);
        bPos.position(0);
        ByteBuffer cc=ByteBuffer.allocateDirect(sCoord.length*4);
        cc.order(ByteOrder.nativeOrder());
        bCoord=cc.asFloatBuffer();
        bCoord.put(sCoord);
        bCoord.position(0);

        ByteBuffer dd=ByteBuffer.allocateDirect(sColor.length*4);
        dd.order(ByteOrder.nativeOrder());
        bColor=dd.asFloatBuffer();
        bColor.put(sColor);
        bColor.position(0);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(1f,1f,1f,1f);
        mProgram = mDefaultUitl.createProgram(versource,fragsource);

//        GLES20.glEnable(GLES20.GL_TEXTURE_2D);
        glAPosition =  GLES20.glGetAttribLocation(mProgram,"aPosition");
        glACoordinate = GLES20.glGetAttribLocation(mProgram,"aCoordinate");
        glUVMatrix = GLES20.glGetUniformLocation(mProgram,"vMatrix");
        glUVTextture = GLES20.glGetUniformLocation(mProgram,"vTexture");
        glUisHalf = GLES20.glGetUniformLocation(mProgram,"vIsHalf");
        onDrawCreatedSet(mProgram);
    }

    /**
     * 初始新加的着色器属性
     * @param mProgram
     */
    protected abstract void onDrawCreatedSet(int mProgram);

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0,0,width,height);
        int w=mBitmap.getWidth();
        int h=mBitmap.getHeight();
        float sWH=w/(float)h; //图片宽高比
        float sWidthHeight=width/(float)height;//屏幕宽高比
        uXY=sWidthHeight;
        if(width>height){
            if(sWH>sWidthHeight){
                Matrix.orthoM(mProjectMatrix, 0, -sWidthHeight*sWH,sWidthHeight*sWH, -1,1, 3, 5);
            }else{
                Matrix.orthoM(mProjectMatrix, 0, -sWidthHeight/sWH,sWidthHeight/sWH, -1,1, 3, 5);
            }
        }else{
            if(sWH>sWidthHeight){
                Matrix.orthoM(mProjectMatrix, 0, -1, 1, -1/sWidthHeight*sWH, 1/sWidthHeight*sWH,3, 5);
            }else{
                Matrix.orthoM(mProjectMatrix, 0, -1, 1, -sWH/sWidthHeight, sWH/sWidthHeight,3, 5);
            }
        }

        //设置相机位置
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, 5.0f, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        //计算变换矩阵
        Matrix.multiplyMM(mMVPMatrix,0,mProjectMatrix,0,mViewMatrix,0);

    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT|GLES20.GL_DEPTH_BUFFER_BIT);
        GLES20.glUseProgram(mProgram);
        onDrawSet();

//        GLES20.glUniform1f(glHUxy,uXY);
        GLES20.glUniform1i(glUisHalf,isHalf);

        GLES20.glUniformMatrix4fv(glUVMatrix,1,false,mMVPMatrix,0);

        GLES20.glVertexAttribPointer(glAPosition,2,GLES20.GL_FLOAT,false,0,bPos);
        GLES20.glVertexAttribPointer(glACoordinate,2,GLES20.GL_FLOAT,false,0,bCoord);

        GLES20.glEnableVertexAttribArray(glAPosition);
        GLES20.glEnableVertexAttribArray(glACoordinate);

        textureId=createTexture();
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,textureId);
        GLES20.glUniform1i(glUVTextture, 0);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP,0,4);

    }

    private int createTexture(){
        int[] texture=new int[1];
        if(mBitmap!=null&&!mBitmap.isRecycled()){
            //生成纹理
            GLES20.glGenTextures(1,texture,0);
            //生成纹理
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,texture[0]);
            //设置缩小过滤为使用纹理中坐标最接近的一个像素的颜色作为需要绘制的像素颜色
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER,GLES20.GL_NEAREST);
            //设置放大过滤为使用纹理中坐标最接近的若干个颜色，通过加权平均算法得到需要绘制的像素颜色
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_MAG_FILTER,GLES20.GL_LINEAR);
            //设置环绕方向S，截取纹理坐标到[1/2n,1-1/2n]。将导致永远不会与border融合
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S,GLES20.GL_CLAMP_TO_EDGE);
            //设置环绕方向T，截取纹理坐标到[1/2n,1-1/2n]。将导致永远不会与border融合
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T,GLES20.GL_CLAMP_TO_EDGE);
            //根据以上指定的参数，生成一个2D纹理
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, mBitmap, 0);
            return texture[0];
        }
        return 0;
    }


    /**
     * 传值给着色器
     */
    protected abstract void onDrawSet();
}
