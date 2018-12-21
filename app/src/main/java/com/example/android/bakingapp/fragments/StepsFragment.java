package com.example.android.bakingapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.DetailActivity;
import com.example.android.bakingapp.FragmentInteractionListener;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.adapters.IngredientsAdapter;
import com.example.android.bakingapp.adapters.StepsAdapter;
import com.example.android.bakingapp.model.Ingredient;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.Step;

import java.util.ArrayList;

public class StepsFragment extends Fragment implements StepsAdapter.StepsAdapterOnClickHandler{
    private ArrayList<Step> steps;
    RecyclerView stepsListView;
    RecyclerView ingredientsListView;
    private Step mCurrentStep;
    private Recipe mCurrentRecipe;
    private StepsAdapter stepsAdapter;
    private IngredientsAdapter ingredientAdapter;
    private ArrayList<Ingredient> ingredients;
    private Ingredient mCurrentIngredients;
    private FragmentInteractionListener mListener;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle extras = getArguments();
        if (extras != null) {
            mCurrentRecipe = extras.getParcelable(DetailActivity.RECIPE_DETAIL_KEY);
        }

        StepsAdapter stepsAdapter = new StepsAdapter(steps, this);
        stepsAdapter.setStepData(mCurrentRecipe.getSteps());

        View rootView=inflater.inflate(R.layout.fragment_recipe_steps,container,false);

        ingredientAdapter = new IngredientsAdapter(ingredients);
        ingredientAdapter.setIngredientData(mCurrentRecipe.getIngredients());

        LinearLayoutManager ingredientLayoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        ingredientsListView = rootView.findViewById(R.id.recipe_ingredients_recyclerview);
        ingredientsListView.setLayoutManager(ingredientLayoutManager);
        ingredientsListView.setNestedScrollingEnabled(false);
        ingredientsListView.setAdapter(ingredientAdapter);

        LinearLayoutManager stepLayoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        stepsListView = rootView.findViewById(R.id.recipe_steps_recyclerview);
        stepsListView.setLayoutManager(stepLayoutManager);
        stepsListView.setHasFixedSize(true);
        stepsListView.setAdapter(stepsAdapter);

        return rootView;
    }
    @Override
    public void onClick(Step step) {
        mCurrentStep = step;
        mListener.onStepClicked(mCurrentStep);
    }
    public void setRecipe(Recipe recipe) {
        mCurrentRecipe = recipe;
    }
    public StepsFragment() {
    }
    public void setStepsAdapter(StepsAdapter adapter){
        stepsAdapter = adapter;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (FragmentInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement FragmentInteractionListener");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}