package com.example.christian.sushi_samurai_2;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class GameView extends SurfaceView {
    GameLoopThread gameLoopThread;
    SurfaceHolder holder;

    public static int globalxSpeed = 8;

    Bitmap playerbmp;
    Bitmap coinbmp;
    Bitmap groundbmp;
    Bitmap sharks1bmp;
    Bitmap powerupshieldbmp;
    Bitmap buttonsbmp;
    Bitmap oceanbmp;
    int xx = 0;

    private List<Sushi> sushi = new ArrayList<Sushi>();
    private List<Player> player = new ArrayList<Player>();
    private List<Ground> ground = new ArrayList<Ground>();
    private List<Sharks> sharks = new ArrayList<Sharks>();
    private List<PowerUpShield> powerupshield = new ArrayList<PowerUpShield>();
    private List<Buttons> buttons = new ArrayList<Buttons>();

    public static int Sushicollected = 0;
    public static int Score = 0;
    public static int HighScore = 0;
    public static int AchievmentScore10000 = 0; // False

    private boolean PlayerGotPowerupShield = false;
    private int PlayerPowerupShieldTimer = 120;
    private static SharedPreferences prefs;

    private int timerCoins = 0;
    private int timerSpikes = 0;
    private int timerPowerupShield = 0;

    private int timerRandomPowerupShield = 0;
    private int timerRandomSpikes = 1;

    private int lastScore = 0;

    private String saveAchievmentScore10000 = "Achievmentscore10000";
    private String saveScore = "Highscore";
    private String Menu = "Running";




    public GameView(Context context) {
        super(context);
        prefs = context.getSharedPreferences("your.ligr.endlessrunninggame", context.MODE_PRIVATE);

        String spackage ="your.ligr.endlessrunninggame";

        HighScore = prefs.getInt(saveScore , 0);
        AchievmentScore10000 = prefs.getInt(saveAchievmentScore10000, 0);

        gameLoopThread = new GameLoopThread(this);

        holder = getHolder();
        holder.addCallback(new Callback() {

            public void surfaceDestroyed(SurfaceHolder arg0) {
                // TODO Auto-generated method stub
                Score =0;
                Sushicollected = 0;
                prefs.edit().putInt(saveScore,HighScore).commit();
                prefs.edit().putInt(saveAchievmentScore10000,AchievmentScore10000).commit();
                gameLoopThread.running = false;
            }

            public void surfaceCreated(SurfaceHolder arg0) {
                // TODO Auto-generated method stub
                gameLoopThread.setRunning();
                gameLoopThread.start();


            }

            public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub

            }

        });
        playerbmp = BitmapFactory.decodeResource(getResources(), R.drawable.rsamurai);
        coinbmp = BitmapFactory.decodeResource(getResources(), R.drawable.sushicoin);
        groundbmp = BitmapFactory.decodeResource(getResources(), R.drawable.ground);
        sharks1bmp = BitmapFactory.decodeResource(getResources(), R.drawable.blueshark);
        powerupshieldbmp = BitmapFactory.decodeResource(getResources(), R.drawable.powerupshield);
        buttonsbmp = BitmapFactory.decodeResource(getResources(), R.drawable.buttons);

        powerupshield.add(new PowerUpShield(this,powerupshieldbmp,600,32));
        sharks.add(new Sharks(this,sharks1bmp,1000,0));
        sharks.add(new Sharks(this, sharks1bmp, 1800, 0));
        player.add(new Player(this,playerbmp,50,50));
        sushi.add(new Sushi(this, coinbmp, 120, 32));

        sushi.add(new Sushi(this, coinbmp, 50, 0));
        // TODO Auto-generated constructor stub
    }


    @Override
    public boolean onTouchEvent(MotionEvent e){
        for(Player pplayer: player)
        {
            pplayer.ontouch();
        }
        if (Menu =="Mainmenu")
        {
            for(int i = 0; i < buttons.size(); i++){
                if (buttons.get(i).getState() == 1){   // Restart
                    if ((buttons.get(i).getX()<e.getX() && buttons.get(i).getX()+84>e.getX())){
                        if (buttons.get(i).getY()<e.getY() && buttons.get(i).getY()+32>e.getY()){
                            Menu = "Running";
                            startGame();}
                    }
                }

            }
        }
        return false;

    }


    public void update(){
        if(Menu=="Running"){
            Score += 5;
            lastScore = Score;
            updatetimers();
            deleteground();

            if (Score >= 10000 && AchievmentScore10000 == 0)
            {
                AchievmentScore10000 = 1;
            }
            if (Score > HighScore)
            {
                HighScore = Score;
            }}
    }

    public void updatetimers(){

        timerCoins ++;
        timerSpikes ++;
        timerPowerupShield ++;
        if (Menu =="Running"){
            if (PlayerGotPowerupShield){
                PlayerPowerupShieldTimer --;
                if (PlayerPowerupShieldTimer <= 0)
                {
                    PlayerGotPowerupShield = false;
                }
            }

            switch(timerRandomPowerupShield){

                case 0:
                    if(timerPowerupShield >= 150){
                        powerupshield.add(new PowerUpShield(this,powerupshieldbmp,this.getWidth()+32,0));
                        Random randomPowerupShield = new Random();
                        timerRandomPowerupShield = randomPowerupShield.nextInt(3);
                        timerPowerupShield = 0;

                    }break;
                case 1:
                    if(timerPowerupShield >= 250){
                        powerupshield.add(new PowerUpShield(this,powerupshieldbmp,this.getWidth()+32,0));
                        Random randomPowerupShield = new Random();
                        timerRandomPowerupShield = randomPowerupShield.nextInt(3);
                        timerPowerupShield = 0;

                    }break;
                case 2:
                    if(timerPowerupShield >= 350){
                        powerupshield.add(new PowerUpShield(this,powerupshieldbmp,this.getWidth()+32,0));
                        Random randomPowerupShield = new Random();
                        timerRandomPowerupShield = randomPowerupShield.nextInt(3);
                        timerPowerupShield = 0;

                    }break;
            }
            switch(timerRandomSpikes){

                case 0:
                    if(timerSpikes >= 125)
                    {
                        sharks.add(new Sharks(this, sharks1bmp, this.getWidth() + 24, 0));
                        Random randomSpikes = new Random();
                        timerRandomSpikes = randomSpikes.nextInt(3);
                        timerSpikes = 0;
                    }break;
                case 1:
                    if(timerSpikes >= 175)
                    {
                        sharks.add(new Sharks(this,sharks1bmp,this.getWidth()+24,0));
                        Random randomSpikes = new Random();
                        timerRandomSpikes = randomSpikes.nextInt(3);
                        timerSpikes = 0;
                    }break;

                case 2:
                    if(timerSpikes >= 100)
                    {

                        sharks.add(new Sharks(this, sharks1bmp, this.getWidth() + 24, 0));
                        Random randomSpikes = new Random();
                        timerRandomSpikes = randomSpikes.nextInt(3);
                        timerSpikes = 0;
                    }
                    break;
            }

            if (timerCoins >= 100){

                Random randomCoin = new Random();
                int random;
                random = randomCoin.nextInt(3);

                switch(random){

                    case 1:
                        int currentcoin = 1;
                        int xx = 1;
                        while(currentcoin <= 5){

                            sushi.add(new Sushi(this, coinbmp, this.getWidth() + (32 * xx), 32));

                            currentcoin++;
                            xx++;
                        }
                        break;

                    case 2:
                        currentcoin = 1;

                        sushi.add(new Sushi(this, coinbmp, this.getWidth() + 32, 32));
                        sushi.add(new Sushi(this, coinbmp, this.getWidth() + 64, 48));
                        sushi.add(new Sushi(this, coinbmp, this.getWidth() + 96, 32));
                        sushi.add(new Sushi(this, coinbmp, this.getWidth() + 128, 48));
                        sushi.add(new Sushi(this, coinbmp, this.getWidth() + 160, 32));

                }
                timerCoins = 0;
            }
        }
    }


    public void addground(){

        while(xx < this.getWidth()+Ground.width)
        {
            ground.add(new Ground(this,groundbmp,xx,0));

            xx += groundbmp.getWidth();
        }

    }

    public void deleteground(){

        for (int i = ground.size()-1;i >= 0; i--)
        {
            int groundx = ground.get(i).returnX();

            if (groundx<=-Ground.width){
                ground.remove(i);
                ground.add(new Ground(this,groundbmp,groundx+this.getWidth()+Ground.width,0));
            }
        }

    }
    public void startGame(){
        for(int i = 0; i < buttons.size(); i++){
            buttons.remove(i);
        }
        player.add(new Player(this,playerbmp,50,50));

    }

    public void endGame(){
        Menu  = "Mainmenu";
        timerCoins =0;
        timerSpikes =0;
        timerPowerupShield=0;
        buttons.add(new Buttons(this,buttonsbmp,this.getWidth()/2-64,this.getHeight()/2,3));
        buttons.add(new Buttons(this,buttonsbmp,this.getWidth()/2-64,this.getHeight()/2+48,1));
        for(int i = 0; i < sushi.size(); i++)
        {
            sushi.remove(i);
        }
        for(int i = 0; i < sharks.size(); i++)
        {
            sharks.remove(i);
        }
        for(int i = 0; i < powerupshield.size(); i++)
        {
            powerupshield.remove(i);
        }
        player.remove(0);

    }

    @Override
    protected void onDraw(Canvas canvas) {

        update();
        canvas.drawColor(Color.CYAN);

        // If the menu is Main menu, draw the button
        if (Menu=="Mainmenu")
        {
            for(Buttons bbuttons: buttons)
            {
                bbuttons.onDraw(canvas);

            }

        }
        // If the game is running, draw it
        if (Menu == "Running"){
            addground();
            Paint textpaint = new Paint();

            textpaint.setTextSize(48);

            canvas.drawText("Score: "+String.valueOf(Score), 0, 32, textpaint);
            canvas.drawText("High Score: "+String.valueOf(HighScore), 0, 80, textpaint);
            canvas.drawText("Sushi Collected: "+String.valueOf(Sushicollected), 0, 130, textpaint);

            for(Ground gground: ground){
                gground.onDraw(canvas);
            }

            for(Player pplayer: player)
            {
                pplayer.onDraw(canvas);
            }
            for(int i = 0; i < sharks.size(); i++)
            {

                sharks.get(i).onDraw(canvas);
                Rect playerr = player.get(0).GetBounds();
                Rect spikesr = sharks.get(i).GetBounds();



                if (sharks.get(i).checkCollision(playerr, spikesr)){
                    if(!PlayerGotPowerupShield){

                        Score = 0;
                        Sushicollected=0;
                        endGame();}
                    else{
                        sharks.remove(i);
                        PlayerGotPowerupShield = false;
                    }
                    break;
                }


            }
            for(int i = 0; i < sushi.size(); i++)
            {

                sushi.get(i).onDraw(canvas);
                Rect playerr = player.get(0).GetBounds();
                Rect coinr = sushi.get(i).GetBounds();

                if (sushi.get(i).returnX() < 0-32){
                    sushi.remove(i);

                }
                else if (sushi.get(i).checkCollision(playerr, coinr)){
                    sushi.remove(i);
                    Score += 500;
                    Sushicollected+=1;

                }
            }
            for(int i = 0; i < powerupshield.size(); i++)
            {

                powerupshield.get(i).onDraw(canvas);
                Rect playerr = player.get(0).GetBounds();
                Rect powerupshieldr = powerupshield.get(i).GetBounds();

                if (powerupshield.get(i).returnX() < 0-32){
                    powerupshield.remove(i);

                }
                else if(powerupshield.get(i).checkCollision(playerr, powerupshieldr)){
                    powerupshield.remove(i);
                    PlayerGotPowerupShield = true;
                    PlayerPowerupShieldTimer = 120;

                }




            }

        }
        if (Menu=="Mainmenu")
        {Paint textpaint = new Paint();

            textpaint.setTextSize(32);

            canvas.drawText("Score: "+String.valueOf(lastScore), canvas.getWidth()/2, canvas.getHeight()/2, textpaint);

        }
    }







}
