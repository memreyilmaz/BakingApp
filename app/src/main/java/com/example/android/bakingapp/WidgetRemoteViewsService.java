package com.example.android.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.bakingapp.model.Ingredient;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.rest.Client;
import com.example.android.bakingapp.rest.RecipeInterface;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

public class WidgetRemoteViewsService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RecipeRemoteViewsFactory(this.getApplicationContext());
    }
}

class RecipeRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private ArrayList<Ingredient> ingredients;

    public RecipeRemoteViewsFactory(Context context){
        mContext = context;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
            loadData();
    }

    @Override
    public void onDestroy() {
        if (ingredients != null) {
            ingredients = null;}
    }

    @Override
    public int getCount() {
        return ingredients == null ? 0: ingredients.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Ingredient mIngredient = ingredients.get(position);
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.ingredients_item_for_widget);
        String ingredientName = mIngredient.getIngredient();
        double ingredientQuantity = mIngredient.getQuantity();
        String ingredientMeasure = mIngredient.getMeasure();

        views.setTextViewText(R.id.recipe_ingredients_name_textview_for_widget, StringUtils.capitalize(ingredientName));
        views.setTextViewText(R.id.recipe_ingredients_quantity_textview_for_widget, reformatquantity(ingredientQuantity));
        views.setTextViewText(R.id.recipe_ingredients_measure_textview_for_widget, ingredientMeasure);
        return views;
    }
    public static String reformatquantity(double ingredientQuantity)
    {
        if(ingredientQuantity == (long) ingredientQuantity)
            return String.format("%d",(long)ingredientQuantity);
        else
            return String.format("%s",ingredientQuantity);
    }
    @Override
    public RemoteViews getLoadingView() {
       return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    private void loadData(){
        RecipeInterface apiService;
        apiService = Client.getClient().create(RecipeInterface.class);

        Call<ArrayList<Recipe>> call = apiService.getRecipe();
        try {
            Response<ArrayList<Recipe>> response = call.execute();
            if (response != null){
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
                int position = preferences.getInt(MainActivity.DETAIL_POSITION_KEY, 0);
                Recipe recipe = response.body().get(position);
                ingredients = recipe.getIngredients();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
