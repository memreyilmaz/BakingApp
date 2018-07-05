package com.example.android.bakingapp.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsAdapterViewHolder> {
    private ArrayList<Ingredient> mIngredients;
    private Context context;


    public IngredientsAdapter(ArrayList<Ingredient> ingredients) {
        this.mIngredients = ingredients;
    }

    public class IngredientsAdapterViewHolder extends RecyclerView.ViewHolder {


        public TextView ingredientNameTextView;
        public TextView ingredientQuantityTextView;
        public TextView ingredientMeasureTextView;

        public IngredientsAdapterViewHolder(View view) {
            super(view);
            ingredientNameTextView = view.findViewById(R.id.recipe_ingredients_name_textview);
            ingredientQuantityTextView = view.findViewById(R.id.recipe_ingredients_quantity_textview);
            ingredientMeasureTextView = view.findViewById(R.id.recipe_ingredients_measure_textview);
        }
    }

    @Override
    public IngredientsAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.ingredients_item, parent, false);

        return new IngredientsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientsAdapterViewHolder holder, int position) {

        Ingredient ingredient = mIngredients.get(position);
        String ingredientDescription = ingredient.getIngredient();
        Double ingredientQuantity = ingredient.getQuantity();
        String ingredientMeasure = ingredient.getMeasure();

        holder.ingredientNameTextView.setText(ingredientDescription);
        holder.ingredientQuantityTextView.setText(String.valueOf(ingredientQuantity));
        holder.ingredientMeasureTextView.setText(ingredientMeasure);

    }

    @Override
    public int getItemCount() {
        if (null == mIngredients) return 0;
        return mIngredients.size();
    }

    public List<Ingredient> getIngredients() {
        return mIngredients;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void setIngredientData(ArrayList<Ingredient> ingredients) {
        mIngredients = ingredients;
        notifyDataSetChanged();
    }
}