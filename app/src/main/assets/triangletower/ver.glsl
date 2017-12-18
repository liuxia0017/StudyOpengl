uniform mat4 uMVPMatrix;
attribute vec3 aPosition;
attribute vec2 aTexCoor;
varying vec2 vTextureCoord;
varying vec4 vDiffuse;//要传递的散射光变量
vec4 pointLight(vec3 normal,vec3 lightLocation,vec4 lightDiffuse){
     vec3 vp = normalize(lightLocation -(uMVPMatrix*vec4(aPosition,1)).xyz);
     vec3 newTargetc = normalize((uMVPMatrix*vec4(normal+aPosition,1)).xyz-
     (uMVPMatrix*vec4(aPosition,1)).xyz);
     return lightDiffuse*max(0.0,dot(newTargetc,vp));
     }
void main() {

     gl_Position = uMVPMatrix*vec4(aPosition,1);
     vTextureCoord = aTexCoor;
     vec3 pos = vec3(0.0,100.0,100.0);
     vec4 at =vec4(2.0,2.0,2.0,1.0);
     vDiffuse = pointLight(normalize(aPosition),pos,at);

}
