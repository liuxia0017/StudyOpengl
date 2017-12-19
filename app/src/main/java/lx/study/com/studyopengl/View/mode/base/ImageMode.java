package lx.study.com.studyopengl.View.mode.base;

import android.content.Context;
import android.graphics.Bitmap;

/**
 * Created by lx on 2017/12/18.
 */

public class ImageMode extends EGLMode {

    public ImageMode(Context c, String verFile, String fragFile) {
        super(c, verFile, fragFile);

    }

    @Override
    public void initData() {
        float []  vers = {
                1.0f,
        };
    }

    @Override
    public void initShader() {

    }


    @Override
    protected int[] createTextureIds() {



        return new int[0];
    }


    @Override
    protected void drawSelf(int[] textureId) {

    }
}
