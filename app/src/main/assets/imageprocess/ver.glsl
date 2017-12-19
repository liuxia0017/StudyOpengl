uniform mat4 vMatrix;

attribute vec4 vPosition;
attribute vec2 aCoordinate;


varying vec2 vCoordinate;


void main(){
    gl_Position=vMatrix*vPosition;
    vCoordinate=aCoordinate;
}