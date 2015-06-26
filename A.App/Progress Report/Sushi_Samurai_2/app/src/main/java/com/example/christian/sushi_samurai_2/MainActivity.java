package com.example.christian.sushi_samurai_2;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
    private GameView gameView;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameView = new GameView(this);
        setContentView(gameView);
    }

    @Override
    public void onPause(){
        super.onPause();
        gameView.gameLoopThread.running = false;
        finish();

    }
}