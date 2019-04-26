package com.example.cse.makeupapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class CosmeticWidget extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        SharedPreferences sharedPreferences = context.getSharedPreferences("cosmeticname", Context.MODE_PRIVATE);
        String str= sharedPreferences.getString("putintent","No Data");
        Intent intent=new Intent(context,MainActivity.class);
        PendingIntent pending=PendingIntent.getActivity(context,1,intent,0);
        RemoteViews rv=new RemoteViews(context.getPackageName(),R.layout.cosmetic_widget);
        rv.setOnClickPendingIntent(R.id.appwidget_text,pending);
        rv.setTextViewText(R.id.appwidget_text,str);
        appWidgetManager.updateAppWidget(appWidgetIds, rv);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

