package com.example.android.bakingapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.android.bakingapp.fragments.RecipesFragment;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.rest.RecipeInterface;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    private static final String TAG = MainActivity.class.getSimpleName();

    private TextView mEmptyStateTextView;
    private ArrayList<Recipe> recipes;
    RecyclerView recipeListView;
    RecipeInterface apiService;
    private Recipe mCurrentRecipe;
    private RecipeAdapter mAdapter;
    public RecipeAdapter.RecipeAdapterOnClickHandler clickHandler;
    RecipesFragment recipesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recipesFragment = (RecipesFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_recipes);
        //recipesFragment = (RecipesFragment) getFragmentManager().findFragmentById(R.id.fragment_recipes);

    }


}
