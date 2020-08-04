package com.renaultivo.bluetoothserial.tools;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBManager extends SQLiteOpenHelper {

    String dbName;
    String[] columns;
    String[] columnsTypes;

    public DBManager(Context context, String name, String columnsAndTypes[][]) {
        super(context, name, null, 1);

        dbName = name;
        columns = new String[columnsAndTypes.length];
        columnsTypes = new String[columnsAndTypes.length];

        for (int i=0; i<columnsAndTypes.length; i++) {
            columns[i] = columnsAndTypes[i][0];
            columnsTypes[i] = columnsAndTypes[i][1];
        }

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        StringBuilder createCommand = new StringBuilder();

        createCommand.append("CREATE TABLE " + dbName + " (");

        for (int i=0; i<columns.length; i++) {
            createCommand.append(columns[i] + " " + columnsTypes[i] + ", ");
        }

        sqLiteDatabase.execSQL(createCommand.substring(0, createCommand.length() - 2) + ")");

    }

    public Cursor getAllItems() {
        return getReadableDatabase().query(dbName, columns, null, null, null, null, null, null);
    }

    public Cursor query(String where) {
        return getReadableDatabase().query(dbName, columns, where, null, null, null, null, null);
    }

    public long insert(ContentValues contentValues) {
        return getWritableDatabase().insert(dbName, null, contentValues);
    }

    public long update(ContentValues contentValues, String where) {
        return getWritableDatabase().update(dbName, contentValues, where, null);
    }

    public long delete(String where) {
        return getWritableDatabase().delete(dbName, where, null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {}

}
