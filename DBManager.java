package myprojectpackage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class DBManager extends SQLiteOpenHelper {

    public static final boolean MANAGE_TABLES = true;
    public static final String DELETE_TABLE = "DELETE TABLE";

    Context context;

    String dbName;
    String[] columns;
    String[] columnTypes;

    boolean dbManageTables = false;

    public DBManager(Context context, String name, String columnsAndTypes[][]) {
        super(context, name, null, 1);
        this.context = context;

        dbName = name;
        columns = new String[columnsAndTypes.length];
        columnTypes = new String[columnsAndTypes.length];

        for (int i=0; i<columnsAndTypes.length; i++) {
            columns[i] = columnsAndTypes[i][0];
            columnTypes[i] = columnsAndTypes[i][1];
        }

    }

    public DBManager(Context context, boolean MANAGE_TABLES) {
        super(context, "dbManager", null, 1);
        this.context = context;

        dbManageTables = MANAGE_TABLES;

        dbName = "dbManager";
        columns = new String[] {
                "id",
                "name",
                "columnsAndTypes"
        };
        columnTypes = new String[] {
                "INTEGER PRIMARY KEY AUTOINCREMENT",
                "TEXT",
                "TEXT"
        };

    }

    public Boolean tableExists() {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }

    public DBManager createTable(String name, String[][] columnsAndTypes) {

        if (!dbManageTables) {
            return null;
        }

        String dbName = name;
        String columnTypes = "{";

        for (int i=0; i<columnsAndTypes.length; i++) {
            columnTypes += ("\"" + columnsAndTypes[i][0] + "\":\"" + columnsAndTypes[i][1] + "\",");
        }

        columnTypes = columnTypes.substring(0, columnTypes.length()-1);

        columnTypes += "}";

        Cursor cursor = query("name =\"" + dbName + "\"");

        if (!cursor.moveToPosition(0)) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", dbName);
            contentValues.put("columnsAndTypes", columnTypes);
            insert(contentValues);
        }

        return new DBManager(context, dbName, columnsAndTypes);

    }

    public void deleteTable(String deleteConfirm, String dbName) {

        if (deleteConfirm == DELETE_TABLE) {
            getWritableDatabase().execSQL("DROP TABLE " + dbName);
        }

        File dbFile = context.getDatabasePath(dbName);
        dbFile.delete();

        if (dbManageTables) {
            delete("name =\"" + dbName + "\"");
        }

    }

    public DBManager getTable(String dbName) {

        Cursor cursor = query("name =\"" + dbName + "\"");

        if (cursor.moveToPosition(0)) {

            String columnSettings = cursor.getString(cursor.getColumnIndexOrThrow("columnsAndTypes"));
            String[][] columnsAndTypes;

            try {

                JSONObject jsonObject = new JSONObject(columnSettings);
                JSONArray jsonArray = jsonObject.names();

                columnsAndTypes = new String[jsonArray.length()][2];

                for (int i=0; i<jsonArray.length(); i++) {
                    columnsAndTypes[i] = new String[] { jsonArray.get(i).toString(), jsonObject.getString(jsonArray.get(i).toString()) };
                }

            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }

            return new DBManager(context, dbName, columnsAndTypes);

        } else {
            return null;
        }


    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        StringBuilder createCommand = new StringBuilder();

        createCommand.append("CREATE TABLE " + dbName + " (");

        for (int i=0; i<columns.length; i++) {
            createCommand.append(columns[i] + " " + columnTypes[i] + ", ");
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
