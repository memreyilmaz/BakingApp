package com.example.android.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.android.bakingapp.adapters.RecipeAdapter;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.rest.Client;
import com.example.android.bakingapp.rest.RecipeInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.RecipeAdapterOnClickHandler{
    private static final String TAG = MainActivity.class.getSimpleName();

    private TextView mEmptyStateTextView;
    private ArrayList<Recipe> recipes;
    RecyclerView recipeListView;
    RecipeInterface apiService;
    private Recipe mCurrentRecipe;
    private RecipeAdapter mAdapter;
    public RecipeAdapter.RecipeAdapterOnClickHandler clickHandler;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         recipeListView = findViewById(R.id.recipe_name_recyclerview);
         RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
         recipeListView.setLayoutManager(layoutManager);
         recipeListView.setHasFixedSize(true);
         mAdapter = new RecipeAdapter(recipes,this);
         recipeListView.setAdapter(mAdapter);
         mEmptyStateTextView = findViewById(R.id.empty_view);

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

    }
    @Override
    public void onClick(Recipe recipe) {
        mCurrentRecipe = recipe;

        Bundle recipeBundle = new Bundle();
        ArrayList<Recipe> mCurrentRecipe = new ArrayList<>();
        mCurrentRecipe.add(recipe);
        recipeBundle.putParcelableArrayList("current_recipe", mCurrentRecipe);

        Intent intent = new Intent(this, StepsActivity.class);
        intent.putExtras(recipeBundle);
        startActivity(intent);
    }
}
