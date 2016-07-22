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
    public static final String DATABASE_TABLE_EMPLOYEES = "employees";
    public static final String DATABASE_TABLE_FINANCE = "finance";
    public static final String EMPLOYEE_NAME = "name";
    public static final String EMPLOYEE_COEF = "coef";
    public static final String EMPLOYEE_START_BALANCE = "start_balance";
    public static final String EMPLOYEE_BALANCE = "balance";
    public static final String EMPLOYEE_ACTIVITY = "activity";
    public static final String EMPLOYEE_COMMENT = "comment";
    public static final String CASH_DATE = "date";
    public static final String CASH_OWNER = "owner";
    public static final String CASH_FLOW = "cash";
    public static final String CASH_COMMENT = "comment";


    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //public DatabaseHelper

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String script = "create table " + DATABASE_TABLE_EMPLOYEES + " (" +
                BaseColumns._ID + " integer primary key autoincrement, " +
                EMPLOYEE_NAME + " text not null, " +
                EMPLOYEE_COEF + " real, " +
                EMPLOYEE_START_BALANCE + " integer, " +
                EMPLOYEE_BALANCE + " integer, " +
                EMPLOYEE_ACTIVITY + " integer, " +
                EMPLOYEE_COMMENT + " text, ";
        db.execSQL(script);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
