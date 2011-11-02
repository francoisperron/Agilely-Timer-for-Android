package com.pyxis.androidAgilelyTimer;


public class Alarm implements SoundPlayer{

    private int soundItem;

    public Alarm(int soundItem) {
         this.soundItem = soundItem;
    }


    public void play(int nbTime) {
        SoundManager.playSound(this.soundItem, nbTime - 1);
    }
}
