precision mediump float;

uniform sampler2D vTexture;
uniform int vIsHalf;
uniform int vChangeType;
uniform vec3 vChangeColor;
uniform float uXY;



varying vec2 vCoordinate;
varying vec4 vPosition;

void modifyColor(vec4 color){
    color.r=max(min(color.r,1.0),0.0);
    color.g=max(min(color.g,1.0),0.0);
    color.b=max(min(color.b,1.0),0.0);
    color.a=max(min(color.a,1.0),0.0);
}

void main(){
    vec4 nColor = texture2D(vTexture,vCoordinate);
    if(vIsHalf==1){
       if(vPosition.x>0.0){
            if(vChangeType==0){
                gl_FragColor =nColor;
            }else if(vChangeType==1){
                float color = nColor.r*vChangeColor.r+nColor.g*vChangeColor.g+nColor.b*vChangeColor.b;
                gl_FragColor= vec4(color,color,color,nColor.a);
            }else if(vChangeType==2){ //
                vec4 color = nColor +vec4(vChangeColor,0.0);
                modifyColor(color);
                gl_FragColor= color;
            }else if(vChangeType==3){
                vec4 color = nColor +vec4(vChangeColor,0.0);
                modifyColor(color);
                gl_FragColor= color;
            }else if(vChangeType==4){
                nColor += texture2D(vTexture,vec2(vCoordinate.x-vChangeColor.r,vCoordinate.y-vChangeColor.r));
                        nColor += texture2D(vTexture,vec2(vCoordinate.x+vChangeColor.r,vCoordinate.y+vChangeColor.r));
                        nColor += texture2D(vTexture,vec2(vCoordinate.x+vChangeColor.r,vCoordinate.y-vChangeColor.r));
                        nColor += texture2D(vTexture,vec2(vCoordinate.x-vChangeColor.r,vCoordinate.y+vChangeColor.r));
                        nColor += texture2D(vTexture,vec2(vCoordinate.x-vChangeColor.g,vCoordinate.y-vChangeColor.g));
                        nColor += texture2D(vTexture,vec2(vCoordinate.x+vChangeColor.g,vCoordinate.y+vChangeColor.g));
                        nColor += texture2D(vTexture,vec2(vCoordinate.x+vChangeColor.g,vCoordinate.y-vChangeColor.g));
                        nColor += texture2D(vTexture,vec2(vCoordinate.x-vChangeColor.g,vCoordinate.y+vChangeColor.g));
                        nColor += texture2D(vTexture,vec2(vCoordinate.x-vChangeColor.b,vCoordinate.y-vChangeColor.b));
                        nColor += texture2D(vTexture,vec2(vCoordinate.x+vChangeColor.b,vCoordinate.y+vChangeColor.b));
                        nColor += texture2D(vTexture,vec2(vCoordinate.x+vChangeColor.b,vCoordinate.y-vChangeColor.b));
                        nColor += texture2D(vTexture,vec2(vCoordinate.x-vChangeColor.b,vCoordinate.y+vChangeColor.b));
//                        nColor = nColor/5.0;
                        gl_FragColor = nColor;
            }else if(vChangeType==5){//马赛克
                  vec2 textSize = vec2(vChangeColor.z,vChangeColor.z);
                  vec2 mosaicSize = vec2(vChangeColor.x,vChangeColor.y);
                  vec2 intXY = vec2(vCoordinate.x*textSize.x,vCoordinate.y*textSize.y);
                  vec2 xymosaicSize = vec2(floor(intXY.x/mosaicSize.x)*mosaicSize.x,floor(intXY.y/mosaicSize.y)*mosaicSize.y);
                  vec2 uvmosaicSize = vec2(xymosaicSize.x/textSize.x,xymosaicSize.y/textSize.y);
                  gl_FragColor = texture2D(vTexture,uvmosaicSize);
            }else if(vChangeType==6){
                  float  dis = distance(vec2(vPosition.x,vPosition.y/uXY),vec2(vChangeColor.x,vChangeColor.y));
                  if(dis<vChangeColor.z){
                     nColor = texture2D(vTexture,vec2(vCoordinate.x/2.0+0.25,vCoordinate.y/2.0+0.25));
                  }
                 gl_FragColor= nColor;
            }else if(vChangeType==7){
                gl_FragColor= nColor;
            }else if(vChangeType==8){
                float uD=80.0;//角度
                 float uR=0.5;//旋转半径
                 //传过来的纹理坐标
                  vec2 st=vCoordinate;
                  //模型
                 ivec2 ires=ivec2(512,512);
                 //向量的S坐标
                 float Res=float(ires.s);
                //计算半径
                 float Radius=Res*uR;
                  //变换纹理
                 vec2 xy=Res*st;
                 vec2 dxy=xy-vec2(Res/2.0,Res/2.0);
                 float r =length(dxy);
                 //计算抛物线递减因子得到角度
                 float beta= atan(dxy.y,dxy.x)+radians(uD)*2.0*(-(r/Radius)*(r/Radius)+1.0);
                 vec2 xy1=xy;
                 //范围内有效果
                 if(r<=Radius){
                     xy1=Res/2.0+r*vec2(cos(beta),sin(beta));
                 }
                 st=xy1/Res;
                 gl_FragColor=vec4(texture2D(vTexture,st).rgb,1.0);
            }else if(vChangeType==9){
               gl_FragColor= texture2D(vTexture,vec2(1.0-vCoordinate.x,1.0-vCoordinate.y));
            }else if(vChangeType==10){
   //当前取得对应纹理坐标的纹理值
                    vec2 tex= vCoordinate;
                   //模型
                   vec2  texSize=vec2(500.0,500.0);
                  ///下一个纹理坐标
                   vec2 upTex=vec2(tex.x-1.0/texSize.x, tex.y-1.0/texSize.y);
                   //取得模型对应纹理坐标的纹理值
                   vec4 newTex=texture2D(vTexture,upTex);
                   //相减
                 vec4 dleTex=nColor-newTex;
                 //灰度
                   vec4  bkColor=vec4(0.5,0.5,0.5,0.5);

                   gl_FragColor=dleTex+bkColor;
             }else if(vChangeType==11){
               //算式(1-K)V1+K*V2
                float k=0.5;//K
                vec3 iRGB= nColor.rgb;
                vec3 tRGB= vec3(0.0,0.0,0.0);
                //mix（x，y，a）返回x和y的线性插值结果
                gl_FragColor=vec4(mix(iRGB,tRGB,k),1.0);
             }else if(vChangeType==12){
                float d = 500.0;
                float data = 1.0/d;
                vec4 maxColor = vec4(-1.0);
                for(int i =-1;i<=1;i++){
                    for(int j=-1;j<=1;j++){
                        float x = vCoordinate.x+float(i)*data;
                        float y = vCoordinate.y+float(i)*data;
                        maxColor= max(maxColor,texture2D(vTexture,vec2(x,y)));
                    }
                }
               gl_FragColor = maxColor;
              }else if(vChangeType==13){
                            float d = 500.0;
                            float data = 1.0/d;
                            vec4 maxColor = vec4(1.0);
                            for(int i =-1;i<=1;i++){
                                for(int j=-1;j<=1;j++){
                                    float x = vCoordinate.x+float(i)*data;
                                    float y = vCoordinate.y+float(i)*data;
                                    maxColor= min(maxColor,texture2D(vTexture,vec2(x,y)));
                                }
                            }
                           gl_FragColor = maxColor;
                          }else{
               gl_FragColor =nColor;
            }
          }else{
              gl_FragColor =nColor;
          }
    }else {
       if(vChangeType==0){
           gl_FragColor =nColor;
       }else if(vChangeType==1){
           float color = nColor.r*vChangeColor.r+nColor.g*vChangeColor.g+nColor.b*vChangeColor.b;
           gl_FragColor= vec4(color,color,color,nColor.a);
       }else if(vChangeType==2){
            vec4 color = nColor +vec4(vChangeColor,0.0);
            modifyColor(color);
            gl_FragColor= color;
       }else if(vChangeType==3){
            vec4 color = nColor +vec4(vChangeColor,0.0);
            modifyColor(color);
            gl_FragColor= color;
       }else if(vChangeType==4){
            nColor += texture2D(vTexture,vec2(vCoordinate.x-vChangeColor.r,vCoordinate.y-vChangeColor.r));
            nColor += texture2D(vTexture,vec2(vCoordinate.x+vChangeColor.r,vCoordinate.y+vChangeColor.r));
            nColor += texture2D(vTexture,vec2(vCoordinate.x+vChangeColor.r,vCoordinate.y-vChangeColor.r));
            nColor += texture2D(vTexture,vec2(vCoordinate.x-vChangeColor.r,vCoordinate.y+vChangeColor.r));
            nColor += texture2D(vTexture,vec2(vCoordinate.x-vChangeColor.g,vCoordinate.y-vChangeColor.g));
            nColor += texture2D(vTexture,vec2(vCoordinate.x+vChangeColor.g,vCoordinate.y+vChangeColor.g));
            nColor += texture2D(vTexture,vec2(vCoordinate.x+vChangeColor.g,vCoordinate.y-vChangeColor.g));
            nColor += texture2D(vTexture,vec2(vCoordinate.x-vChangeColor.g,vCoordinate.y+vChangeColor.g));
            nColor += texture2D(vTexture,vec2(vCoordinate.x-vChangeColor.b,vCoordinate.y-vChangeColor.b));
            nColor += texture2D(vTexture,vec2(vCoordinate.x+vChangeColor.b,vCoordinate.y+vChangeColor.b));
            nColor += texture2D(vTexture,vec2(vCoordinate.x+vChangeColor.b,vCoordinate.y-vChangeColor.b));
            nColor += texture2D(vTexture,vec2(vCoordinate.x-vChangeColor.b,vCoordinate.y+vChangeColor.b));
            nColor = nColor/13.0;
            gl_FragColor = nColor;
       }else if(vChangeType==5){
            vec2 textSize = vec2(vChangeColor.z,vChangeColor.z);
            vec2 mosaicSize = vec2(vChangeColor.x,vChangeColor.y);
            vec2 intXY = vec2(vCoordinate.x*textSize.x,vCoordinate.y*textSize.y);
            vec2 xymosaicSize = vec2(floor(intXY.x/mosaicSize.x)*mosaicSize.x,floor(intXY.y/mosaicSize.y)*mosaicSize.y);
            vec2 uvmosaicSize = vec2(xymosaicSize.x/textSize.x,xymosaicSize.y/textSize.y);
            gl_FragColor = texture2D(vTexture,uvmosaicSize);
       }else if(vChangeType==6){
         float  dis = distance(vec2(vPosition.x,vPosition.y/uXY),vec2(vChangeColor.x,vChangeColor.y));
           if(dis<vChangeColor.z){
              nColor = texture2D(vTexture,vec2(vCoordinate.x/2.0+0.1,vCoordinate.y/2.0+0.1));
           }
           gl_FragColor= nColor;
       }else if(vChangeType==7){//缩小
         gl_FragColor= nColor;
       }else if(vChangeType==8){//扭曲
                    float uD=80.0;//角度
                    float uR=0.5;//旋转半径
                    //传过来的纹理坐标
                     vec2 st=vCoordinate;
                     //模型
                    ivec2 ires=ivec2(512,512);
                    //向量的S坐标
                    float Res=float(ires.s);
                   //计算半径
                    float Radius=Res*uR;
                     //变换纹理
                    vec2 xy=Res*st;
                    vec2 dxy=xy-vec2(Res/2.0,Res/2.0);
                    float r =length(dxy);
                    //计算抛物线递减因子得到角度
                    float beta= atan(dxy.y,dxy.x)+radians(uD)*2.0*(-(r/Radius)*(r/Radius)+1.0);
                    vec2 xy1=xy;
                    //范围内有效果
                    if(r<=Radius){
                        xy1=Res/2.0+r*vec2(cos(beta),sin(beta));
                    }
                    st=xy1/Res;
                    gl_FragColor=vec4(texture2D(vTexture,st).rgb,1.0);
       }else if(vChangeType==9){//旋转
            gl_FragColor= texture2D(vTexture,vec2(1.0-vCoordinate.x,1.0-vCoordinate.y));
       }else if(vChangeType==10){
                 //当前取得对应纹理坐标的纹理值
                    vec2 tex= vCoordinate;
                   //模型
                   vec2  texSize=vec2(500.0,500.0);
                  ///下一个纹理坐标
                   vec2 upTex=vec2(tex.x-1.0/texSize.x, tex.y-1.0/texSize.y);
                   //取得模型对应纹理坐标的纹理值
                   vec4 newTex=texture2D(vTexture,upTex);
                   //相减
                 vec4 dleTex=nColor-newTex;
                 //灰度
                   vec4  bkColor=vec4(0.5,0.5,0.5,0.5);

                   gl_FragColor=dleTex+bkColor;
       }else if(vChangeType==11){
                       //算式(1-K)V1+K*V2
                        float k=0.5;//K
                        vec3 iRGB= nColor.rgb;
                        vec3 tRGB= vec3(0.0,0.0,0.0);
                        //mix（x，y，a）返回x和y的线性插值结果
                        gl_FragColor=vec4(mix(iRGB,tRGB,k),1.0);
       }else if(vChangeType==12){
              float d = 500.0;
              float data = 1.0/d;
              vec4 maxColor = vec4(-1);
              for(int i =-1;i<=1;i++){
                  for(int j=-1;j<=1;j++){
                      float x = vCoordinate.x+float(i)*data;
                      float y = vCoordinate.y+float(i)*data;
                      maxColor= max(maxColor,texture2D(vTexture,vec2(x,y)));
                  }
              }
             gl_FragColor = maxColor;
        }else if(vChangeType==13){
             float d = 500.0;
             float data = 1.0/d;
             vec4 maxColor = vec4(1.0);
             for(int i =-1;i<=1;i++){
                 for(int j=-1;j<=1;j++){
                     float x = vCoordinate.x+float(i)*data;
                     float y = vCoordinate.y+float(i)*data;
                     maxColor= min(maxColor,texture2D(vTexture,vec2(x,y)));
                 }
             }
            gl_FragColor = maxColor;
        }else{
          gl_FragColor =nColor;
       }
    }


}