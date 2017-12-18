package lx.study.com.studyopengl.View;

import android.content.Context;

import lx.study.com.studyopengl.View.mode.base.EGLMode;


/**
 * Created by lx on 2017/12/13.
 */

public interface IOnSurfaceCreateListener {
    EGLMode createEGLMode(Context c);
}
