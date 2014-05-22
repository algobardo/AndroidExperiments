package com.example.android.musicplayer.test;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onData;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.pressBack;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.closeSoftKeyboard;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.typeText;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import com.google.android.apps.common.testing.ui.testapp.R;
import com.google.android.apps.common.testing.ui.testapp.SimpleActivity;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;

/**
 * Highlights basic
 * {@link com.google.android.apps.common.testing.ui.espresso.Espresso#onView(org.hamcrest.Matcher)}
 * functionality.
 */
@LargeTest
public class BasicTest extends ActivityInstrumentationTestCase2<SimpleActivity> {

    public void setUp() throws Exception {
        super.setUp();
        // Espresso will not launch our activity for us, we must launch it via getActivity().
        getActivity();
    }

    public void testSimpleClickAndCheckText() {
        onView(withId(R.id.button_simple))
                .perform(click());

        onView(withId(R.id.text_simple))
                .check(matches(withText("Hello Espresso!")));
    }

}
