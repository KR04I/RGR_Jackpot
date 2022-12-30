package com.example.jackpot_android_app;

import android.media.MediaPlayer;
import android.os.Bundle;
import com.example.jackpot_android_app.wheel.Wheel;
import androidx.appcompat.app.AppCompatActivity;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView textMessage;
    private ImageView image1, image2, image3;
    private Wheel wheel1, wheel2, wheel3;
    private Button buttonStartStop;
    private boolean flagStart;

    public static final Random RANDOM = new Random();

    public static long getRandomNumber(long min, long max) {
        return min + (long) (RANDOM.nextDouble() * (max - min));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image1 = (ImageView) findViewById(R.id.img1);
        image2 = (ImageView) findViewById(R.id.img2);
        image3 = (ImageView) findViewById(R.id.img3);
        buttonStartStop = (Button) findViewById(R.id.btn);
        textMessage = (TextView) findViewById(R.id.msg);

        buttonStartStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CountDownTimer timer = new CountDownTimer(4000, 1000) {
                    @Override
                    public void onTick(long l) {}

                    @Override
                    public void onFinish() {
                        if (flagStart) {
                            wheel1.stopWheel();
                            wheel2.stopWheel();
                            wheel3.stopWheel();

                            if (wheel1.getCurrentIndexOfImage() == wheel2.getCurrentIndexOfImage() &&
                                    wheel2.getCurrentIndexOfImage() == wheel3.getCurrentIndexOfImage()) {
                                textMessage.setText("JACKPOT! You win, happy new year!");
                                addSoundEffect(R.raw.sound_win);
                            } else {
                                textMessage.setText("You lose");
                                addSoundEffect(R.raw.sound_lose);

                            }

                            buttonStartStop.setVisibility(View.VISIBLE);
                            buttonStartStop.setText("Играть");
                            flagStart = false;

                        }
                    }
                };

                if (!flagStart) {
                    startProgram(timer);
                }
            }
        });
    }

    private void addSoundEffect(int soundId) {
        MediaPlayer mediaPlayer = MediaPlayer.create(this, soundId);
        mediaPlayer.start();
    }

    private void startProgram(CountDownTimer timer) {
        wheel1 = new Wheel(new Wheel.WheelListener() {
            @Override
            public void newImage(final int img) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        image1.setImageResource(img);
                    }
                });
            }
        }, 100, getRandomNumber(0, 200));
        wheel1.start();

        wheel2 = new Wheel(new Wheel.WheelListener() {
            @Override
            public void newImage(final int img) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        image2.setImageResource(img);
                    }
                });
            }
        }, 100, getRandomNumber(150, 300));
        wheel2.start();

        wheel3 = new Wheel(new Wheel.WheelListener() {
            @Override
            public void newImage(final int img) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        image3.setImageResource(img);
                    }
                });
            }
        }, 100, getRandomNumber(150, 300));
        wheel3.start();

        buttonStartStop.setVisibility(View.GONE);
        textMessage.setText("Wait...");
        flagStart = true;

        timer.start();
    }
}