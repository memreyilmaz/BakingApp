package com.example.android.bakingapp;

import android.content.Intent;
import android.graphics.Movie;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.rest.Client;
import com.example.android.bakingapp.rest.RecipeInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.RecipeAdapterOnClickHandler{
    private static final String TAG = MainActivity.class.getSimpleName();

    List<Recipe> recipes= new ArrayList<>();
    private TextView mEmptyStateTextView;
    RecyclerView recipeListView;
    RecipeInterface apiService;
    private Recipe mCurrentRecipe;
    private RecipeAdapter mAdapter;
    public RecipeAdapter.RecipeAdapterOnClickHandler clickHandler;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recipeListView = findViewById(R.id.recipe);
        mAdapter = new RecipeAdapter(recipes,this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recipeListView.setLayoutManager(layoutManager);
        recipeListView.setHasFixedSize(true);
        recipeListView.setAdapter(mAdapter);
        mEmptyStateTextView = findViewById(R.id.empty_view);

        apiService = Client.getClient().create(RecipeInterface.class);

        Call<Recipe> call = apiService.getRecipes();
        call.enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                Log.d(TAG, call.request().url().toString());
                int statusCode = response.code();
                mCurrentRecipe = response.body();
                mAdapter.setRecipeData(mCurrentRecipe);
                mAdapter.notifyDataSetChanged();
                recipes = mCurrentRecipe.getRecipes();
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
            public void onFailure(Call<Recipe> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }

    @Override
    public void onClick(Recipe recipe) {
        mCurrentRecipe = recipe;

        //Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        //intent.putExtra(getResources().getString(R.string.parcel_movie), mCurrentMovie);

        //startActivity(intent);
    }
}
