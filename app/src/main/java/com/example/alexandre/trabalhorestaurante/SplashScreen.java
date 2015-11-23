package com.example.alexandre.trabalhorestaurante;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import android.os.Handler;

public class SplashScreen extends Activity implements Runnable {
    private final int DELAY = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_screen);

        Handler h = new Handler();
        h.postDelayed(this, DELAY);
    }

    public void run() {
        startActivity(new Intent(this, MainActivity.class));
    }
}
