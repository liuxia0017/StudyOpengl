uniform mat4 uMVPMatrix;
attribute vec3 aPosition;
//attribute vec4 aColor;
attribute vec2 aTexCoor;//顶点纹理坐标

//varying vec4 vColor;
varying vec2 vTexCoor;

void main() {

  gl_Position = uMVPMatrix*vec4(aPosition,1);
//  vColor = aColor;
  vTexCoor = aTexCoor;

}
