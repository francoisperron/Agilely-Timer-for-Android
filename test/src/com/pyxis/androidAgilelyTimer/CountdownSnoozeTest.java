package com.pyxis.androidAgilelyTimer;

import junit.framework.TestCase;

/**
 *
 */
public class CountdownSnoozeTest extends TestCase implements SoundPlayer{

    private CountdownSnooze snooze;
    private int nbTime;

    public void setUp(){
        snooze = new CountdownSnooze(3, this);
    }

    public void tearDown(){
        snooze = null;
        nbTime = 0;
    }


    public void testNoAlarm(){
        snooze.update(4);
        assertEquals("Nothing played", 0, nbTime);
    }

    public void testTimesUp(){
        snooze.update(3);
        assertEquals("Played once", 1, nbTime);
    }

    public void testTwoTimes(){
        snooze.update(0);
        assertEquals("Played 2 times", 2, nbTime);
    }

    public void testThreeSnooze(){
        snooze.update(-3);
        assertEquals("Played 3 times", 3, nbTime);
    }

    public void testAlotOfSnooze(){
        snooze.update(-27);
        assertEquals("Played 11 times", 11, nbTime);
    }

    public void play(int nbTime) {

        this.nbTime = nbTime;
    }
}
