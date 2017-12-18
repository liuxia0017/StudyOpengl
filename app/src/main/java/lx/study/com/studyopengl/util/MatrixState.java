package lx.study.com.studyopengl.util;

import android.opengl.Matrix;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Stack;

//存储矩阵状态的类
public class MatrixState {
    public static float[] mProjMatrix = new float[16];//4x4矩阵 投影用



    private static float[] mCameraMatrix = new float[16];//摄像机位置朝向9参数矩阵

    private static float[] mMVPMatrix;//最后起作用的总变换矩阵
    static float[] mMMatrix = new float[16];//具体物体的移动旋转矩阵

    public static float[] getMMMatrix(){ return  mMMatrix;}
    public static float[] getCameraMatrix(){ return  mCameraMatrix;}

    public static float[] lightLocationSun = new float[]{0, 0, 0};//太阳定位光光源位置
    public static FloatBuffer cameraFB,lightPositionFBSun;

    public static Stack<float[]> mStack = new Stack<float[]>();//保护变换矩阵的栈

    //保护变换矩阵
    public static void pushMatrix() {
        //把项压入堆栈顶部。
        mStack.push(mMMatrix.clone());
    }

    //恢复变换矩阵
    public static void popMatrix() {
        //移除堆栈顶部的对象，并作为此函数的值返回该对象。
        mMMatrix = mStack.pop();
    }

    //获取不变换初始矩阵
    public static void setInitStack() {
        Matrix.setRotateM(mMMatrix, 0, 0, 1, 0, 0);
    }
    //设置沿xyz轴移动
    public static void transtate(float x, float y, float z) {
        Matrix.translateM(mMMatrix, 0, x, y, z);
    }
    //设置绕xyz轴转动
    public static void rotate(float angle, float x, float y, float z) {
        Matrix.rotateM(mMMatrix, 0, angle, x, y, z);
    }


    /**
     * 作用：设置摄像机
     * 前三个：摄像机位置x y z
     * 中间三个：摄像机目标点x y z
     * 后三个：摄像机UP向量X分量，摄像机UP向量Y分量，摄像机UP向量Z分量
     */
    public static void setCamera(float cx, float cy, float cz, float tx, float ty, float tz, float upx, float upy, float upz) {
        Matrix.setLookAtM(mCameraMatrix, 0,
                cx, cy, cz,
                tx, ty, tz,
                upx, upy, upz);
        float[] cameraLocation = new float[3];//摄像机位置
        cameraLocation[0] = cx;
        cameraLocation[1] = cy;
        cameraLocation[2] = cz;

        ByteBuffer llbb = ByteBuffer.allocateDirect(3 * 4);
        llbb.order(ByteOrder.nativeOrder());//设置字节顺序
        cameraFB = llbb.asFloatBuffer();
        cameraFB.put(cameraLocation);
        cameraFB.position(0);

    }


    /**
     * 作用：设置透视投影参数
     * 前四个：near面的left，right，bottom，top
     * 后两个：near面距离，far面距离
     */
    public static void setProject(float left, float right, float bottom, float top, float near, float far) {
        Matrix.frustumM(mProjMatrix, 0, left, right, bottom, top, near, far);
    }

    /**
     * 设置正交投影参数
     * <p>
     * 流程 :
     * <p>
     * Matrix.orthoM(mProjMatrix, 0, left, right, bottom, top, near, far);
     *
     * @param left,right,bottom,top near面的left,right,bottom,top。
     * @param near                  near面距离
     * @param far                   far面距离
     */
    public static void setProjectOrtho(float left, float right,
                                       float bottom, float top,
                                       float near, float far) {
        Matrix.orthoM(mProjMatrix, 0, left, right, bottom, top, near, far);
    }
    /**
     * 设置太阳光源位置的方法
     * <p>
     * 流程 :
     * <p><p/>
     *
     * @param x,y,z 光源位置
     */
    public static void setLightLocationSun(float x, float y, float z) {
        lightLocationSun[0] = x;
        lightLocationSun[1] = y;
        lightLocationSun[2] = z;

        ByteBuffer llbb = ByteBuffer.allocateDirect(3 * 4);
        llbb.order(ByteOrder.nativeOrder());//设置字节顺序
        lightPositionFBSun = llbb.asFloatBuffer();
        lightPositionFBSun.put(lightLocationSun);
        lightPositionFBSun.position(0);
    }



    //获取具体物体的总变换矩阵
    public static float[] getFinalMatrix() {
        mMVPMatrix = new float[16];
        Matrix.multiplyMM(mMVPMatrix, 0, mCameraMatrix, 0, mMMatrix, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjMatrix, 0, mMVPMatrix, 0);
        return mMVPMatrix;
    }

}
