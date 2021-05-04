package com.example.myslotmachine;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private boolean isRunning = false;
    private TextView infoTextView;
    private ImageView image1, image2, image3;
    private Button button;

    public static final ArrayList<Integer> arrDrawables = new ArrayList<>();

    private int one;
    private int two;
    private int three;
    private int countScore;

    private CountDownTimer timer;
    private final long time = 3000;

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);
        button = findViewById(R.id.button);
        infoTextView = findViewById(R.id.textView);

        arrDrawables.add(R.drawable.slot1);
        arrDrawables.add(R.drawable.slot2);
        arrDrawables.add(R.drawable.slot3);
        arrDrawables.add(R.drawable.slot4);
        arrDrawables.add(R.drawable.slot5);
        arrDrawables.add(R.drawable.slot6);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                infoTextView.setText("");
                isRunning = true;
                button.setVisibility(View.INVISIBLE);
                downloadCountDounTimer(time);
                timer.start();

            }
        });

        setImg();
        runGame();
    }

    private void runGame() {

        final Handler handler = new Handler();

        handler.post(new Runnable() {

            @Override
            public void run() {

                if (isRunning) {
                    setImg();
                }

                handler.postDelayed(this, 200);
            }
        });
    }

    private void downloadCountDounTimer(long millisec) {

        timer = new CountDownTimer(millisec, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                isRunning = false;
                showResult();
            }
        };
    }

    private void setImg() {
        int max = 5;
        one = (int) (Math.random() * (max + 1));
        two = (int) (Math.random() * (max + 1));
        three = (int) (Math.random() * (max + 1));

        image1.setImageResource(arrDrawables.get(one));
        image2.setImageResource(arrDrawables.get(two));
        image3.setImageResource(arrDrawables.get(three));
    }

    @SuppressLint("StringFormatMatches")
    private void showResult() {
        isRunning = false;

        int maxScore = 10;
        if (one == two && one == three) {
            countScore += maxScore;
            infoTextView.setText(String.format(getString(R.string.msg), maxScore, countScore));
        } else if (one == two
                || two == three
                || one == three) {
            int minScore = 5;
            countScore += minScore;
            infoTextView.setText(String.format(getString(R.string.msg), minScore, countScore));
        } else {
            infoTextView.setText(R.string.msg_you_lose);
        }

        button.setText(R.string.btn_text);
        button.setVisibility(View.VISIBLE);
    }
}