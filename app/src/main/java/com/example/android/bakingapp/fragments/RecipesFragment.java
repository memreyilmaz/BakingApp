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

import com.example.android.bakingapp.MainActivity;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.RecipeAdapter;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.rest.Client;
import com.example.android.bakingapp.rest.RecipeInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RecipesFragment extends Fragment implements RecipeAdapter.RecipeAdapterOnClickHandler{
    private TextView mEmptyStateTextView;
    private ArrayList<Recipe> recipes;
    RecyclerView recipeListView;
    RecipeInterface apiService;
    private Recipe mCurrentRecipe;
    private RecipeAdapter mAdapter;
    public RecipeAdapter.RecipeAdapterOnClickHandler clickHandler;
    Context context;
    private MainActivity parentActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_recipes, container, false);

        parentActivity = (MainActivity) getActivity();


        recipeListView = rootView.findViewById(R.id.recipe);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(parentActivity);
        recipeListView.setLayoutManager(layoutManager);
        recipeListView.setHasFixedSize(true);
        mAdapter = new RecipeAdapter(recipes,this);
            recipeListView.setAdapter(mAdapter);
        mEmptyStateTextView = rootView.findViewById(R.id.empty_view);

        apiService = Client.getClient().create(RecipeInterface.class);

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
            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
               // Log.e(TAG, t.toString());
            }
        });
        return rootView;
    }

    @Override
    public void onClick(Recipe recipe) {
        mCurrentRecipe = recipe;

        //Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        //intent.putExtra(getResources().getString(R.string.parcel_movie), mCurrentMovie);

        //startActivity(intent);
    }

    public RecipesFragment() {
        // Required empty public constructor
    }


}
