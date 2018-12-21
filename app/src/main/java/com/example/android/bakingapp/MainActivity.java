package com.example.android.bakingapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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
    public static final String DETAIL_KEY = "detail";
    public static final String DETAIL_POSITION_KEY = "detail_position";

    @Nullable
    private SimpleIdlingResource mIdlingResource;

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         recipeListView = findViewById(R.id.recipe_name_recyclerview);
         if (findViewById(R.id.main_activity_tablet) != null){
             RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
             recipeListView.setLayoutManager(layoutManager);
             recipeListView.setHasFixedSize(true);
         } else {
             RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
             recipeListView.setLayoutManager(layoutManager);
             recipeListView.setHasFixedSize(true);
         }

         mAdapter = new RecipeAdapter(recipes,this);
         recipeListView.setAdapter(mAdapter);
         mEmptyStateTextView = findViewById(R.id.empty_view);

         apiService = Client.getClient().create(RecipeInterface.class);

         Call<ArrayList<Recipe>> call = apiService.getRecipe();
         call.enqueue(new Callback<ArrayList<Recipe>>() {
             @Override
             public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {
                 int statusCode = response.code();
                 recipes = response.body();
                 mAdapter.setRecipeData(recipes);
                 mAdapter.notifyDataSetChanged();
             }
             @Override
             public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                 recipeListView.setVisibility(View.GONE);
                 mEmptyStateTextView.setVisibility(View.VISIBLE);
             }
         });
    }
    @Override
    public void onClick(Recipe recipe, int position) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(MainActivity.DETAIL_POSITION_KEY, position);
        editor.apply();
        IngredientsListWidget.sendUpdateBroadcast(this);

        Intent intent = new Intent(this, DetailActivity.class);
        mCurrentRecipe = recipe;
        Bundle bundle = new Bundle();
        bundle.putParcelable(DETAIL_KEY, mCurrentRecipe);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
