package ru.rubicon.salary.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;

import ru.rubicon.salary.entity.Employee;
import ru.rubicon.salary.entity.Salary;
import ru.rubicon.salary.utils.utils;

/**
 * Created by roma on 03.06.2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper implements BaseColumns{

    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "salary.db";

    public static final String DATABASE_TABLE_EMPLOYEES = "employees";
    public static final String EMPLOYEE_NAME = "name";
    public static final String EMPLOYEE_COEF = "coef";
    public static final String EMPLOYEE_START_BALANCE = "start_balance";
    public static final String EMPLOYEE_BALANCE = "balance";
    public static final String EMPLOYEE_ACTIVITY = "activity";
    public static final String EMPLOYEE_COMMENT = "comment";

    public static final String DATABASE_TABLE_FINANCE = "salaries";
    public static final String CASH_DATE = "date";
    public static final String CASH_TOTAL = "total";
    public static final String CASH_EMPLOYEE_IDS = "ids";
    public static final String CASH_COEFS = "coefs";
    public static final String CASH_DAYS = "days";
    public static final String CASH_SUMS = "sums";
    public static final String CASH_OWNER = "owner";
    public static final String CASH_FLOW = "cash";
    public static final String CASH_COMMENT = "comment";

    private static DatabaseHelper instance;

    public static DatabaseHelper getInstance (Context context){
        if (instance == null){
            return new DatabaseHelper(context);
        } else {
            return instance;
        }
    }

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String script_employee = "create table " + DATABASE_TABLE_EMPLOYEES + " (" +
                BaseColumns._ID + " integer primary key autoincrement, " +
                EMPLOYEE_NAME + " text not null, " +
                EMPLOYEE_COEF + " real, " +
                EMPLOYEE_START_BALANCE + " integer, " +
                EMPLOYEE_BALANCE + " integer, " +
                EMPLOYEE_ACTIVITY + " integer, " +
                EMPLOYEE_COMMENT + " text);";
        String script_salaries = "create table " + DATABASE_TABLE_FINANCE + " (" +
                BaseColumns._ID + " integer primary key autoincrement, " +
                CASH_DATE + " integer, " +
                CASH_TOTAL + " integer, " +
                CASH_EMPLOYEE_IDS + " text, " +
                CASH_COEFS + " text, " +
                CASH_DAYS + " text, " +
                CASH_SUMS + " text, " +
                CASH_COMMENT + " text);";
        db.execSQL(script_employee);
        fillEmployeeDataBase(db);
        db.execSQL(script_salaries);
        //fillSalaryTable(db);
    }

    private void fillSalaryTable(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_EMPLOYEES);
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_FINANCE);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_EMPLOYEES);
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_FINANCE);
        onCreate(db);
    }

    public void fillEmployeeDataBase(SQLiteDatabase db){
        ArrayList<Employee> employees = new Salary().getEmployees();
        if( db != null ){
            ContentValues values;

            for(Employee employee:employees){
                values = new ContentValues();
                values.put(BaseColumns._ID, employee.getId());
                values.put(EMPLOYEE_NAME, employee.getName());
                values.put(EMPLOYEE_COEF, employee.getCoefficient());
                values.put(EMPLOYEE_ACTIVITY, true);
                db.insert(DATABASE_TABLE_EMPLOYEES, null, values);
            }
        }
        else {
            utils.log("database is null");
        }
    }
}
