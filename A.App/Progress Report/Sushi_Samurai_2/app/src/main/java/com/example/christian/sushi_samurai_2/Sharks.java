package com.example.christian.sushi_samurai_2;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Sharks{

    private int x,y;
    private Bitmap bmp;
    private GameView gameview;
    private Rect playerr;
    private Rect sharksr;

    public Sharks(GameView gameview, Bitmap sharks1bmp,int x,int y){
        this.gameview = gameview;
        this.bmp = sharks1bmp;
        this.x = x;
    }
    public boolean checkCollision(Rect playerr, Rect spikesr){

        this.playerr = playerr;
        this.sharksr = spikesr;

        return Rect.intersects(playerr, spikesr);
    }
    public Rect GetBounds()
    {
        return new Rect(this.x,this.y,this.x+bmp.getWidth(),this.y+bmp.getHeight());
    }

    public void Update(){
        x-=gameview.globalxSpeed;
        y = gameview.getHeight()-Ground.height-bmp.getHeight();
    }
    public int returnX(){
        return x;
    }

    public void onDraw(Canvas canvas){
        Update();
        int srcX = bmp.getWidth();
        Rect src = new Rect(0,0,srcX,bmp.getHeight());
        Rect dst = new Rect(x,y,x+bmp.getWidth(),y+bmp.getHeight());
        canvas.drawBitmap(bmp,src,dst,null);
    }
}
