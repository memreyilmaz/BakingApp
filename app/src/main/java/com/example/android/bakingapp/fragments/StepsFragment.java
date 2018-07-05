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
import com.example.android.bakingapp.adapters.StepsAdapter;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.Step;
import com.example.android.bakingapp.rest.RecipeInterface;

import java.util.ArrayList;


public class StepsFragment extends Fragment implements StepsAdapter.StepsAdapterOnClickHandler{
    private TextView mEmptyStateTextView;
    private ArrayList<Step> steps;
    //private List<Recipe> recipes;
    RecyclerView stepsListView;
    RecipeInterface apiService;
    private Step mCurrentStep;
    private Recipe mCurrentRecipe;
    private StepsAdapter mAdapter;

    public StepsAdapter.StepsAdapterOnClickHandler clickHandler;
    Context context;
    private StepsActivity parentActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       // View rootView = inflater.inflate(R.layout.fragment_recipe_steps, container, false);

        parentActivity = (StepsActivity) getActivity();
       /* Bundle ingredientBundle = this.getArguments();
        if (ingredientBundle != null) {
            steps = ingredientBundle.getParcelableArrayList("steps");
        }*/
         /*   stepsListView = rootView.findViewById(R.id.recipe_steps_recyclerview);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(parentActivity);
            stepsListView.setLayoutManager(layoutManager);
            stepsListView.setHasFixedSize(true);
            mAdapter = new StepsAdapter(steps,this);
            stepsListView.setAdapter(mAdapter);
           // mEmptyStateTextView = rootView.findViewById(R.id.steps_empty_view);
            mAdapter.setStepData(steps);*/

        View rootView=inflater.inflate(R.layout.fragment_recipe_steps,container,false);


        /*mCurrentRecipe=getArguments().getParcelable("ingredientBundle");

        steps=mCurrentRecipe.getSteps();

        steps=new ArrayList<Step>(steps);

        Bundle bundle1=new Bundle();


// Get an ArrayList from a List
        ArrayList<Step> steps1 = new ArrayList<>(steps);

// Put the ArrayList in the Bundle
        bundle1.putParcelableArrayList("stepsList", steps1);*/
        LinearLayoutManager stepLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

       // LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        //mAdapter=new StepsAdapter(steps,this);
        stepsListView=rootView.findViewById(R.id.recipe_steps_recyclerview);

        stepsListView.setLayoutManager(stepLayoutManager);

        stepsListView.setHasFixedSize(true);
        //mIngredients = mCurrentRecipe.getRecipeIngredients();
        stepsListView.setAdapter(mAdapter);
        //stepsListView.setLayoutManager(new LinearLayoutManager(context));

//         mAdapter.notifyDataSetChanged();
        /*apiService = Client.getClient().create(RecipeInterface.class);

        Call<ArrayList<Recipe>> call = apiService.getRecipe();
        call.enqueue(new Callback<ArrayList<Recipe>>() {
            @Override
            public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {
                // Log.d(TAG, call.request().url().toString());
                int statusCode = response.code();
                recipes = response.body();
                mAdapter.setRecipeData(recipes);
                mAdapter.notifyDataSetChanged();

                /*if (recipes.isEmpty()) {
                    recipeListView.setVisibility(View.GONE);
                    mEmptyStateTextView.setVisibility(View.VISIBLE);
                    mEmptyStateTextView.setText(R.string.no_internet_connection);
                } else {
                    recipeListView.setVisibility(View.VISIBLE);
                    mEmptyStateTextView.setVisibility(View.GONE);
                }*/
           /* }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                // Log.e(TAG, t.toString());
            }
        });*/
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
        // Required empty public constructor
    }

    public void setStepsAdapter(StepsAdapter adapter){
        mAdapter = adapter;
    }


}
