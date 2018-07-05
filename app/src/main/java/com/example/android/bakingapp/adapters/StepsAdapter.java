package com.example.android.bakingapp.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Step;

import java.util.ArrayList;
import java.util.List;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsAdapterViewHolder> {
    private ArrayList<Step> mSteps;
    private Context context;
    private StepsAdapterOnClickHandler stepsAdapterOnClickHandler;

    public interface StepsAdapterOnClickHandler {
        void onClick(Step step);
    }

    public StepsAdapter(ArrayList<Step> steps, StepsAdapterOnClickHandler stepsAdapterOnClickHandler) {
        this.stepsAdapterOnClickHandler = stepsAdapterOnClickHandler;
        this.mSteps = steps;
    }

    public class StepsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public TextView stepDescTextView;

        public StepsAdapterViewHolder(View view) {
            super(view);
            stepDescTextView = view.findViewById(R.id.step_description_textview);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Step step = mSteps.get(adapterPosition);
            stepsAdapterOnClickHandler.onClick(step);
        }
    }

    @Override
    public StepsAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.step_item, parent, false);

        return new StepsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepsAdapterViewHolder holder, int position) {

        Step step = mSteps.get(position);
        String stepDesc = step.getShortDescription();

        holder.stepDescTextView.setText(stepDesc);

    }

    @Override
    public int getItemCount() {
        if (null == mSteps) return 0;
        return mSteps.size();
    }

    public List<Step> getSteps() {
        return mSteps;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void setStepData(ArrayList<Step> steps) {
        mSteps = steps;
        notifyDataSetChanged();
    }
}