package com.example.android.bakingapp;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private final static String SAMPLE_RECIPE_NAME = "Yellow Cake";

    @Rule
    public final ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    private IdlingResource mIdlingResource;


    @Before
    public void registerIdlingResource() {
        mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();
        IdlingRegistry.getInstance().register(mIdlingResource);
    }

    @Test
    public void testRecipeName() {

        onView(withId(R.id.recipe_name_recyclerview))
                .perform(RecyclerViewActions.scrollToPosition(2));

        onView(withText(SAMPLE_RECIPE_NAME))
           .check(matches(isDisplayed()));
    }

    @Test
    public void testClickStepAtPosition() throws Exception {
        /**
         * Clicks on a recyclerview item and checks it opens up the stepsactivity with the correct details.
         */
        onView(withId(R.id.recipe_name_recyclerview))
                .perform(RecyclerViewActions
                .actionOnItem(hasDescendant(withText(SAMPLE_RECIPE_NAME)), click()));

        onView(withId(R.id.recipe_ingredients_recyclerview))
                .check(matches(isDisplayed()));

        onView(withId(R.id.recipe_steps_recyclerview))
                .check(matches(isDisplayed()));
    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mIdlingResource);
        }
    }
}
