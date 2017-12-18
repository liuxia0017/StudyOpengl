package lx.study.com.studyopengl.aty;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import lx.study.com.studyopengl.View.EGLView;
import lx.study.com.studyopengl.View.IOnSurfaceCreateListener;
import lx.study.com.studyopengl.View.mode.TriangleTower;
import lx.study.com.studyopengl.View.mode.base.EGLMode;


/**
 * Created by lx on 2017/12/17.
 */

public class PyramidsActivity extends AppCompatActivity {

    private EGLView mEGlView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mEGlView = new EGLView(this);
        mEGlView.setOnSurefaceCreateListener(new IOnSurfaceCreateListener() {
            @Override
            public EGLMode createEGLMode(Context c) {
                TriangleTower triangleTower = new TriangleTower(c, "triangletower/ver.glsl", "triangletower/frag.glsl");
                triangleTower.startRotat();
                return triangleTower;
            }
        });

        setContentView(mEGlView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mEGlView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mEGlView.onPause();
    }
}
