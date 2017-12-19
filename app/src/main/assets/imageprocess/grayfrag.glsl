precision mediump float;

uniform sampler2D vTexture;
uniform int vIsHalf;


varying vec2 vCoordinate;
varying vec4 vPosition;

void main(){
    vec4 nColor = texture2D(vTexture,vCoordinate);
    if(vIsHalf==0){
        float color = nColor.r*0.30+nColor.g*0.59+nColor.b*0.11;
        gl_FragColor= vec4(color,color,color,nColor.a);
    }else{
        if(vPosition.x>0.0){
             float color = nColor.r*0.30+nColor.g*0.59+nColor.b*0.11;
             gl_FragColor= vec4(color,color,color,nColor.a);
        }else{
             gl_FragColor = nColor;
        }
    }
}