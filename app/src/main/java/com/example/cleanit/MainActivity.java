package com.example.cleanit;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setStatusBarColor(getResources().getColor(R.color.statBarMain, this.getTheme()));
         getSupportActionBar().hide();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Intent lance= new Intent(MainActivity.this, LoginActivity.class);
                startActivity(lance);
                finish();
            }
        }, 2000);

        Toast.makeText(this,"MainActivity",Toast.LENGTH_SHORT);
    }
}
