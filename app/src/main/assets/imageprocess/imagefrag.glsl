precision mediump float;
uniform sampler2D sTexture;
varying vec2 vTexCoor;

//varying vec4 vColor;
void main() {
  gl_FragColor = texture2D(sTexture,vTexCoor);

}
