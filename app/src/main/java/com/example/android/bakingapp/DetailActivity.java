package com.example.android.bakingapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.android.bakingapp.adapters.StepsAdapter;
import com.example.android.bakingapp.fragments.StepsDetailFragment;
import com.example.android.bakingapp.fragments.StepsFragment;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.Step;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity implements StepsAdapter.StepsAdapterOnClickHandler{
    private static final String TAG = StepsActivity.class.getSimpleName();
    private ArrayList<Recipe> recipes;
    private Bundle recipeBundle;
    private ArrayList<Step> steps;
    StepsFragment recipesStepsFragment;
    private FragmentManager mFragmentManager;
    private Recipe mCurrentRecipe;
    private Step mCurrentStep;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mCurrentStep = extras.getParcelable(StepsActivity.STEP_DETAIL_KEY);
            mCurrentRecipe = extras.getParcelable(StepsActivity.RECIPE_DETAIL_KEY);
            if (mCurrentStep != null) {
                //setTitle(mCurrentStep.getDescription());

                StepsDetailFragment fragment = new StepsDetailFragment();
               // StepsAdapter stepsAdapter = new StepsAdapter(steps, this);
               fragment.setStep(mCurrentStep);
               fragment.setRecipe(mCurrentRecipe);
               // stepAdapter.setStepData(mCurrentRecipe.getSteps());
               // fragment.setStepsAdapter(stepAdapter);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.recipe_video_container, fragment).commit();
            }
        }

    }

    @Override
    public void onClick(Step step) {

    }
}