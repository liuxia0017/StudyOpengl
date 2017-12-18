package lx.study.com.studyopengl.View;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import lx.study.com.studyopengl.View.mode.base.EGLMode;


/**
 * Created by lx on 2017/12/13.
 */

public class EGLView extends GLSurfaceView {
    private EGLMode mEGLMode;
    private IOnSurfaceCreateListener mOnSurefaceCreateListener;
    private Context mContext;
    private EGLRender mRender;

    public void enableRotat(){
        if(mRender!=null)
            mRender.enableRotat();
    }
    public void disableRotat(){
        if(mRender!=null)
            mRender.disableRotat();
    }

    public void setOnSurefaceCreateListener(IOnSurfaceCreateListener mOnSurefaceCreateListener){
        if(mRender!=null)
            mRender.setOnSurefaceCreateListener(mOnSurefaceCreateListener);
    }

    public void putRender(EGLRender render){
        onPause();
        mRender = render;
    }
    public EGLView(Context context) {
        super(context);
        mContext = context;
        init(context);
    }


    public EGLView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init( context  );
    }

    private void init(Context context) {
        setEGLContextClientVersion(2);
        mRender = new EGLRender(context);
        setRenderer(mRender);
        setRenderMode(RENDERMODE_CONTINUOUSLY);
    }




//    public class  EGLRender implements  Renderer{
//
//
//        @Override
//        public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
//            GLES20.glClearColor(0.5f,0.5f,0.5f,0.5f);
//            GLES20.glEnable(GLES20.GL_DEPTH_TEST);
//            //关闭背面剪裁
//            GLES20.glDisable(GLES20.GL_CULL_FACE);
//           if(mOnSurefaceCreateListener!=null){
//                mEGLMode = mOnSurefaceCreateListener.createEGLMode(mContext);
//            }
//
//        }
//
//        @Override
//        public void onSurfaceChanged(GL10 gl10, int i, int i1) {
//
//            GLES20.glViewport(0,0,i,i1);
//            float r = i*1.0f/i1;
//            MatrixState.setProject(-r,r,-1,1,2,100);
//            MatrixState.setCamera(0,0,7,0,0,0,0,1,0);
//            MatrixState.setLightLocationSun(100, 20, 0);
//            MatrixState.setInitStack();
//
//        }
//
//        @Override
//        public void onDrawFrame(GL10 gl10) {
//            GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT|GLES20.GL_COLOR_BUFFER_BIT);
//            if(mEGLMode!=null){
//                mEGLMode.draw();
//            }
//        }
//    }


}
