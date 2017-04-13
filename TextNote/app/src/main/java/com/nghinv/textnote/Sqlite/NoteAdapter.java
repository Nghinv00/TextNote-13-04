package com.nghinv.textnote.Sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by NghiNV on 24/03/2017.
 */

public class NoteAdapter {

    public static final String KEY_ROWID ="_id";
    public static final String KEY_TITILE = "title";
    public static final String KEY_NOTE ="note";
    public static final String KEY_DATE = "date";
    public static final String TAG  = "NoteAdapter";
    private DbHelper dbHelper;
    private SQLiteDatabase db;

    private static final String DATABASE_NAME = "DbNote.sqlite";
    private static final String DATABASE_TABLE = "TextNote";
    private static final int DATABASE_VERSION = 1;
    private static Context context;
    /**
     *  tạo 1 database
     */
    private static final String CREATE_TABLE =
            "Create Table " + DATABASE_TABLE
            + " (_id integer primary key autoincrement, "
            + " title TEXT , "
            + " note TEXT , "
            + " date TEXT );";



    private static class DbHelper extends SQLiteOpenHelper{
        DbHelper( Context context)  {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.v(TAG, "Upgrading database from version " + oldVersion  + " to "
            + newVersion +", which will destroy all old data" );
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE );
            onCreate(db);
        }
    }
    /**
     *
     * @param context
     */
    public NoteAdapter(Context context){
        this.context = context;
    }
    /**
     * Open the notes database. If it cannot be opened, try to create a new
     * instance of the database. If it cannot be created, throw an exception to
     * signal the failure
     *
     * @return this (self reference, allowing this to be chained in an
     *         initialization call)
     * @throws SQLException if the database could be neither opened or created
     */
    public NoteAdapter open() throws SQLException {

        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }
    /**
     * @param title
     * @param note
     * @return
     * Trả về -1 nếu tạo note thất bại, trả về số thứ tụ id nếu tạo note thành công
     *
     */
    public long createNode(String title, String note, String date){
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_TITILE, title);
        initialValues.put(KEY_NOTE, note);
        initialValues.put(KEY_DATE, date);

        long id = db.insert(DATABASE_TABLE, null, initialValues);
        //db.close();
        return id;
    }

    /**
     * @param rowID
     * @param title
     * @param note
     * @return
     */
    public boolean updateNote(long rowID, String title, String note, String date){
        ContentValues values = new ContentValues() ;
        values.put(KEY_TITILE, title);
        values.put(KEY_NOTE, note);
        values.put(KEY_DATE, date);
        long id = db.update(DATABASE_TABLE, values, KEY_ROWID + " = " + rowID, null) ;
        //db.close();
        return id > 0 ? true : false;
    }

    public boolean deleteNote( long id){
        long abc = db.delete(DATABASE_TABLE, KEY_ROWID + " = " + id, null);
            return abc > 0 ? true : false;
    }

    public Cursor fetchAllNotes() {
        Cursor cursor = db.query(DATABASE_TABLE, new String[]{
        KEY_ROWID, KEY_TITILE, KEY_NOTE, KEY_DATE}, null, null, null, null, null );
        return cursor;
    }

    public Cursor fetchNote (long rowID) throws SQLException {
        Cursor cursor = db.query(true, DATABASE_TABLE,
           new String[] { KEY_ROWID, KEY_TITILE, KEY_NOTE,KEY_DATE },
                KEY_ROWID + " = " + rowID , null, null, null, null, null);
        if ( cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }
    public Cursor fetchTime( long rowID) throws  SQLException {
        Cursor cursor = db.query(true, DATABASE_TABLE,
                new String[] { KEY_ROWID }, KEY_ROWID + " = "+ rowID,
                null, null, null, null, null   );

        return cursor;
    }

}


