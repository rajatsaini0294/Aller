package com.rajat.android.aller.data;

/**
 * Created by rajat on 3/6/2017.
 */

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

@Database(version = AllerDatabase.VERSION)
public class AllerDatabase {

    public static final int VERSION = 1;

    @Table(TableColumns.class) public static final String VISITED = "visited";
    @Table(TableColumns.class) public static final String TO_VISIT = "to_visit";
}
