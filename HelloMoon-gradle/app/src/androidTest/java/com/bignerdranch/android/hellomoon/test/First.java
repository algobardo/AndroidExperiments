package com.bignerdranch.android.hellomoon.test;

import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;

import com.bignerdranch.android.hellomoon.HelloMoonActivity;
import com.bignerdranch.android.hellomoon.R;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;

/**
 * Created by mezzetti on 22/05/14.
 */
public class First extends ActivityInstrumentationTestCase2<HelloMoonActivity>{

    public First(){
        super(HelloMoonActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public First(Class<HelloMoonActivity> activityClass) {
        super(activityClass);
    }

    public void testSomething(){

    }
}
