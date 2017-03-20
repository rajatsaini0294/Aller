package com.rajat.android.aller.appWidget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by rajat on 3/17/2017.
 */

public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return(new WidgetViewsFactory(this.getApplicationContext(),intent));
    }
}
