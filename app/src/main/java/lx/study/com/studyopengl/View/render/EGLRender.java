package lx.study.com.studyopengl.View.render;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import lx.study.com.studyopengl.View.IOnSurfaceCreateListener;
import lx.study.com.studyopengl.View.mode.base.EGLMode;
import lx.study.com.studyopengl.util.MatrixState;


/**
 * Created by lx on 2017/12/17.
 */

public class EGLRender implements GLSurfaceView.Renderer {

    private IOnSurfaceCreateListener mOnSurefaceCreateListener;
    private EGLMode mEGLMode;
    private Context mContext;

    public EGLRender(Context context){
        mContext = context;
    }

    public void enableRotat(){
        if(mEGLMode!=null)
            mEGLMode.enableRotat();
    }
    public void disableRotat(){
        if(mEGLMode!=null)
            mEGLMode.disableRotat();
    }

    public void setOnSurefaceCreateListener(IOnSurfaceCreateListener mOnSurefaceCreateListener){
        this.mOnSurefaceCreateListener = mOnSurefaceCreateListener;
    }


    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        GLES20.glClearColor(0.5f,0.5f,0.5f,0.5f);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        //关闭背面剪裁
        GLES20.glDisable(GLES20.GL_CULL_FACE);
        if(mOnSurefaceCreateListener!=null){
            mEGLMode = mOnSurefaceCreateListener.createEGLMode(mContext);
        }
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int i, int i1) {

        GLES20.glViewport(0,0,i,i1);
        float r = i*1.0f/i1;
        MatrixState.setProject(-r,r,-1,1,2,100);
        MatrixState.setCamera(0,0,7,0,0,0,0,1,0);
        MatrixState.setLightLocationSun(100, 20, 0);
        MatrixState.setInitStack();

    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT|GLES20.GL_COLOR_BUFFER_BIT);
        if(mEGLMode!=null){
            mEGLMode.draw();
        }
    }
}
