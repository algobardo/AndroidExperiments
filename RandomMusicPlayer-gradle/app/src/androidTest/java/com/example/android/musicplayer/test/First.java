package com.example.android.musicplayer.test;

import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;
import com.example.android.musicplayer.MainActivity;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.onData;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.pressBack;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.registerIdlingResources;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.closeSoftKeyboard;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.typeText;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.*;

import com.example.android.musicplayer.R;

/**
 * Created by mezzetti on 22/05/14.
 */
public class First extends ActivityInstrumentationTestCase2<MainActivity> {
    MainActivity activity;

    public First(){
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        this.activity = getActivity();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public First(Class<MainActivity> activityClass) {
        super(activityClass);
    }

    public void testInitiallyNoCurrentTrack() {
        // Initially, no track should be playing
        onView(withId(R.id.currentTrack)).check(matches(withText("")));
    }

    public void testPlayShowsCurrentTrack() {
        // Click on the eject button and press "Play!" on the displayed dialog
        onView(withId(R.id.ejectbutton)).perform(click());
        onView(withId(android.R.id.button1)).perform(click());

        // Now the application should be loading the suggested song
        onView(withId(R.id.currentTrack)).check(matches(withText(MainActivity.SUGGESTED_URL + " (loading)")));

        // Wait till it is no more loading
        registerIdlingResources(new StatusIdlingResource("StatusIdlingResource", activity, null, "loading"));

        // Now the application should be playing the suggested song
        onView(withId(R.id.currentTrack)).check(matches(withText(MainActivity.SUGGESTED_URL + " (playing)")));

        // Click on the rewind button
        onView(withId(R.id.rewindbutton)).perform(click());

        // Now the application should have started playing the (same) song from the beginning
        onView(withId(R.id.currentTrack)).check(matches(withText(MainActivity.SUGGESTED_URL + " (playing)")));
    }
}
