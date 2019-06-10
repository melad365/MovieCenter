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

public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<LoginActivity>(LoginActivity.class);

    private LoginActivity mLoginActivity = null;


    //testing if sign up texview bring you to sign up activity
    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(SignUpActivity.class.getName(), null , false);

    @Before
    public void setUp() throws Exception {
        mLoginActivity = mActivityTestRule.getActivity();
    }

    @Test
    public void testLaunch(){
        View view = mLoginActivity.findViewById(R.id.email_sign_in_button);
        assertNotNull(view);
    }

    @Test
    public void emailValidatorTrue(){
        assertTrue(LoginActivity.isEmailValid("anas.melad@yahoo.co.uk"));
    }
    @Test
    public void emailValidatorFalse(){
        assertFalse(LoginActivity.isEmailValid("anas.melad"));
    }
    @Test
    public void PasswordValidatorTrue(){
        assertTrue(LoginActivity.isPasswordValid("fgh78tu96"));
    }
    @Test
    public void PasswordValidatorFalse(){
        assertFalse(LoginActivity.isPasswordValid("abc"));
    }

    @Test
    public void testLaunchOfSignUp(){
        assertNotNull(mLoginActivity.findViewById(R.id.textViewSignup));
        onView(withId(R.id.textViewSignup)).perform(click());

        //wait 5 seconds until an activity is launched else return null
        Activity signUpActivity = getInstrumentation().waitForMonitorWithTimeout(monitor, 5000);

        assertNotNull(signUpActivity);

        signUpActivity.finish();
    }

    @Test
    public void testLaunchOFHomeActivity(){
        assertNotNull(mLoginActivity.findViewById(R.id.email_sign_in_button));
        onView(withId(R.id.email_sign_in_button)).perform(click());

        //wait 5 seconds until an activity is launched else return null
        Activity HomeActivity = getInstrumentation().waitForMonitorWithTimeout(monitor, 5000);

        assertNotNull(HomeActivity);

        HomeActivity.finish();
    }


    @After
    public void tearDown() throws Exception {
        mLoginActivity = null;
    }




}