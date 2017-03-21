package com.rajat.android.aller.appWidget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.rajat.android.aller.R;
import com.rajat.android.aller.Util.Constants;
import com.rajat.android.aller.data.DataProvider;
import com.rajat.android.aller.data.TableColumns;

/**
 * Created by rajat on 3/17/2017.
 */

public class WidgetViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    Cursor cursor;
    private static final String[] items = {"Kashmir", "Nanda Devi", "Manali", "Kullu", "Goa", "Mumbai", "Pune"};
    private Context context = null;
    private int appWidgetId;

    public WidgetViewsFactory(Context context, Intent intent) {
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
        cursor = context.getContentResolver().query(DataProvider.ToVisit.CONTENT_URI, new String[]{TableColumns.PLACE_NAME}, null, null, null);
        cursor.moveToFirst();
    }

    @Override
    public void onDataSetChanged() {
        cursor = context.getContentResolver().query(DataProvider.ToVisit.CONTENT_URI, new String[]{TableColumns.PLACE_NAME}, null, null, null);
        cursor.moveToFirst();
    }

    @Override
    public void onDestroy() {
        if (this.cursor != null) {
            this.cursor.close();
        }
    }

    @Override
    public int getCount() {
        if (cursor != null && cursor.getCount() > 0)
            return cursor.getCount();
        return 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToPosition(position);
            RemoteViews row = new RemoteViews(context.getPackageName(),
                    R.layout.widget_row_item);


            String placeName = cursor.getString(cursor.getColumnIndex(TableColumns.PLACE_NAME));

            row.setTextViewText(R.id.item_name, placeName);

            Intent i = new Intent();
            Bundle extras = new Bundle();
            extras.putString(Constants.EXTRA_WORD_WIDGET, placeName);
            i.putExtras(extras);
            row.setOnClickFillInIntent(R.id.item_name, i);

            return (row);
        }
        return null;
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
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }


}
