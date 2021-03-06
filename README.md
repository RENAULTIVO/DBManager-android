# DBManager for android
### Android SQLite DataBase manager

## Lib Goals
- [x] Get a complete Sqlite instance with just one code line
- [x] Table safe delete (delete table and file)
- [x] Create a table instance and file only if necessary (avoiding empty tables)

### Creating a table instance
``` java
// Create DBManager instance
DBManager dbManager = new DBManager(this, "instanceName");

// Create table
dbManager.createTable("tableName", new String[][] {
    { "ID", "INTENGER PRIMARY KEY AUTOINCREMENT" },
    { "name", "TEXT" },
    { "age", "INTEGER"},
    { "blob_name", "BLOB" }
});
```
- After that, you don't need to recreate your table in any part of your app, the table settings will be safe in an internal table.

### Get a table instance (after you have created it)
``` java
DBManager tableInstance = dbManager.getTable("example");
```

See the [DBManager-android documentation](https://github.com/RENAULTIVO/DBManager-android/blob/master/DOCUMENTATION.md)

### Make database implementation on android easier and faster
