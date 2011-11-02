package com.pyxis.androidAgilelyTimer.activities;

import android.test.ActivityInstrumentationTestCase2;

public class BaseMenuActivityTest extends ActivityInstrumentationTestCase2<BaseMenuActivity> {
    public BaseMenuActivityTest() {
        super("com.pyxis.androidAgilelyTimer", BaseMenuActivity.class);
    }


    public void testNotNull() {
        final BaseMenuActivity baseMenu = new BaseMenuActivity();
                
        assertNotNull(baseMenu);
    }
}
