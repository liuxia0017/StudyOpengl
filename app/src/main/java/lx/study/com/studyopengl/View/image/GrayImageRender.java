package lx.study.com.studyopengl.View.image;

import android.content.Context;
import android.opengl.GLES20;

/**
 * Created by lx on 2017/12/19.
 */

public class GrayImageRender extends ImageBaseRender {




    private GrayImageRender(Context c, String verFile, String fragFile) {
        super(c, verFile, fragFile);
    }
    public GrayImageRender(Context c) {
        this(c, "imageprocess/grayver.glsl", "imageprocess/grayfrag.glsl");
    }

    @Override
    protected void onDrawCreatedSet(int mProgram) {
    }

    @Override
    protected void onDrawSet() {
    }
}
