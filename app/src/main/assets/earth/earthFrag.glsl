precision mediump float;

varying vec2 vTextureCoord;//接收从顶点着色器过来的参数
uniform sampler2D sTextureDay;//纹理内容数据
uniform sampler2D sTextureNight;//纹理内容数据

varying vec4 vAmbient;//环境光强度
varying vec4 vDiffuse;//散色光强度
varying vec4 vSpecular;//镜面光强度
void main(){

  //给此片元从纹理中采样出颜色值
  vec4 finalColorDay;
  vec4 finalColorNight;



  finalColorDay  = texture2D(sTextureDay,vTextureCoord);
  finalColorDay  = finalColorDay*vAmbient+finalColorDay*vDiffuse+finalColorDay*vSpecular;

   finalColorNight = texture2D(sTextureNight,vTextureCoord);
   finalColorNight= finalColorNight*vec4(0.5f,0.5f,0.5f,1);

   if(vDiffuse.x>0.21){
      gl_FragColor = finalColorDay;
   }else if(vDiffuse.x<0.05){
      gl_FragColor= finalColorNight;
   }else{
       float t = (vDiffuse.x-0.05)/0.16f;
       gl_FragColor = t*finalColorDay+(1.0+t)*finalColorNight;
   }
}