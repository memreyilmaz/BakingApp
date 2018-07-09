package com.example.android.bakingapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.DetailActivity;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.adapters.StepsAdapter;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.Step;

import java.util.ArrayList;


public class StepsDetailFragment extends Fragment implements StepsAdapter.StepsAdapterOnClickHandler{
    private ArrayList<Step> steps;
    //private List<Recipe> recipes;
    TextView stepsDetailTextView;
    private Step mCurrentStep;
    private Recipe mCurrentRecipe;
    private StepsAdapter mAdapter;
    public StepsAdapter.StepsAdapterOnClickHandler clickHandler;
    Context context;
    private DetailActivity parentActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        parentActivity = (DetailActivity) getActivity();

        View rootView=inflater.inflate(R.layout.activity_detail,container,false);

        //stepsListView.setLayoutManager(stepLayoutManager);
        //stepsListView.setHasFixedSize(true);
        //stepsListView.setAdapter(mAdapter);



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

    public StepsDetailFragment() {
    }

    public void setStepsAdapter(StepsAdapter adapter){
        mAdapter = adapter;
    }


}