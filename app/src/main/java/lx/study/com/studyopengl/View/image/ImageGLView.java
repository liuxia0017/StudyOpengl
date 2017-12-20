package lx.study.com.studyopengl.View.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

/**
 * Created by lx on 2017/12/18.
 */

public class ImageGLView  extends GLSurfaceView{
    private ImageRender mRender;
    private Bitmap mBitmap;

    public ImageGLView(Context context,Bitmap bm) {
        super(context);
        this.mBitmap = bm;
        init(context);
    }

    public void setImageRender(ImageBaseRender render){
        if(mRender!=null){
            mRender.setImageRender(render);
            mRender.setImage(mBitmap);
            mRender.setRequsetFresh();
            this.requestRender();
        }
    }

    public void setImage(Bitmap bm){
        if(mRender!=null){
            mRender.setImage(bm);
            mRender.setRequsetFresh();

        }
        this.requestRender();
    }

    public ImageGLView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context c) {
        setEGLContextClientVersion(2);
        mRender=new ImageRender(new ColorImageRender(c,0));
        mRender.setImage(mBitmap);
        setRenderer(mRender);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        requestRender();
    }


    public void setHalf(boolean half) {
        mRender.setHalf(half);
        mRender.setRequsetFresh();
        requestRender();
    }
    public boolean getIsHalf(){
        return   mRender.getIshalf();
    }
}
