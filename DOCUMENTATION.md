# Creating and accessing tables
### Creating a dbManager instance
- Be sure to use a valid SQLite instance name
```java
DBManager dbManager = new DBManager(Context, InstanceName);
```

### Creating table
- Be sure to use valid SQLite names and types
```java
dbManager.createTable("tableName", new String[][] {
    { "ID", "INTENGER PRIMARY KEY AUTOINCREMENT" },
    { "name", "TEXT" },
    { "age", "INTEGER"},
});
```

### Getting a table instance
```java
DBManager tableName = dbManager.getTable("tableName");
```

### Deleting a table
- This method delete the table instance and file
```java
// Table created previously in this documentation
DBManager tableName = dbManager.getTable("tableName");
tableName.deleteTable(DBManager.DELETE_TABLE, "tableName");
```

### Notes
- You just need to create a table only one time, after that all the collumns and settings will be save in another table named by InstanceName (constructor call)
- Only tables created by DBManager are accessable with DBManager.getTable()

# Data manipulation

### Insertint data
```java
// Table created previously in this documentation
DBManager tableName = dbManager.getTable("tableName");

ContentValues contentValues = new ContentValues();
contentValues.put("name", "Random User Name");
contentValues.put("age", 30);

tableName.insert(contentValues);
```

### Query
```java
// Table created previously in this documentation
DBManager tableName = dbManager.getTable("tableName");
Cursor queryResult = tableName.query("name=\"Random User Name\"");
```

### Delete row
```java
// Table created previously in this documentation
DBManager tableName = dbManager.getTable("tableName");
tableName.delete("name=\"Random User Name\"");
```
