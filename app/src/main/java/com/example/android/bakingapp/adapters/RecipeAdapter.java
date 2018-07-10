package com.example.android.bakingapp.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Recipe;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeAdapterViewHolder> {

    private List<Recipe> mRecipes;
    private Context context;
    private RecipeAdapterOnClickHandler recipeAdapterOnClickHandler;

    public interface RecipeAdapterOnClickHandler {
        void onClick(Recipe recipe);
    }

    public RecipeAdapter(List<Recipe> recipes, RecipeAdapterOnClickHandler recipeAdapterOnClickHandler) {
        this.recipeAdapterOnClickHandler = recipeAdapterOnClickHandler;
        this.mRecipes = recipes;
    }

    public class RecipeAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView recipeImageView;
        public TextView recipeNameTextView;

        public RecipeAdapterViewHolder(View view) {
            super(view);
            recipeImageView = view.findViewById(R.id.recipe_image_imageview);
            recipeNameTextView = view.findViewById(R.id.recipe_name_textview);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Recipe recipe = mRecipes.get(adapterPosition);
            recipeAdapterOnClickHandler.onClick(recipe);
        }
    }

    @Override
    public RecipeAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item, parent, false);

        return new RecipeAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeAdapterViewHolder holder, int position) {

        Recipe recipe = mRecipes.get(position);
        String recipeName = recipe.getName();
        String imageUri = recipe.getImage();
        if (!TextUtils.isEmpty(imageUri)) {
            Picasso.with(context)
                    .load(imageUri)
                    .into(holder.recipeImageView);
        } else {
            holder.recipeImageView.setImageResource(setStockRecipeImage(recipeName));

        }
        holder.recipeNameTextView.setText(recipeName);
    }

    @Override
    public int getItemCount() {
        if (null == mRecipes) return 0;
        return mRecipes.size();
    }

    public List<Recipe> getRecipes() {
        return mRecipes;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void setRecipeData(List<Recipe> recipes) {
        mRecipes = recipes;
        notifyDataSetChanged();
    }

    public static int setStockRecipeImage(String recipeName) {
        switch (recipeName){
            case "Brownies":
                return R.drawable.brownies;
            case "Yellow Cake":
                return R.drawable.yellowcake;
            case "Nutella Pie":
                return R.drawable.nutellapie;
            case "Cheesecake":
                return R.drawable.chesecake;
            default:
                return R.drawable.chesecake;
        }
    }
}
