uniform mat4 vMatrix;

attribute vec4 aPosition;
attribute vec2 aCoordinate;
uniform int vChangeTypever;


varying vec2 vCoordinate;
varying vec4 vPosition;


void main(){
    if(vChangeTypever==7){
        gl_Position=vMatrix*vec4(aPosition.x/2.0,aPosition.y/2.0,aPosition.z/2.0,1);
    }else{
        gl_Position=vMatrix*aPosition;
    }
    vCoordinate=aCoordinate;
    vPosition = gl_Position;
}