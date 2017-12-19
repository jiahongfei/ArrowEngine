package com.andrjhf.arrow.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.andrjhf.arrow.demo.login.LoginActivity;
import com.andrjhf.arrow.demo.weather.WeatherActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.textView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, WeatherActivity.class));
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
    }
}
