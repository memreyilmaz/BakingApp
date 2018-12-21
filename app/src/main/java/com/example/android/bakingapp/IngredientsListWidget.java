package com.example.android.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.widget.RemoteViews;

import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.rest.Client;
import com.example.android.bakingapp.rest.RecipeInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IngredientsListWidget extends AppWidgetProvider {

    private static Recipe mCurrentRecipe;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        updateWidgets(context, appWidgetManager, appWidgetIds);
    }

    public static void updateWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds){
        for (int appWidgetId : appWidgetIds) {
            updateWidget(context, appWidgetManager, appWidgetId);
        }
    }

    static void updateWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        loadRecipeForWidget(context, appWidgetManager, appWidgetId);
    }

    private static void loadRecipeForWidget(final Context context, final AppWidgetManager appWidgetManager, final int appWidgetId) {

        RecipeInterface apiService;
        apiService = Client.getClient().create(RecipeInterface.class);
        Call<ArrayList<Recipe>> call = apiService.getRecipe();

        call.enqueue(new Callback<ArrayList<Recipe>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<Recipe>> call, @NonNull Response<ArrayList<Recipe>> response) {

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                int position = preferences.getInt(MainActivity.DETAIL_POSITION_KEY, 0);
                mCurrentRecipe = response.body().get(position);
                Intent detailLaunchIntent = new Intent(context, DetailActivity.class);
                Bundle extras = new Bundle();
                extras.putParcelable(MainActivity.DETAIL_KEY, mCurrentRecipe);
                detailLaunchIntent.putExtras(extras);
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, detailLaunchIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_list_widget);
                Intent remoteViewsIntent = new Intent(context, WidgetRemoteViewsService.class);
                views.setRemoteAdapter(R.id.ingredients_list_view_for_widget, remoteViewsIntent);
                views.setEmptyView(R.id.ingredients_list_view_for_widget, R.id.empty_view_for_widget);
                views.setTextViewText(R.id.recipe_name_textview_for_widget, mCurrentRecipe.getName());
                views.setOnClickPendingIntent(R.id.layout_for_widget, pendingIntent);
                appWidgetManager.updateAppWidget(appWidgetId, views);
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<Recipe>> call, @NonNull Throwable t) {
                RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_list_widget);
                Intent remoteViewsIntent = new Intent(context, WidgetRemoteViewsService.class);
                views.setRemoteAdapter(R.id.ingredients_list_view_for_widget, remoteViewsIntent);
                views.setEmptyView(R.id.ingredients_list_view_for_widget, R.id.empty_view_for_widget);
                views.setTextViewText(R.id.recipe_name_textview_for_widget, mCurrentRecipe.getName());
                appWidgetManager.updateAppWidget(appWidgetId, views);
            }
        });
    }

    public static void sendUpdateBroadcast(Context context) {
        Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.setComponent(new ComponentName(context, IngredientsListWidget.class));
        context.sendBroadcast(intent);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        if (action.equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            ComponentName componentName = new ComponentName(context, IngredientsListWidget.class);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetManager.getAppWidgetIds(componentName), R.id.ingredients_list_view_for_widget);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(componentName);
            updateWidgets(context, appWidgetManager, appWidgetIds);
        }
        super.onReceive(context, intent);
    }
}

