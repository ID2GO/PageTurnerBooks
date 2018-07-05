package eu.id2go.bookstore.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import eu.id2go.bookstore.data.BookstoreContract.BookTitleEntry;

// Reflection on SQLiteOpenHelper class, what should it do?
// 1 Create a SQLite database when first accessed
// 2 Connecting to created database
// 3 Managing updating database schema if version changes

/**
 * BookstoreDbHelper class should extend the SQLiteOpenHelper
 */

public class BookstoreDbHelper extends SQLiteOpenHelper {


    /**
     * Constants for the database name and database version
     * If the database schema changes (i.e. when adding columns for extra data),
     * than increment the database version number!
     */
    private static final String DATABASE_NAME = "shelter.db";
    private static final int DATABASE_VERSION = 1;

    /**
     * Construct a new instance of BookstoreDbHelper
     *
     * @param context of the app
     */
    public BookstoreDbHelper(Context context) {
        // database name, cursor factory is set to null to use the default setting, database version number
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Because of subClassing the abstract classe (SQLiteOpenHelper) we need to implement the (onCreate() method & onUpgrade() method
     *
     * @param @{onCreate()}  method - this method is for when the database is first created
     * @param @{onUpgrade()} method - this method is for when the database schema of the database changes (ex: adding a different column)
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // CREATE TABLE bookstore (_id, INTEGER PRIMARY KEY (add the AUTOINCREMENT to automatically increment new unique _id numbers)
        // Create a String that contains the SQL statement to create the bookstore table
        String SQL_CREATE_BOOK_TABLE = "CREATE TABLE " + BookTitleEntry.TABLE_NAME + "("
                + BookTitleEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + BookTitleEntry.COLUMN_AUTHOR + " TEXT NOT NULL, "
                + BookTitleEntry.COLUMN_TITLE + " TEXT, "
                + BookTitleEntry.COLUMN_DESCRIPTION + " Text, "
                + BookTitleEntry.COLUMN_PRICE + " INTEGER NOT NULL DEFAULT 0,"
                + BookTitleEntry.COLUMN_STOCK_QUANTITY + " INTEGER NOT NULL DEFAULT + 0,"
                + BookTitleEntry.COLUMN_PUBLISHER + " TEXT, "
                + BookTitleEntry.COLUMN_PHONE_NUMBER + " TEXT, "
                + BookTitleEntry.COLUMN_GENRE + " INTEGER NOT NULL, "
                + BookTitleEntry.COLUMN_PAGES + " INTEGER NOT NULL DEFAULT 0);";

        // To execute the SQL statement
        db.execSQL(SQL_CREATE_BOOK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still @ version 1, so implementation of this command follows later

    }


}
