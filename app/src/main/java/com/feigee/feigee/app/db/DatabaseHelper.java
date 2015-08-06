package com.feigee.feigee.app.db;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.feigee.feigee.app.entity.TodoList;
import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.android.AndroidDatabaseConnection;
import com.j256.ormlite.android.DatabaseTableConfigUtil;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.table.DatabaseTableConfig;
import com.j256.ormlite.table.TableUtils;

public class DatabaseHelper extends SQLiteOpenHelper{
    protected AndroidConnectionSource connectionSource =
            new AndroidConnectionSource(this);
    private static DatabaseHelper mHelper;
    private static final String DATABASE_NAME = "feigee.db";
    private static final int DATABASE_VERSION = 1;
    private Dao<TodoList, Integer> todoListDao = null;

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public synchronized static DatabaseHelper getInstance(Context context) {
        if (mHelper == null) {
            mHelper = new DatabaseHelper(context);
        }
        return mHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        DatabaseConnection conn = connectionSource.getSpecialConnection();
        boolean clearSpecial = false;
        if (conn == null) {
            conn = new AndroidDatabaseConnection(db, true);
            try {
                connectionSource.saveSpecialConnection(conn);
                clearSpecial = true;
            } catch (SQLException e) {
                throw new IllegalStateException(
                        "Could not save special connection", e);
            }
        }
        try {
            onCreate();
        } finally {
            if (clearSpecial) {
                connectionSource.clearSpecialConnection(conn);
            }
        }
    }

    private void onCreate() {
        try {
            TableUtils.createTable(connectionSource, TodoList.class);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        DatabaseConnection conn = connectionSource.getSpecialConnection();
        boolean clearSpecial = false;
        if (conn == null) {
            conn = new AndroidDatabaseConnection(db, true);
            try {
                connectionSource.saveSpecialConnection(conn);
                clearSpecial = true;
            } catch (SQLException e) {
                throw new IllegalStateException(
                        "Could not save special connection", e);
            }
        }
        try {
            onUpgrade(oldVersion, newVersion);
        } finally {
            if (clearSpecial) {
                connectionSource.clearSpecialConnection(conn);
            }
        }
    }

    private void onUpgrade(int oldVersion, int newVersion) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, TodoList.class, true);
            // after we drop the old databases, we create the new ones
            onCreate();
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }

    public Dao<TodoList, Integer> getTodoListDao() throws SQLException{
        if(todoListDao == null){
            todoListDao = getDao(TodoList.class);
        }
        return todoListDao;
    }

    private <D extends Dao<T, ?>, T> D getDao(Class<T> clazz)
            throws SQLException {
        // lookup the dao, possibly invoking the cached database config
        Dao<T, ?> dao = DaoManager.lookupDao(connectionSource, clazz);
        if (dao == null) {
            // try to use our new reflection magic
            DatabaseTableConfig<T> tableConfig = DatabaseTableConfigUtil
                    .fromClass(connectionSource, clazz);
            if (tableConfig == null) {
                /**
                 * TODO: we have to do this to get to see if they are using the
                 * deprecated annotations like {@link DatabaseFieldSimple}.
                 */
                dao = (Dao<T, ?>) DaoManager.createDao(connectionSource, clazz);
            } else {
                dao = (Dao<T, ?>) DaoManager.createDao(connectionSource,
                        tableConfig);
            }
        }

        @SuppressWarnings("unchecked")
        D castDao = (D) dao;
        return castDao;
    }

}
