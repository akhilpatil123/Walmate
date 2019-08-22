package com.example.manisha.walmate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {
private TextView tv;
private ImageView iv;
Timer t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv=(TextView)findViewById(R.id.tv);
        iv=(ImageView)findViewById(R.id.iv);
        Animation myanim= AnimationUtils.loadAnimation(this,R.anim.fadein);
        tv.startAnimation(myanim);
        iv.startAnimation(myanim);
        t=new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent=new Intent(MainActivity.this,HomeScreen.class);
                startActivity(intent);
                finish();
            }
        },3000);

    }

}
