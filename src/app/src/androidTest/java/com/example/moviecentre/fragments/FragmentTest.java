package com.example.moviecentre.fragments;

import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.widget.LinearLayout;

import com.example.moviecentre.HomeActivity;
import com.example.moviecentre.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class FragmentTest {

    @Rule
    public ActivityTestRule<HomeActivity> mActivityTestRule = new ActivityTestRule<HomeActivity>(HomeActivity.class);

    private HomeActivity mHomeActivity = null;

    @Before
    public void setUp() throws Exception {
        mHomeActivity = mActivityTestRule.getActivity();
    }

    @Test
    public void testLaunch(){

        View view = mHomeActivity.findViewById(R.id.view_pager);
        assertNotNull(view);
    }

    @After
    public void tearDown() throws Exception {
        mHomeActivity = null;
    }
}