package com.example.android.bakingapp;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mCurrentRecipe = extras.getParcelable(MainActivity.DETAIL_KEY);

            // Create a new RecipeDetailFragment instance with the recipe got from the intent
            if (mCurrentRecipe != null) {
                setTitle(mCurrentRecipe.getName());
                StepsFragment fragment = new StepsFragment();
                StepsAdapter stepAdapter = new StepsAdapter(steps, this);
                fragment.setRecipe(mCurrentRecipe);
                stepAdapter.setStepData(mCurrentRecipe.getSteps());
                fragment.setStepsAdapter(stepAdapter);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.recipe_detail_container, fragment).commit();
            }
        }
        /*Recipe selectedRecipe= getIntent().getExtras().getParcelable("key");

        Bundle ingredientBundle=new Bundle();
        ingredientBundle.putParcelable("ingredientBundle", selectedRecipe);

        StepsFragment recipesStepsFragment=new StepsFragment();
        recipesStepsFragment.setArguments(ingredientBundle);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_steps,recipesStepsFragment,"step_fragment").commit();*/
       /* if (getIntent().getExtras() != null) {
            recipeBundle = getIntent().getExtras();
            recipes = recipeBundle.getParcelableArrayList("current_recipe");
        }

        if ((recipes == null) ) return;
        setTitle(recipes.get(0).getName());*/
        //mFragmentManager = getSupportFragmentManager();
       // recipesStepsFragment = (StepsFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_steps);
    }

    @Override
    public void onClick(Step step) {

    }
}
