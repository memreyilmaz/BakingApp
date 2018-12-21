package com.example.android.bakingapp.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.DetailActivity;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.adapters.StepsAdapter;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;


@SuppressWarnings("deprecation")
public class DetailFragment extends Fragment implements StepsAdapter.StepsAdapterOnClickHandler, ExoPlayer.EventListener{
    private ArrayList<Step> steps;
    //private List<Recipe> recipes;
    private static final String TAG = DetailFragment.class.getSimpleName();

    TextView stepsDetailTextView;
    private Step mCurrentStep;
    private Recipe mCurrentRecipe;
    private String mVideoUrl;
    private int stepId;
    private int stepNr;
    String mDetailedDescription;
    String mImageUrl;
    private StepsAdapter mAdapter;
    public StepsAdapter.StepsAdapterOnClickHandler clickHandler;
    private Context context;

    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private FloatingActionButton nextButton;
    private FloatingActionButton previousButton;
    private static MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;
    private long playerPosition;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle extras = getArguments();
        if (extras != null) {
            mCurrentStep = extras.getParcelable(DetailActivity.STEP_DETAIL_KEY);
            mCurrentRecipe = extras.getParcelable(DetailActivity.RECIPE_DETAIL_KEY);
         //   mCurrentRecipe = extras.getParcelable(DetailActivity.RECIPE_DETAIL_KEY_FOR_TABLET);
        }
        if (mCurrentStep == null){
            steps = mCurrentRecipe.getSteps();
            mCurrentStep = steps.get(0);
        }
        View rootView=inflater.inflate(R.layout.fragment_recipe_details,container,false);
        nextButton = rootView.findViewById(R.id.recipe_next_button);
        previousButton = rootView.findViewById(R.id.recipe_previous_button);
        stepsDetailTextView = rootView.findViewById(R.id.step_description_detail_textview);
        mPlayerView = rootView.findViewById(R.id.recipe_description_video);
        //  steps = mCurrentRecipe.getSteps();
        //  stepNr = steps.size();
        //  stepId = mCurrentStep.getId();
        //mDetailedDescription = mCurrentStep.getDescription();
        getStepDetails();
        stepsDetailTextView.setText(mDetailedDescription);
        //mVideoUrl = mCurrentStep.getVideoURL();
        //mImageUrl = mCurrentStep.getThumbnailURL();
        initializeMediaSession();
       // setUi();
        initializePlayer(Uri.parse(mVideoUrl));
        /*nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (steps != null) {
                    int maxStepId = stepNr - 1;
                    if (stepId < maxStepId) {
                        int index = ++stepId;
                        mCurrentStep = steps.get(index);
                        setStep(mCurrentStep);
                    } else {
                        stepId = 0;
                        mCurrentStep = steps.get(stepId);
                        setStep(mCurrentStep);
                    }
                }

            }
        });*/

        /*previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (steps != null) {
                    int totStepNo = stepNr - 1;
                    if (stepId < totStepNo && stepId > 0) {
                        int index = --stepId;
                        mCurrentStep = steps.get(index);
                        setStep(mCurrentStep);
                    } else {
                        mCurrentStep = steps.get(stepId);
                        setStep(mCurrentStep);
                    }
                }
            }
        });*/
        //stepsListView.setLayoutManager(stepLayoutManager);
        //stepsListView.setHasFixedSize(true);
        //stepsListView.setAdapter(mAdapter);
        steps = mCurrentRecipe.getSteps();
        stepNr = steps.size();
        stepId = mCurrentStep.getId();
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (steps != null) {
                    int maxStepId = stepNr - 1;
                    if (stepId < maxStepId) {
                        int index = ++stepId;
                        mCurrentStep = steps.get(index);
                        getStepDetails();
                        stepsDetailTextView.setText(mDetailedDescription);
                        initializeMediaSession();
                        initializePlayer(Uri.parse(mVideoUrl));
                        //setStep(mCurrentStep);
                    } else {
                        stepId = 0;
                        mCurrentStep = steps.get(stepId);
                        setStep(mCurrentStep);
                    }
                }

            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (steps != null) {
                    int totStepNo = stepNr - 1;
                    if (stepId < totStepNo && stepId > 0) {
                        int index = --stepId;
                        mCurrentStep = steps.get(index);
                        setStep(mCurrentStep);
                        getStepDetails();
                        stepsDetailTextView.setText(mDetailedDescription);
                        initializeMediaSession();
                        initializePlayer(Uri.parse(mVideoUrl));

                    } else {
                        mCurrentStep = steps.get(stepId);
                        setStep(mCurrentStep);

                    }
                }
            }
        });


        return rootView;
    }

    @Override
    public void onClick(Step step) {
        //
    }
    public void getStepDetails(){
        mDetailedDescription = mCurrentStep.getDescription();
        mVideoUrl = mCurrentStep.getVideoURL();
        mImageUrl = mCurrentStep.getThumbnailURL();
    }

    public void setRecipe(Recipe recipe) {
        mCurrentRecipe = recipe;
    }
    public void setStep(Step step) {
        mCurrentStep = step;
    }
    public DetailFragment() {
    }
    public void setStepsAdapter(StepsAdapter adapter){
        mAdapter = adapter;
    }
    private void initializeMediaSession() {

        mMediaSession = new MediaSessionCompat(getContext(), TAG);

        mMediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        mMediaSession.setMediaButtonReceiver(null);

        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mMediaSession.setPlaybackState(mStateBuilder.build());

        mMediaSession.setCallback(new StepsVideoCallback());

        mMediaSession.setActive(true);
    }

    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();

            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);
            mExoPlayer.addListener(this);
            String userAgent = Util.getUserAgent(getContext(), "Baking App");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }
    private class StepsVideoCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            mExoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            mExoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            mExoPlayer.seekTo(0);
        }
    }
    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {
    }
    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
    }
    @Override
    public void onLoadingChanged(boolean isLoading) {
    }
    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
    }
    @Override
    public void onRepeatModeChanged(int repeatMode) {
    }
    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {
    }
    @Override
    public void onPlayerError(ExoPlaybackException error) {
    }
    @Override
    public void onPositionDiscontinuity(int reason) {
    }
    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
    }
    @Override
    public void onSeekProcessed() {
    }
    private void releasePlayer() {
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }
    /*@Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
        mMediaSession.setActive(false);
    }*/

    @Override
    public void onResume() {
        super.onResume();
        if (Util.SDK_INT <= 23 || mExoPlayer == null) {
            initializeMediaSession();
            initializePlayer(Uri.parse(mVideoUrl));
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }
    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }
}