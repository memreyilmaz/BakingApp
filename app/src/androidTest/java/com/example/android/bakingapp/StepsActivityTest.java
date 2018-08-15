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
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class StepsActivityTest {

    private IdlingResource mIdlingResource;
    private final static String SAMPLE_RECIPE_NAME = "Yellow Cake";
    private static final String SAMPLE_STEP_SHORT_DESC = "Recipe Introduction";

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void registerIdlingResource() {
        mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();
        IdlingRegistry.getInstance().register(mIdlingResource);
    }

    @Test
    public void testClickStepAtPosition() throws Exception {
        /**
         * Clicks on a recipe recyclerview item and on a steps recyclerview item in order and checks it opens up the detailactivity with the correct details.
         */
        onView(withId(R.id.recipe_name_recyclerview))
                .perform(RecyclerViewActions.actionOnItem
                        (hasDescendant(withText(SAMPLE_RECIPE_NAME)), click()));

        onView(withId(R.id.recipe_steps_recyclerview))
                .check(matches(hasDescendant(withText(SAMPLE_STEP_SHORT_DESC))));

        onView(withId(R.id.recipe_steps_recyclerview))
                .perform(scrollTo());

        onView(withId(R.id.recipe_steps_recyclerview))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.recipe_description_video))
                .check(matches(isDisplayed()));

        onView(withId(R.id.step_description_detail_textview))
                .check(matches(withText(SAMPLE_STEP_SHORT_DESC)));
    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mIdlingResource);
        }
    }
}