float ax, ay, az;
FallingBalls fallingballs[] = new FallingBalls[1];
AccelerometerManager accel;

void setup (){
  for(int i = 0; i < fallingballs.length; i++) {
    int si = 100;
    float sp = si*0.2;
    int x = 0;
    int y = 0;
    PImage yo = loadImage("guy.png");
    fallingballs[i] = new FallingBalls (yo, x, y, si, sp);
  }
accel = new AccelerometerManager(this);
  
 orientation(PORTRAIT);
}

void draw() {
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
