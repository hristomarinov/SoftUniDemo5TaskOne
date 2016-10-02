package marinov.hristo.task5.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import marinov.hristo.task5.utils.FoodObj;

/**
 * @author HristoMarinov (christo_marinov@abv.bg).
 */
public class DatabaseAccess {

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;
    private static final String COLUMN_1 = "name";
    private static final String COLUMN_2 = "price";
    private static final String COLUMN_3 = "rating";
    private static final String COLUMN_4 = "image";
    private static final String TABLE_NAME = "food";

    /**
     * Private constructor
     *
     * @param context the Context
     */
    private DatabaseAccess(Context context) {
        this.openHelper = new MyDatabase(context);
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DatabaseAccess
     */
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    /**
     * Open the database connection.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    /**
     * Read all quotes from the database.
     *
     * @return a List of food
     */
    public ArrayList<FoodObj> getFood() {
        ArrayList<FoodObj> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            list.add(new FoodObj(cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getBlob(4)));
            cursor.moveToNext();
        }

        cursor.close();
        return list;
    }

    /**
     * Add food element to the database
     * @param foodObj the FoodObj
     */
    public void addFood(FoodObj foodObj) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_1, foodObj.get_name());
        cv.put(COLUMN_2, foodObj.get_price());
        cv.put(COLUMN_3, foodObj.get_rating());
        cv.put(COLUMN_4, foodObj.getImage());
        database.insert(TABLE_NAME, null, cv);
    }
}