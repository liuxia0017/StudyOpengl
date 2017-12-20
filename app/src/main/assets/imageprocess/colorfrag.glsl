precision mediump float;

uniform sampler2D vTexture;
uniform int vIsHalf;
uniform int vChangeType;
uniform vec3 vChangeColor;

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
            }else if(vChangeType==5){
            }else if(vChangeType==6){
            }else if(vChangeType==7){
            }else if(vChangeType==8){
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
       }else if(vChangeType==6){
       }else if(vChangeType==7){
       }else if(vChangeType==8){
       }else{
          gl_FragColor =nColor;
       }
    }


}