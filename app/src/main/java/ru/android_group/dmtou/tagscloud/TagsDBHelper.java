package ru.android_group.dmtou.tagscloud;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


class TagsDBHelper extends SQLiteOpenHelper {

    private static final String TAG = "DB";

    public TagsDBHelper(Context context) {
        super(context, "TagsDB.db", null, 7);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG,"--- onCreate database ---");
        db.execSQL("create table TagsTable ("
                + "id integer primary key autoincrement,"
                + "tag_name text,"
                + "print text,"
                + "size double,"
                + "bold_italic text,"
                + "parent_id integer secondary key"
                + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("delete * from TagsTable");
        db.execSQL("DROP TABLE TagsTable");
        this.onCreate(db);
    }
}