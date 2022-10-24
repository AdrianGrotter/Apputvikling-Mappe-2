package com.example.s354378_mappe2;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

public class MyContentProvider extends ContentProvider {
    public static final String KEY_ID = "_id";
    public static final String KEY_FIRST = "FirstName";
    public static final String KEY_LAST = "LastName";
    public static final String KEY_PHONE = "Phone";
    private static final String DB_NAME = "Database_name";
    private static final int DB_VERSION = 3;
    private final static String TABLE_CONTACTS = "Contacts";
    public final static String PROVIDER = "com.example.contentprovidercontact";
    private static final int CONTACT = 1;

    MyContentProvider.DatabaseHelper DBhelper;
    SQLiteDatabase db;

    public static final Uri CONTENT_URI = Uri.parse("content://" + PROVIDER + "/contact");
    private static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER, "contact", CONTACT);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            String CREATE_TABLE = "CREATE TABLE " + TABLE_CONTACTS +
                    "(" +
                    KEY_ID + " INTEGER PRIMARY KEY," +
                    KEY_FIRST + " TEXT," +
                    KEY_LAST + " TEXT," +
                    KEY_PHONE + " TEXT" +
                    ")";
            Log.d("DatabaseHelper ", " oncreated sql " + CREATE_TABLE);
            db.execSQL(CREATE_TABLE);
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("drop table if exists " + TABLE_CONTACTS);
            Log.d("DatabaseHelper", "updated");
            onCreate(db);
        }
    }

    @Override
    public boolean onCreate() {
        DBhelper = new MyContentProvider.DatabaseHelper(getContext());
        db = DBhelper.getWritableDatabase();
        return true;
    }
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case CONTACT:
                return "vnd.android.cursor.dir/vnd.example.contact";
            default:
                throw new
                        IllegalArgumentException("Ugyldig URI" + uri);
        }
    }
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        db.insert(TABLE_CONTACTS, null, values);
        Cursor c = db.query(TABLE_CONTACTS, null, null, null, null, null, null);
        c.moveToLast();
        long minid = c.getLong(0);
        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, minid);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        if (uriMatcher.match(uri) == CONTACT) {
            db.delete(TABLE_CONTACTS, KEY_ID + " = ?", selectionArgs);
            getContext().getContentResolver().notifyChange(uri, null);
            return 1;
        }
        return 0;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (uriMatcher.match(uri) == CONTACT) {
            db.update(TABLE_CONTACTS, values, KEY_ID + " = ?", selectionArgs);
            getContext().getContentResolver().notifyChange(uri, null);
            return 1;
        }
        return 0;
    }
}
