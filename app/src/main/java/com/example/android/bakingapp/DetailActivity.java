package com.example.android.bakingapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

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
    private FloatingActionButton nextButton;
    private FloatingActionButton previousButton;

    private int stepId;
    private int stepNr;

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
        steps = mCurrentRecipe.getSteps();
        stepNr = steps.size();
        stepId = mCurrentStep.getId();
        nextButton = findViewById(R.id.recipe_next_button);
        previousButton = findViewById(R.id.recipe_previous_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (steps != null) {
                    int maxStepId = stepNr - 1;
                    if (stepId < maxStepId) {
                        int index = ++stepId;
                        mCurrentStep = steps.get(index);
                        StepsDetailFragment fragment = new StepsDetailFragment();
                        fragment.setStep(mCurrentStep);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.recipe_video_container, fragment).commit();
                    } else {
                        stepId = 0;
                        mCurrentStep = steps.get(stepId);
                        StepsDetailFragment fragment = new StepsDetailFragment();
                        fragment.setStep(mCurrentStep);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.recipe_video_container, fragment).commit();
                    }
                }

            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (steps != null) {
                    int totStepNo = stepNr - 1;
                    if (stepId < totStepNo && stepId > 0) {
                        int index = --stepId;
                        mCurrentStep = steps.get(index);
                        StepsDetailFragment fragment = new StepsDetailFragment();
                        fragment.setStep(mCurrentStep);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.recipe_video_container, fragment).commit();
                    } else {
                        mCurrentStep = steps.get(stepId);
                        StepsDetailFragment fragment = new StepsDetailFragment();
                        fragment.setStep(mCurrentStep);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.recipe_video_container, fragment).commit();
                    }
                }
            }
        });

    }


    @Override
    public void onClick(Step step) {

    }
}