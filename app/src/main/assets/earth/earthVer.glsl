
uniform mat4 uMVPMatrix;
uniform mat4 uMMatrix;
uniform vec3  uCamera; //相机位置
uniform vec3 uLightLocation;//光位置

attribute vec3 aNormal;//法向量
attribute vec3 aPostion;//顶点位置
attribute vec2 aTexCoor;//顶点纹理坐标
varying vec2 vTextureCoord;//用于传递片元着色器的变量
varying vec4 vAmbient;//环境光强度
varying vec4 vDiffuse;//散色光强度
varying vec4 vSpecular;//镜面光强度



//定位光
void pointLight(in vec3 normal,//法向量
inout vec4 ambient, //环境光强度 输出
inout vec4 diffuse,//散射光强度  输出
inout vec4 specular,//镜面光强度 输出
in vec3 lightLocation,//光源位置 输入
in vec4 lightAmbient,//环境光强度
in vec4 lightDiffuse,//散射光强度
in vec4 lightSpecular//镜面光强度
){
      //环境光 =材质反射系数*环境光强度
      ambient = lightAmbient;
      //散射光=材质反射系数*散射光强度*max(cos(入射角),0)
        //计算变换后的法向量
        vec3 normalTarget = normal+aPostion;
        vec3 newNormal = (uMMatrix*vec4(normalTarget,1)).xyz-
        (uMMatrix*vec4(aPostion,1)).xyz;
        //标准化法向量
        newNormal = normalize(newNormal);
        //计算从表面点到光源的位置=光源位置-变换后的坐标位置
        vec3 vp = normalize(lightLocation-(uMMatrix*vec4(aPostion,1)).xyz);
          float nDotViewPostion=max( 0.0,dot(newNormal,vp) );
         diffuse=lightDiffuse*nDotViewPostion;

        //镜面光=材质反射系统*镜面光强度*
//        max(0,(cos(入射角))*【粗糙度次方】)
        //计算表面点到摄象机的向量
         vec3 eye = normalize(uCamera-(uMMatrix*vec4(aPostion,1)).xyz);
         //求视线与光线的半向量
         vec3 halfVector = normalize(vp+eye);
         //粗造度
         float ess = 50.0f;

         specular = lightSpecular*(max(0.0, pow(dot(newNormal,halfVector),ess)));
}


void main() {
      gl_Position = uMVPMatrix*vec4(aPostion,1);
      vec4 ambientT =vec4(0,0,0,0);
      vec4 diffuseT = vec4(0,0,0,0);
      vec4 specularT =vec4(0.0,0,0,0);

      pointLight(normalize(aNormal),
      ambientT,diffuseT,specularT,
      uLightLocation,
      vec4(0.05f,0.05f,0.05f,1),
      vec4(1,1,1,1),
      vec4(0.3f,0.3f,0.3f,1));

      vAmbient = ambientT;
      vDiffuse = diffuseT;
      vSpecular = specularT;

      vTextureCoord = aTexCoor;

}

