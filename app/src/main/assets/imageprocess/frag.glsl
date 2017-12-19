precision mediump float;

uniform sampler2D vTexture;

varying vec2 vCoordinate;
varying vec4 vColor;

void main(){
    gl_FragColor=texture2D(vTexture,vCoordinate);
}