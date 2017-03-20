package com.rajat.android.aller.appWidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

import com.rajat.android.aller.R;
import com.rajat.android.aller.ui.activities.MainActivity;

/**
 * Created by rajat on 3/17/2017.
 */

public class WidgetProvider extends AppWidgetProvider {
    public static final String UPDATE_MEETING_ACTION = "android.appwidget.action.APPWIDGET_UPDATE";

    public static final String EXTRA_ITEM = "com.example.edockh.EXTRA_ITEM";
    public static String EXTRA_WORD =
            "com.rajat.android.aller.widget";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int i = 0; i < appWidgetIds.length; i++) {
            Intent svcIntent = new Intent(context, WidgetService.class);

            svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            svcIntent.setData(Uri.parse(svcIntent.toUri(Intent.URI_INTENT_SCHEME)));

            RemoteViews widget = new RemoteViews(context.getPackageName(),
                    R.layout.widget_layout);

            widget.setRemoteAdapter(appWidgetIds[i], R.id.list_view,
                    svcIntent);

            Intent clickIntent = new Intent(context, MainActivity.class);
            PendingIntent clickPI = PendingIntent
                    .getActivity(context, 0,
                            clickIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT);

            widget.setPendingIntentTemplate(R.id.list_view, clickPI);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds[i], R.id.list_view);
            //appWidgetManager.updateAppWidget(new ComponentName(context, WidgetProvider.class), widget);
            appWidgetManager.updateAppWidget(appWidgetIds[i], widget);

        }

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        AppWidgetManager mgr = AppWidgetManager.getInstance(context);

        if (intent.getAction().equals(UPDATE_MEETING_ACTION)) {

            int appWidgetIds[] = mgr.getAppWidgetIds(new ComponentName(context, WidgetProvider.class));

            Log.e("received", intent.getAction());

            mgr.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.list_view);

        }
    }
}
