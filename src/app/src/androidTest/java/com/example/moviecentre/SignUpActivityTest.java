package com.example.moviecentre;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class SignUpActivityTest {

    @Rule
    public ActivityTestRule<SignUpActivity> mActivityTestRule = new ActivityTestRule<SignUpActivity>(SignUpActivity.class);

    private SignUpActivity mSignUpActivity = null;


    //testing if sign up texview bring you to sign up activity
    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(LoginActivity.class.getName(), null , false);


    @Before
    public void setUp() throws Exception {
        mSignUpActivity = mActivityTestRule.getActivity();
    }
    @Test
    public void testLaunch(){
        View view = mSignUpActivity.findViewById(R.id.textViewLogin);
        assertNotNull(view);
    }
    @Test
    public void testLaunchOfLogIn(){
        assertNotNull(mSignUpActivity.findViewById(R.id.textViewLogin));
        onView(withId(R.id.textViewLogin)).perform(click());

        //wait 5 seconds untill an activity is launched else return null
        Activity logInActivity = getInstrumentation().waitForMonitorWithTimeout(monitor, 5000);

        assertNotNull(logInActivity);
        logInActivity.finish();
    }


    @Test
    public void onCreate() {
    }

    @Test
    public void onClick() {
    }

    @After
    public void tearDown() throws Exception {
        mSignUpActivity = null;
    }



}
