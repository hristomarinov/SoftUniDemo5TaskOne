package marinov.hristo.task5.database;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * @author HristoMarinov (christo_marinov@abv.bg).
 */
public class MyDatabase extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "food.db";
    private static final int DATABASE_VERSION = 1;

    public MyDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

}
