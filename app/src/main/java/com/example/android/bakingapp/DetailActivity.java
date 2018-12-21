package com.example.android.bakingapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.android.bakingapp.fragments.DetailFragment;
import com.example.android.bakingapp.fragments.StepsFragment;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.Step;

public class DetailActivity extends AppCompatActivity implements FragmentInteractionListener{

    StepsFragment stepsFragment;
    DetailFragment detailFragment;
    private Recipe mCurrentRecipe;
    private Bundle recipebundle;
    private Step mCurrentStep;
    public static final String STEP_DETAIL_KEY = "step_detail";
    public static final String RECIPE_DETAIL_KEY = "recipe_detail";

    private FragmentManager mFragmentManager;
    public static final String FRAGMENT_DETAIL = "step_detail_fragment";
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);

        if(findViewById(R.id.activity_steps_tablet) != null) {
            mTwoPane = true;
        } else {
            mTwoPane =false;
        }
        mFragmentManager = getSupportFragmentManager();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mCurrentRecipe = extras.getParcelable(MainActivity.DETAIL_KEY);
        }
        recipebundle = new Bundle();
        if (mCurrentRecipe != null) {
            setTitle(mCurrentRecipe.getName());
        }
        if (savedInstanceState == null) {
            if (mTwoPane){
                setStepsFragment();
                setDetailsFragmentForTablet();
            }else {
                setStepsFragment();
            }
        }
    }
    public void setStepsFragment(){
        recipebundle.putParcelable(RECIPE_DETAIL_KEY, mCurrentRecipe);
        stepsFragment = new StepsFragment();
        stepsFragment.setArguments(recipebundle);
        stepsFragment.setRecipe(mCurrentRecipe);
        mFragmentManager.beginTransaction()
                .add(R.id.recipe_detail_container, stepsFragment)
                .commit();
    }
    private void setDetailsFragmentForTablet() {
        detailFragment = new DetailFragment();
        mCurrentStep = mCurrentRecipe.getSteps().get(0);
        recipebundle.putParcelable(RECIPE_DETAIL_KEY, mCurrentRecipe);
        recipebundle.putParcelable(STEP_DETAIL_KEY, mCurrentStep);
        detailFragment.setArguments(recipebundle);
        mFragmentManager.beginTransaction()
                .add(R.id.recipe_video_container, detailFragment, FRAGMENT_DETAIL)
                .commit();
    }
    @Override
    public void onStepClicked(Step step) {
        mCurrentStep = step;
        recipebundle.putParcelable(STEP_DETAIL_KEY, mCurrentStep);
        recipebundle.putParcelable(RECIPE_DETAIL_KEY, mCurrentRecipe);

        if (!mTwoPane) {
            detailFragment = new DetailFragment();
            detailFragment.setArguments(recipebundle);
            mFragmentManager.beginTransaction()
                    .replace(R.id.recipe_detail_container, detailFragment, FRAGMENT_DETAIL)
                    .addToBackStack(null)
                    .commit();
        } else {
            detailFragment = new DetailFragment();
            detailFragment.setArguments(recipebundle);
            mFragmentManager.beginTransaction()
                    .replace(R.id.recipe_video_container, detailFragment, FRAGMENT_DETAIL)
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }
}