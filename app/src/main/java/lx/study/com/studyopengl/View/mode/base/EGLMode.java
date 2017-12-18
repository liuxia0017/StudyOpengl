package lx.study.com.studyopengl.View.mode.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import java.nio.FloatBuffer;

import lx.study.com.studyopengl.util.ShareUtils;


/**
 * Created by lx on 2017/12/13.
 */

public abstract class EGLMode implements IEGLMode {

    protected FloatBuffer mVerBuffer;  //顶点数据
    protected FloatBuffer mTexCoorBuffer;  //纹理映射数据
    protected int mVerCount;     //顶点数量

    protected int[] mTextureIds;

    protected ShareUtils mDefualShaderUtil;  //工具类
    /**
     *  着色器程序ID
     */
    protected int mProgram;

    protected Context mContext; //activty上下文

    protected boolean startRotat = true;  //是否旋转

    public void enableRotat(){
        startRotat = true;
    }  //开启旋转
    public void disableRotat(){
        startRotat = false;
    } //关闭旋转

    //设置工具
    public void setShaderUitl(ShareUtils utils){
        if(utils==null){
            mDefualShaderUtil = new ShareUtils();
        }else{
            this.mDefualShaderUtil = utils;
        }
    }

    public EGLMode(Context c, String verFile, String fragFile){
        mDefualShaderUtil = new ShareUtils();
        mContext = c;
        initData();
        mProgram = createProgram(verFile,fragFile,c);
        initShader();
        mTextureIds = createTextureIds();
    }

    protected  int createProgram(String verFile,String fragFile,Context c){
        String verSource = mDefualShaderUtil.loadFromAssetsFile(verFile, c.getResources());
        String fragSource = mDefualShaderUtil.loadFromAssetsFile(fragFile, c.getResources());
        return  mDefualShaderUtil.createProgram(verSource,fragSource);
    }

    @SuppressLint("NewApi")
    public int  initTextureId(int drawable){
        int [] textureIds = new int[1];
        GLES20.glGenTextures(1,textureIds,0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,textureIds[0]);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_MIN_FILTER,GLES20.GL_NEAREST);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_MAG_FILTER,GLES20.GL_LINEAR);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_WRAP_S,GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_WRAP_T,GLES20.GL_CLAMP_TO_EDGE);
        Bitmap bm = BitmapFactory.decodeResource(mContext.getResources(), drawable);
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D,0,bm,0);
        bm.recycle();
        return textureIds[0];
    }


    @Override
    public void draw() {
        GLES20.glUseProgram(mProgram);
        drawSelf(mTextureIds);
    }
    //创建纹理 返回ID
    protected abstract int[] createTextureIds();
    //开始画
    protected abstract void drawSelf(int[] textureId);
}
