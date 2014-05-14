package org.esprtimeouts.app.test;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.registerIdlingResources;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withText;
import com.google.android.apps.common.testing.ui.espresso.contrib.CountingIdlingResource;

import static com.google.common.base.Preconditions.checkNotNull;

import android.os.Looper;
import android.os.SystemClock;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;
import android.util.Log;
import android.util.LogPrinter;

import org.esprtimeouts.app.R;
import org.esprtimeouts.app.SyncActivity;

/**
 * Example for {@link CountingIdlingResource}. Demonstrates how to wait on a delayed response from
 * request before continuing with a test.
 */
@LargeTest
public class AdvancedSynchronizationTest extends ActivityInstrumentationTestCase2<SyncActivity> {


    @SuppressWarnings("deprecation")
    public AdvancedSynchronizationTest() {
        // This constructor was deprecated - but we want to support lower API levels.
        super("com.google.android.apps.common.testing.ui.testapp", SyncActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        this.getActivity();

    }

    public void testClickandwait() {
        // Request the "hello world!" text by clicking on the request button.
        onView(withId(R.id.request_button)).perform(click());

        long stime = System.currentTimeMillis();
        while(System.currentTimeMillis() - stime < 2000)
            Thread.yield();
        // Espresso waits for the UI message queue to go idle and then continues.
        Looper.getMainLooper().dump(new LogPrinter(Log.VERBOSE,"MainLooperDump"),"MainLooperDumpP2");
        // Will the check pass?.
        onView(withId(R.id.status_text)).check(matches(withText(R.string.hello_world)));
    }
}
