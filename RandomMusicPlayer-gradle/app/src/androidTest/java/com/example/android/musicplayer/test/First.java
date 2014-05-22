package com.example.android.musicplayer.test;

import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;

import com.example.android.musicplayer.MainActivity;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;

/**
 * Created by mezzetti on 22/05/14.
 */
public class First extends ActivityInstrumentationTestCase2<MainActivity>{

    public First(){
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public First(Class<MainActivity> activityClass) {
        super(activityClass);
    }

    public void testSomething(){

    }
}
