package processing.test.tiltsensor;

import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class TiltSensor extends PApplet {

float ax, ay, az;
FallingBalls fallingballs[] = new FallingBalls[1];
AccelerometerManager accel;

public void setup (){
  for(int i = 0; i < fallingballs.length; i++) {
    int si = 100;
    float sp = si*0.2f;
    int x = 0;
    int y = 0;
    PImage yo = loadImage("guy.png");
    fallingballs[i] = new FallingBalls (yo, x, y, si, sp);
  }
accel = new AccelerometerManager(this);
  
 orientation(PORTRAIT);
}

public void draw() {
  background(0);
  for (int i = 0; i < fallingballs.length; i++) {
    fallingballs[i].update();
  }
}

public void resume() {
  if (accel != null) {
    accel.resume();
  }
}

    
public void pause() {
  if (accel != null) {
    accel.pause();
  }
}


public void shakeEvent(float force) {
  println("shake : " + force);
}
public void accelerationEvent(float x, float y, float z) {
//  println("acceleration: " + x + ", " + y + ", " + z);
  ay = y;
  az = z;
  redraw();
}
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


public void update(){
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




}
