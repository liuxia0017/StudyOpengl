uniform mat4 vMatrix;

attribute vec4 aPosition;
attribute vec2 aCoordinate;


varying vec2 vCoordinate;


void main(){
    gl_Position=vMatrix*aPosition;
    vCoordinate=aCoordinate;
}