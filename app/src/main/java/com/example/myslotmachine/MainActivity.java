package com.example.myslotmachine;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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
    private static final int[] drawables = {R.drawable.slot1, R.drawable.slot2, R.drawable.slot3,
            R.drawable.slot4, R.drawable.slot5, R.drawable.slot6};

    public static final ArrayList<Integer> arrDrawables = new ArrayList<>();

    private int one;
    private int two;
    private int three;
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
                if (isRunning) {

                    isRunning = false;

                    if (one == two && one == three) {
                        infoTextView.setText(R.string.msg_bingo);
                        infoTextView.setTextColor(infoTextView.getResources().getColor(R.color.red));
                    } else if (one == two
                            || two == three
                            || one == three) {
                        infoTextView.setText(R.string.msg_hot_hot);
                        infoTextView.setTextColor(infoTextView.getResources().getColor(R.color.orange));
                    } else {
                        infoTextView.setText(R.string.msg_you_lose);
                        infoTextView.setTextColor(infoTextView.getResources().getColor(R.color.blue));
                    }

                    button.setText(R.string.btn_start);
                    button.setBackgroundColor(button.getResources().getColor(R.color.blue));

                } else {
                    infoTextView.setText("");
                    isRunning = true;
                    button.setText(R.string.btn_stop);
                    button.setBackgroundColor(button.getResources().getColor(R.color.red));
                }
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
                handler.postDelayed(this, 1000);
            }
        });
    }

    private void setImg() {
        int min = 0;
        int max = 5;
        one = (int) (Math.random() * (max - min + 1) + min);
        two = (int) (Math.random() * (max - min + 1) + min);
        three = (int) (Math.random() * (max - min + 1) + min);

        image1.setImageResource(arrDrawables.get(one));
        image2.setImageResource(arrDrawables.get(two));
        image3.setImageResource(arrDrawables.get(three));
    }
}