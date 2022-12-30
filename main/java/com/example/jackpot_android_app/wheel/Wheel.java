package com.example.jackpot_android_app.wheel;

import com.example.jackpot_android_app.R;


public class Wheel extends Thread {

    public interface WheelListener {
        void newImage(int img);
    }

    private static final int[] IMAGES = {R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3,
            R.drawable.image4,
            R.drawable.image5 };

    private int currentIndexOfImage;
    private WheelListener wheelListener;
    private long duration;
    private long startFrom;
    private boolean isStarted;

    public Wheel(WheelListener wheelListener, long frameDuration, long startIn) {
        this.wheelListener = wheelListener;
        this.duration = frameDuration;
        this.startFrom = startIn;
        currentIndexOfImage = 0;
        isStarted = true;
    }

    public void stopWheel() {
        isStarted = false;
    }

    public int getCurrentIndexOfImage() {
        return currentIndexOfImage;
    }

    public void nextImg() {
        currentIndexOfImage++;

        if (currentIndexOfImage == IMAGES.length) {
            currentIndexOfImage = 0;
        }
    }

    @Override
    public void run() {
        try {
            Thread.sleep(startFrom);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while(isStarted) {
            try {
                Thread.sleep(duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            nextImg();

            if (wheelListener != null) {
                wheelListener.newImage(IMAGES[currentIndexOfImage]);
            }
        }
    }
}