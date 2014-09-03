package com.dennisideler.calculator.test.ui;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import com.dennisideler.calculator.MainActivity;
import com.dennisideler.calculator.R;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;

/**
 * Created by cqa on 23/06/14.
 */
public class CalculatorTest extends ActivityInstrumentationTestCase2<MainActivity> {
    public static final String TAG = "CalculatorTest";

    Activity activity;

    public CalculatorTest() {
        super(MainActivity. class);
    }

    @Override
    protected void setUp() throws Exception {
        Log.v(TAG, "setUp");
        super.setUp();
        activity = getActivity();
    }

    @Override
    protected void tearDown() throws Exception {
        Log.v(TAG, "tearDown");
        super.tearDown();
    }

    public void testCalculatorPlus() throws InterruptedException {
        onView(withId(R.id.button2)).perform(click());
        onView(withId(R.id.buttonAdd)).perform(click());
        onView(withId(R.id.button2)).perform(click());
        onView(withId(R.id.buttonEql)).perform(click());
        onView(withId(R.id.textViewAns)).check(matches(withText(is("4"))));
    }

    public void testInjectRotationOnClick() throws InterruptedException {
        onView(withId(R.id.button1)).perform(click());
        onView(withId(R.id.buttonDel)).perform(click());
        onView(withId(R.id.button2)).perform(click());
        onView(withId(R.id.textViewAns)).check(matches(withText(is("2"))));
    }
}