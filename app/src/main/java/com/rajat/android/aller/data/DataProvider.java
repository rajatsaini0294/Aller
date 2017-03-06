package com.rajat.android.aller.data;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Created by rajat on 3/6/2017.
 */

@ContentProvider(authority = DataProvider.AUTHORITY, database = AllerDatabase.class)

public class DataProvider {

    public static final String AUTHORITY = "com.rajat.android.aller.data.DataProvider";

    @TableEndpoint(table = AllerDatabase.VISITED)
    public static class Visited {
        @ContentUri(
                path = "visited",
                type = "vnd.android.cursor.dir/list",
                defaultSort = TableColumns._ID + " ASC")
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/visited");
    }

    @TableEndpoint(table = AllerDatabase.TO_VISIT)
    public static class ToVisit {
        @ContentUri(
                path = "to_visit",
                type = "vnd.android.cursor.dir/list",
                defaultSort = TableColumns._ID + " ASC")
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/to_visit");
    }
}
