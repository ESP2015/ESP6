class FallingBalls{
int x, y, esize;
float speed;
PImage img;
FallingBalls(PImage yo, int xa, int ya, int si, float sp){
x = xa;
y = ya;
esize = si;
speed = sp;
img = yo;
}


void update(){
image(img, x, y, esize, esize);
x-=ax*speed;
y+=ay*speed;

//-------COLLISION DETECTION------------
 if(x > displayWidth - esize){
   x = displayWidth-esize;
 }
 if(x < 0 + esize){
   x = 0 + esize;
 }
 if(y > displayHeight - esize) {
   y = displayHeight - esize;
 }
  if(y < 0 + esize){
   y = 0 + esize;
 }
 //--------------COLLISION DETECTION ENDS--------------

}

}



