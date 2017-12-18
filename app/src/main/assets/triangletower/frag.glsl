precision mediump float;
varying vec2 vTextureCoord;
varying vec4 vDiffuse;
uniform sampler2D sTexture;//采样器
void main() {
   vec4 finalColor = vec4(1.0);
   vec4 finalColor2 =  finalColor*vDiffuse+finalColor*vec4(0.15,0.15,0.15,0.15);
   gl_FragColor = texture2D(sTexture,vTextureCoord)*finalColor2;

}
