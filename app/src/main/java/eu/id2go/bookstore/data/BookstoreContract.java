package eu.id2go.bookstore.data;

import android.provider.BaseColumns;

/**
 * @Contracts exist of 3 parts:
 * Outer class named: BlankContract
 * Inner class named: BlankEntry that implements BaseColumns for each table in the database
 * String constants for each of the headings in the database
 */
public final class BookstoreContract {

    public static abstract class BookTitleEntry implements BaseColumns {

//        private BookstoreContract() {}

        public static final String TABLE_NAME = "bookstore";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_AUTHOR = "author";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DESCRIPTION = "Description";
        public static final String COLUMN_GENRE = "genre";
        public static final String COLUMN_PAGES = "pages";

        /**
         * Possible values for the literary genre of the books
         */
        public static final int GENRE_SCIENCE_FICTION = 0;
        public static final int GENRE_FICTION = 1;
        public static final int GENRE_DRAMA = 2;
        public static final int GENRE_ROMANCE = 3;
        public static final int GENRE_ACTION = 4;
        public static final int GENRE_ADVENTURE = 5;
        public static final int GENRE_SATIRE = 6;
        public static final int GENRE_HORROR = 7;

    }
}

