package ru.android_group.dmtou.tagscloud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    public static final String DB_NAME = "tags-cloud";
    public static final String TABLE_NAME = "TagsTable";
    private static final int DB_VERSION = 1;
    public static final String COLUMN_ID = "id";
    private static final String COLUMN_TAG_NAME = "tag_name";
    private static final String COLUMN_PRINT = "print";
    private static final String COLUMN_SIZE = "size";
    private static final String COLUMN_TYPEFACE = "bold_italic";
    private static final String COLUMN_PARENT_ID = "parent_id";

    public void clear() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "--- onCreate database ---");
        db.execSQL("create table " + TABLE_NAME +
                " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY autoincrement," +
                COLUMN_TAG_NAME + " text," +
                COLUMN_PRINT + " text," +
                COLUMN_SIZE + " double," +
                COLUMN_TYPEFACE + " integer," +
                COLUMN_PARENT_ID + " integer secondary key" +
                " )"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TABLE_NAME);
        onCreate(db);
    }

    public void insert(int id, String tagName, String print, double size, int boldItalic, int parentId) {
        Log.i(TAG, "call DatabaseHelper.insert(" + id + "," + tagName + "," + print + "," + size + "," + boldItalic + "," + parentId + ")");

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ID, id);
        cv.put(COLUMN_TAG_NAME, tagName.replace("\n", ""));
        cv.put(COLUMN_PRINT, print);
        cv.put(COLUMN_SIZE, size);
        cv.put(COLUMN_TYPEFACE, boldItalic);
        cv.put(COLUMN_PARENT_ID, parentId);

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_NAME, null, cv);
        db.close();

        Log.i(TAG, "добавлена запись в БД");
    }

    public List<Mind> getAll(int indexMind) {
        List<Mind> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null);
        if (c.moveToFirst()) {

            // определяем номера столбцов по имени в выборке
            int idColIndex = c.getColumnIndex(COLUMN_ID);
            int tagNameColIndex = c.getColumnIndex(COLUMN_TAG_NAME);
            int sizeColIndex = c.getColumnIndex(COLUMN_SIZE);
            int boldItalicColIndex = c.getColumnIndex(COLUMN_TYPEFACE);
            int parentIdColIndex = c.getColumnIndex(COLUMN_PARENT_ID);

            do {
                int parentId = c.getInt(parentIdColIndex);
                if (parentId == indexMind) {
                    Mind mind = new Mind();
                    mind.setTagName(c.getString(tagNameColIndex));
                    mind.setSizeText(c.getFloat(sizeColIndex));
                    mind.setTypeface(c.getInt(boldItalicColIndex));
                    mind.setSubIndexMind(c.getInt(idColIndex));
                    list.add(mind);
                }
            } while (c.moveToNext());
        }
        c.close();
        return list;
    }

    public enum EventDB {
        NOTHING,
        INSERT,
        UPDATE,
        DELETE;
    }
}