package com.example.android.bakingapp.rest;



import com.example.android.bakingapp.model.Recipe;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RecipeInterface {

    @GET()
    Call<Recipe> getRecipes();

    @GET()
    Call<Recipe> getIngredients();

    @GET()
    Call<Recipe>  getSteps(@Path("preference") String preference, @Query("api_key") String apiKey);

    @GET("movie/{id}")
    Call<Recipe> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);
}
