package lx.study.com.studyopengl.View.image;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;

/**
 * Created by lx on 2017/12/19.
 */

public class ColorImageRender extends ImageBaseRender {

//    uniform int vChangeType;
    private int glUIChangeType;
//    uniform vec3 vChangeColor;
    private int glUVChangColor;

    private int glUvChangeTypever;

    private int type;
    private ColorImageRender(Context c, String verFile, String fragFile) {
        super(c, verFile, fragFile);
    }
    public ColorImageRender(Context c,int type) {
        this(c, "imageprocess/colorver.glsl", "imageprocess/colorfrag.glsl");
        this.type = type;
    }

    @Override
    protected void onDrawCreatedSet(int mProgram) {

//        uniform int vChangeType;'
        glUIChangeType = GLES20.glGetUniformLocation(mProgram,"vChangeType");
//        uniform vec3 vChangeColor;
        glUVChangColor = GLES20.glGetUniformLocation(mProgram,"vChangeColor");
        glUvChangeTypever = GLES20.glGetUniformLocation(mProgram,"vChangeTypever");
    }

    @Override
    protected void onDrawSet() {
        GLES20.glUniform1i(glUIChangeType,type);
        GLES20.glUniform3fv(glUVChangColor,1,ImageChangeColor[type],0);
        GLES20.glUniform1i(glUvChangeTypever,type);
        Log.e("onDrawSet","type="+type);
    }

    public   final  int COLOR_TYPE_NONE=0;
    public  final  int COLOR_TYPE_GRAY=1;
    public  final  int COLOR_TYPE_COOL=2;
    public  final  int COLOR_TYPE_WARM=3;
    public  final  int COLOR_TYPE_BLUR=4;
    public  final  int COLOR_TYPE_MAGN=5;
    public float [][] ImageChangeColor = {
            {0.0f,0.0f,0.0f},//原色
            {0.299f,0.587f,0.114f},//灰度
            {0.0f,0.0f,0.3f}, //冷色
            {0.1f,0.1f,0.0f},//暖色
            {0.006f,0.004f,0.002f},//模糊
            {5.0f,5.0f,400f},//马赛克
            {0.0f,0.0f,0.5f},//放大
            {0.0f,0.0f,0.4f},//缩小
            {0.0f,0.0f,0.4f},//扭曲
            {0.0f,0.0f,0.4f}, //旋转
            {0.0f,0.0f,0.4f}, //浮雕
            {0.0f,0.0f,0.4f}, //膨胀
            {0.0f,0.0f,0.4f}, //腐蚀
            {0.0f,0.0f,0.4f} //

    };

//
//    NONE(0,new float[]{0.0f,0.0f,0.0f}),
//    GRAY(1,new float[]{0.299f,0.587f,0.114f}),
//    COOL(2,new float[]{0.0f,0.0f,0.1f}),
//    WARM(2,new float[]{0.1f,0.1f,0.0f}),
//    BLUR(3,new float[]{0.006f,0.004f,0.002f}),
//    MAGN(4,new float[]{0.0f,0.0f,0.4f});


}
