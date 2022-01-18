package com.example.mobil_veteriner_uygulamasi.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import com.example.mobil_veteriner_uygulamasi.R;

public class splashScreenActivity extends AppCompatActivity {

    private ProgressBar progres;
    private int progdurum=1;
    private Handler handler =new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        progres = (ProgressBar)findViewById(R.id.progres);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (progdurum<100)
                {
                    progdurum +=1;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progres.setProgress(progdurum);

                        }
                    });
                    try
                    {
                        Thread.sleep(10);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }


                Intent intent = new Intent(splashScreenActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }).start();
    }
}