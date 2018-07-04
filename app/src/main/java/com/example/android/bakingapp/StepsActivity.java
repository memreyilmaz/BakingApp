package com.example.android.bakingapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.android.bakingapp.fragments.StepsFragment;
import com.example.android.bakingapp.model.Recipe;

import java.util.ArrayList;

public class StepsActivity extends AppCompatActivity{
    private static final String TAG = StepsActivity.class.getSimpleName();
    private ArrayList<Recipe> recipes;
    private Bundle recipeBundle;
    StepsFragment recipesStepsFragment;
    private FragmentManager mFragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);
        if (getIntent().getExtras() != null) {
            recipeBundle = getIntent().getExtras();
            recipes = recipeBundle.getParcelableArrayList("current_recipe");
        }

        if ((recipes == null) ) return;
        setTitle(recipes.get(0).getName());
        //mFragmentManager = getSupportFragmentManager();
        recipesStepsFragment = (StepsFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_steps);
    }

}
