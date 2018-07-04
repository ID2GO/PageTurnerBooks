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
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import eu.id2go.bookstore.data.BookstoreContract.BookTitleEntry;
import eu.id2go.bookstore.data.BookstoreDbHelper;

//import eu.id2go.bookstore.data.BookstoreContract;

/**
 * Allows user to create a new book or edit an existing one.
 */
public class EditorActivity extends AppCompatActivity {

    /**
     * EditText field to enter the book's author
     */
    private EditText mAuthorEditText;

    /**
     * EditText field to enter the book's title
     */
    private EditText mTitleEditText;

    /**
     * EditText field to enter the book's description
     */
    private EditText mDescriptionEditText;

    /**
     * EditText field to enter the book's pages
     */
    private EditText mPagesEditText;

    /**
     * EditText field to enter the book's genre
     */
    private Spinner mGenreSpinner;

    /**
     * Genre of the book. The possible values are:
     * 0 for unknown genre, 1 for male, 2 for female.
     */
    private int mGenre = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        // Find all relevant views that we will need to read user input from
        mAuthorEditText = findViewById(R.id.edit_book_author);
        mTitleEditText = findViewById(R.id.edit_book_title);
        mDescriptionEditText = findViewById(R.id.edit_book_description);
        mPagesEditText = findViewById(R.id.edit_book_pages);
        mGenreSpinner = findViewById(R.id.spinner_genre);

        setupSpinner();
    }

    /**
     * Setup the dropdown spinner that allows the user to select the genre of the book.
     */
    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter genreSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_genre_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        genreSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mGenreSpinner.setAdapter(genreSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mGenreSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    // Here make reference to OuterClassContract.InnerClassEntry.CONSTANT (BlankContract.BlankEntry.CONSTANT)
                    // Due to importstatement OuterClassContract.InnerClassEntry the outerclass BookstoreContract. can be omitted
                    if (selection.equals(getString(R.string.genre_science_fiction))) {
                        mGenre = BookTitleEntry.GENRE_SCIENCE_FICTION; // Science Fiction
                    } else if (selection.equals(getString(R.string.genre_fiction))) {
                        mGenre = BookTitleEntry.GENRE_FICTION; // Fiction
                    } else if (selection.equals(getString(R.string.genre_drama))) {
                        mGenre = BookTitleEntry.GENRE_DRAMA; // Drama
                    } else if (selection.equals(getString(R.string.genre_romance))) {
                        mGenre = BookTitleEntry.GENRE_ROMANCE; // Romance
                    } else if (selection.equals(getString(R.string.genre_action))) {
                        mGenre = BookTitleEntry.GENRE_ACTION; // Action
                    } else if (selection.equals(getString(R.string.genre_adventure))) {
                        mGenre = BookTitleEntry.GENRE_ADVENTURE; // Adventure
                    } else if (selection.equals(getString(R.string.genre_satire))) {
                        mGenre = BookTitleEntry.GENRE_SATIRE; // Satire
                    } else if (selection.equals(getString(R.string.genre_horror))) {
                        mGenre = BookTitleEntry.GENRE_HORROR; // Horror
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mGenre = 0; // Unknown
            }
        });
    }

    // make new content value object, use key value pair where the key is the author column and the value is the author from the EditText field
    // converting a string into an integer use integer.parseInt method Integer.parseInt("1") -> 1. This will change strings into integers
    // Value for genre is stored in mGenre
    // author, title, description, genre, pages

    /**
     * Get user input from editor and save new book into database.
     */
    private void insertBook() {
        // Read input from EditText fields
        // To avoid poluted output from string @.trim() to eliminate leading or trailing white space
        String authorString = mAuthorEditText.getText().toString().trim();
        String titleString = mTitleEditText.getText().toString().trim();
        String descriptionString = mDescriptionEditText.getText().toString().trim();
        String pagesString = mPagesEditText.getText().toString().trim();
        int pages = Integer.parseInt(pagesString);

        // Creating connection to database using database helper
        BookstoreDbHelper mDbHelper = new BookstoreDbHelper(this);
        // Get the database in writing mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        //  Create a ContentValues object using key value pairs where the key is the author column and the value is the author from the EditText field
        ContentValues values = new ContentValues();
        values.put(BookTitleEntry.COLUMN_AUTHOR, authorString);
        values.put(BookTitleEntry.COLUMN_TITLE, titleString);
        values.put(BookTitleEntry.COLUMN_DESCRIPTION, descriptionString);
        values.put(BookTitleEntry.COLUMN_GENRE, mGenre);
        values.put(BookTitleEntry.COLUMN_PAGES, pages);

        // Insert a new row in the bookstore database returning the id of that new row
        long newRowId = db.insert(BookTitleEntry.TABLE_NAME, null, values);

        // Show a toast message of either success saving or error saving
        if (newRowId == -1) {
            // If the row ID is -1, then saving resulted in an error
            Toast.makeText(this, "Error while saving book info", Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise saving was successful and a toast displays showing a row ID
            Toast.makeText(this, "Book info saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // Save entries to database
                insertBook();
                // exit activity
                finish();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Do nothing for now
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}