/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package eu.id2go.bookstore;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import eu.id2go.bookstore.data.BookstoreContract.BookTitleEntry;
import eu.id2go.bookstore.data.BookstoreDbHelper;

/**
 * Displays list of books that were entered and stored in the app.
 */
public class CatalogActivity extends AppCompatActivity {

    /**
     * Database helper that will provide us access to the database
     */
    private BookstoreDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        mDbHelper = new BookstoreDbHelper(this);

    }

    /**
     * This method is called when user has saved new book info into database
     */
    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    /**
     * Temporary helper method to display information in the onscreen TextView about the state of
     * the bookstore database.
     */
    private void displayDatabaseInfo() {

        // This method is the java equivalent of the sqlite3> .open shelter.db command in the terminal.
        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Define the range of columns from the database to be used
        String[] projection = {
                BookTitleEntry._ID,
                BookTitleEntry.COLUMN_AUTHOR,
                BookTitleEntry.COLUMN_TITLE,
                BookTitleEntry.COLUMN_DESCRIPTION,
                BookTitleEntry.COLUMN_PRICE,
                BookTitleEntry.COLUMN_STOCK_QUANTITY,
                BookTitleEntry.COLUMN_PUBLISHER,
                BookTitleEntry.COLUMN_PHONE_NUMBER,
                BookTitleEntry.COLUMN_GENRE,
                BookTitleEntry.COLUMN_PAGES};

        // Perform a query on the bookstore table
        Cursor cursor = db.query(
                BookTitleEntry.TABLE_NAME,     // The Table of the db to query
                projection,         // The above range of columns from the db
                null,       // The column for the WHERE query
                null,    // The values for the WHERE query
                null,        // Do not group the rows
                null,         // Do not filter on row groups
                null);       // The sorting order

        TextView displayView = findViewById(R.id.text_view_book);


        try {
            // Display the number of rows in the Cursor (which reflects the number of rows in the
            // bookstore table in the database).
//            displayView.setText("The bookstore table contains: " + cursor.getCount() + " books.\n\n");

            displayView.setText(String.format(getResources().getString(R.string.contents_of_database_table) + cursor.getCount() + " books \n\n"));


            displayView.append(BookTitleEntry._ID + " - " +
                    BookTitleEntry.COLUMN_AUTHOR + " - " +
                    BookTitleEntry.COLUMN_TITLE + " - " +
                    BookTitleEntry.COLUMN_DESCRIPTION + " - " +
                    BookTitleEntry.COLUMN_PRICE + " - " +
                    BookTitleEntry.COLUMN_STOCK_QUANTITY + " - " +
                    BookTitleEntry.COLUMN_PUBLISHER + " - " +
                    BookTitleEntry.COLUMN_PHONE_NUMBER + " - " +
                    BookTitleEntry.COLUMN_GENRE + " - " +
                    BookTitleEntry.COLUMN_PAGES + "\n");

            // Match the index to each column
            int idColumnIndex = cursor.getColumnIndex(BookTitleEntry._ID);
            int authorColumnIndex = cursor.getColumnIndex(BookTitleEntry.COLUMN_AUTHOR);
            int titleColumnIndex = cursor.getColumnIndex(BookTitleEntry.COLUMN_TITLE);
            int descriptionColumnIndex = cursor.getColumnIndex(BookTitleEntry.COLUMN_DESCRIPTION);
            int priceColumnIndex = cursor.getColumnIndex(BookTitleEntry.COLUMN_PRICE);
            int stockQuantityColumnIndex = cursor.getColumnIndex(BookTitleEntry.COLUMN_STOCK_QUANTITY);
            int publisherNameColumnIndex = cursor.getColumnIndex(BookTitleEntry.COLUMN_PUBLISHER);
            int phoneNumberColumnIndex = cursor.getColumnIndex(BookTitleEntry.COLUMN_PHONE_NUMBER);
            int genreColumnIndex = cursor.getColumnIndex(BookTitleEntry.COLUMN_GENRE);
            int pagesColumnIndex = cursor.getColumnIndex(BookTitleEntry.COLUMN_PAGES);

            // Loop through the returned table rows in the cursor
            while (cursor.moveToNext()) {
                // Use defined index to extract the String or Integer values at the current row the cursor is on
                int currentID = cursor.getInt(idColumnIndex);
                String currentAuthor = cursor.getString(authorColumnIndex);
                String currentTitle = cursor.getString(titleColumnIndex);
                String currentDescription = cursor.getString(descriptionColumnIndex);
                int currentPrice = cursor.getInt(priceColumnIndex);
                int currentStockQuantity = cursor.getInt(stockQuantityColumnIndex);
                String currentPublisherName = cursor.getString(publisherNameColumnIndex);
                String currentPhoneNumber = cursor.getString(phoneNumberColumnIndex);
                int currentGenre = cursor.getInt(genreColumnIndex);
                int currentPages = cursor.getInt(pagesColumnIndex);
                // Display the values from each column of the current row in the cursor in the TextView
                displayView.append(("\n" + currentID + " - " +
                        currentAuthor + " - " +
                        currentTitle + " - " +
                        currentDescription + " - " +
                        currentPrice + " - " +
                        currentStockQuantity + " - " +
                        currentPublisherName + " - " +
                        currentPhoneNumber + " - " +
                        currentGenre + " - " +
                        currentPages));


            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }

    /**
     * Helper method to insert hardcoded book data into the database. For debugging purposes only.
     */
    private void insertBook() {
        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        // and Tolkien's book attributes are the values.
        // Possible column names are: shop_id_number,ISBN (International Standard Book Number), EAN
        // (European Article Number (bar code)), author, title, description, genre, language, number
        // of pages, year of publication, picture, price, quantity, publisher name, and publisher phone number.
        ContentValues values = new ContentValues();
        values.put(BookTitleEntry.COLUMN_AUTHOR, "Tolkien, John Ronald Reuel");
        values.put(BookTitleEntry.COLUMN_TITLE, "The Hobbit");
        values.put(BookTitleEntry.COLUMN_DESCRIPTION, "Smaug certainly looked fast asleep, when Bilbo peeped once more from the entrance. He was just about to step out on to the floor when he caught a sudden thin ray of red from under the drooping lid of Smaug's left eye. He was only pretending to be asleep! He was watching the tunnel entrance... Whisked away from his comfortable, unambitious life in his hobbit-hole in Bag End by Gandalf the wizard and a company of dwarves, Bilbo");
        values.put(BookTitleEntry.COLUMN_GENRE, BookTitleEntry.GENRE_FICTION);
        values.put(BookTitleEntry.COLUMN_PRICE, 40);
        values.put(BookTitleEntry.COLUMN_STOCK_QUANTITY, 2);
        values.put(BookTitleEntry.COLUMN_PUBLISHER, "HarperCollins");
        values.put(BookTitleEntry.COLUMN_PHONE_NUMBER, 2087417070);
        values.put(BookTitleEntry.COLUMN_PAGES, 310);

        // Insert a new row for The Hobbit in the database, returning the ID of that new row.
        // The first argument for db.insert() is the bookstore table name.
        // The second argument provides the name of a column in which the framework
        // can insert NULL in the event that the ContentValues is empty (if
        // this is set to "null", then the framework will not insert a row when
        // there are no values).
        // The third argument is the ContentValues object containing the info for The Hobbit.

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertBook();
                displayDatabaseInfo();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Do nothing for now
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}


