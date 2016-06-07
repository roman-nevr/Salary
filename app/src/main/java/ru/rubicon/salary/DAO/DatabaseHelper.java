package ru.rubicon.salary.DAO;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by roma on 03.06.2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper implements BaseColumns{

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "salary.db";
    public static final String DATABASE_TABLE_NAMES = "names";
    public static final String DATABASE_TABLE_FINANCE = "finance";
    public static final String EMPLOYEE_NAME = "name";
    public static final String EMPLOYEE_ACTIVITY = "activity";
    public static final String EMPLOYEE_COMMENT = "comment";
    public static final String CASH_DATE = "date";
    public static final String CASH_OWNER = "owner";
    public static final String CASH_FLOW = "cash";
    public static final String CASH_COMMENT = "comment";


    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String script = "create table " + DATABASE_TABLE_NAMES + " (" +
                BaseColumns._ID + " integer primary key autoincrement, " +
                EMPLOYEE_NAME + " text not null, " +
                EMPLOYEE_ACTIVITY + "integer, " +
                EMPLOYEE_COMMENT + "text,";

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
