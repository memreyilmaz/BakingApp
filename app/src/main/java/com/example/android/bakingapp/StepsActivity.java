package com.example.android.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.android.bakingapp.adapters.StepsAdapter;
import com.example.android.bakingapp.fragments.StepsFragment;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.Step;

import java.util.ArrayList;

public class StepsActivity extends AppCompatActivity implements StepsAdapter.StepsAdapterOnClickHandler{
    private static final String TAG = StepsActivity.class.getSimpleName();
    private ArrayList<Recipe> recipes;
    private Bundle recipeBundle;
    private ArrayList<Step> steps;
    StepsFragment recipesStepsFragment;
    private FragmentManager mFragmentManager;
    private Recipe mCurrentRecipe;
    private Step mCurrentStep;
    public static final String STEP_DETAIL_KEY = "step_detail";
    public static final String RECIPE_DETAIL_KEY = "recipe_detail";
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);

        if(findViewById(R.id.recipe_detail_container) != null) {
            // This LinearLayout will only initially exist in the two-pane tablet case
            mTwoPane = true;
        } else {
            mTwoPane =false;
        }
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mCurrentRecipe = extras.getParcelable(MainActivity.DETAIL_KEY);

            // Create a new RecipeDetailFragment instance with the recipe got from the intent
            if (mCurrentRecipe != null) {
                setTitle(mCurrentRecipe.getName());
                StepsFragment fragment = new StepsFragment();
                StepsAdapter stepsAdapter = new StepsAdapter(steps, this);
                fragment.setRecipe(mCurrentRecipe);
                stepsAdapter.setStepData(mCurrentRecipe.getSteps());
                fragment.setStepsAdapter(stepsAdapter);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.recipe_detail_container, fragment).commit();
            }
        }

       /* if (savedInstanceState == null) {
            Bundle mRecipeBundle = new Bundle();
            mRecipeBundle.putParcelableArrayList(RECIPE_DETAIL_KEY, mCurrentRecipe);
            mRecipeBundle.putInt(STEP_DETAIL_KEY, mCurrentStep);
            mRecipeBundle.putInt(Config.INTENT_KEY_STEP_COUNT, stepsCount);

            if (mTwoPane) {

                StepsFragment fragment = new StepsFragment();
                //mRecipeBundle.putInt(Config.INTENT_KEY_SELECTED_STEP, 0);
                fragment.setRecipe(mCurrentRecipe);

                fragment.setArguments(mRecipeBundle);
                mFragmentManager
                        .beginTransaction()
                        .replace(R.id.recipe_detail_container, fragment)
                   //     .addToBackStack(Config.STACK_RECIPE_DETAIL)
                        .commit();

                StepsDetailFragment recipeStepDetailFragment = new StepsDetailFragment();
               // mRecipeBundle.putInt(Config.INTENT_KEY_SELECTED_STEP, 0);
                fragment.setRecipe(mCurrentRecipe);

//                recipeStepDetailFragment.setArguments(mRecipeBundle);
                mFragmentManager
                        .beginTransaction()
                        .replace(R.id.recipe_video_container, recipeStepDetailFragment)
                 //       .addToBackStack(Config.STACK_RECIPE_STEP_DETAIL)
                        .commit();

            }/* else/* {

                StepsFragment fragment = new StepsFragment();
                fragment.setArguments(mRecipeBundle);
                mFragmentManager
                        .beginTransaction()
                        .replace(R.id.recipe_detail_container, fragment)
                        .addToBackStack(Config.STACK_RECIPE_DETAIL)
                        .commit();
            }*/
      //  }
    }

    @Override
    public void onClick(Step step) {
        Intent intent = new Intent(this, DetailActivity.class);
        mCurrentStep = step;

        Bundle bundle = new Bundle();

        bundle.putParcelable(STEP_DETAIL_KEY, mCurrentStep);
        bundle.putParcelable(RECIPE_DETAIL_KEY, mCurrentRecipe);
        intent.putExtras(bundle);
        startActivity(intent);

    }
}
