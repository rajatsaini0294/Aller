package com.rajat.android.aller.data;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

import static net.simonvt.schematic.annotation.DataType.Type.INTEGER;
import static net.simonvt.schematic.annotation.DataType.Type.TEXT;

/**
 * Created by rajat on 3/6/2017.
 */

public interface TableColumns {
    @DataType(INTEGER) @PrimaryKey @AutoIncrement String _ID = "_id";
    @DataType(TEXT) @NotNull String PLACE_ID = "place_id";
    @DataType(TEXT) String PLACE_NAME = "place_name";
    @DataType(TEXT) @NotNull String PLACE_ADDRESS = "place_address";
    @DataType(TEXT) String PLACE_PHONE = "place_phone";
    @DataType(TEXT) String PLACE_WEBSITE = "place_website";
    @DataType(TEXT) @NotNull String PLACE_LATITUDE = "place_latitude";
    @DataType(TEXT) @NotNull String PLACE_LONGITUDE = "place_longitude";
    @DataType(TEXT) String PLACE_RATING = "place_rating";
    @DataType(TEXT) String PLACE_IMAGE = "place_image";

}
