package com.example.android.bakingapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.StepsActivity;
import com.example.android.bakingapp.adapters.IngredientsAdapter;
import com.example.android.bakingapp.adapters.StepsAdapter;
import com.example.android.bakingapp.model.Ingredient;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.Step;
import com.example.android.bakingapp.rest.RecipeInterface;

import java.util.ArrayList;


public class StepsFragment extends Fragment implements StepsAdapter.StepsAdapterOnClickHandler{
    private TextView mEmptyStateTextView;
    private ArrayList<Step> steps;
    //private List<Recipe> recipes;
    RecyclerView stepsListView;
    RecyclerView ingredientsCardView;
    RecipeInterface apiService;
    private Step mCurrentStep;
    private Recipe mCurrentRecipe;
    private StepsAdapter mAdapter;
    private ArrayList<Ingredient> ingredients;
    private Ingredient mCurrentIngredients;
    private Integer mIngredientsAdapter;
    public StepsAdapter.StepsAdapterOnClickHandler clickHandler;
    Context context;
    private StepsActivity parentActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        parentActivity = (StepsActivity) getActivity();

        View rootView=inflater.inflate(R.layout.fragment_recipe_details,container,false);

        IngredientsAdapter ingredientAdapter = new IngredientsAdapter(ingredients);
        ingredientAdapter.setIngredientData(mCurrentRecipe.getIngredients());
        LinearLayoutManager ingredientLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        ingredientsCardView = rootView.findViewById(R.id.recipe_ingredients_recyclerview);

        ingredientsCardView.setLayoutManager(ingredientLayoutManager);
        ingredientsCardView.setNestedScrollingEnabled(false);
        ingredientsCardView.setAdapter(ingredientAdapter);

        LinearLayoutManager stepLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        stepsListView=rootView.findViewById(R.id.recipe_steps_recyclerview);
        stepsListView.setLayoutManager(stepLayoutManager);
        stepsListView.setHasFixedSize(true);
        stepsListView.setAdapter(mAdapter);



        return rootView;
    }

    @Override
    public void onClick(Step step) {
      //  mCurrentRecipe = recipe;

        //Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        //intent.putExtra(getResources().getString(R.string.parcel_movie), mCurrentMovie);

        //startActivity(intent);
    }

    public void setRecipe(Recipe recipe) {
        mCurrentRecipe = recipe;
    }

    public StepsFragment() {
    }

    public void setStepsAdapter(StepsAdapter adapter){
        mAdapter = adapter;
    }


}
