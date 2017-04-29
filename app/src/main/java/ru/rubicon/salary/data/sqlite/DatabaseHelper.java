package ru.rubicon.salary.data.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import ru.rubicon.salary.data.deserealizer.MyAdapterFactory;
import ru.rubicon.salary.domain.entity.Employee;
import ru.rubicon.salary.domain.entity.Salary;
import ru.rubicon.salary.domain.entity.SalaryTableRecord;

/**
 * Created by roma on 03.06.2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper implements BaseColumns {

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "salary.db";

    public static final String EMPLOYEES_TABLE = "employees";
    public static final String EMPLOYEE_NAME = "name";
    public static final String EMPLOYEE_COEF = "coef";
    public static final String EMPLOYEE_IS_ACTIVE = "is_active";
    public static final String EMPLOYEE_HAS_FIXED_SALARY = "has_fixed";
    public static final String EMPLOYEE_FIXED_SALARY = "fixed_salary";
    public static final String EMPLOYEE_COMMENT = "comment";

    public static final String SALARIES_TABLE = "salaries";
    public static final String CASH_DATE = "date";
    public static final String CASH_TOTAL = "total";
    public static final String CASH_TABLE_RECORDS = "records";
    public static final String CASH_COMMENT = "comment";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String script_employee = "create table " + EMPLOYEES_TABLE + " (" +
                BaseColumns._ID + " integer primary key autoincrement, " +
                EMPLOYEE_NAME + " text not null, " +
                EMPLOYEE_COEF + " real not null, " +
                EMPLOYEE_IS_ACTIVE + " integer not null, " +
                EMPLOYEE_HAS_FIXED_SALARY + " integer not null, " +
                EMPLOYEE_FIXED_SALARY + " integer not null, " +
                EMPLOYEE_COMMENT + " text);";
        String script_salaries = "create table " + SALARIES_TABLE + " (" +
                BaseColumns._ID + " integer primary key autoincrement, " +
                CASH_DATE + " integer not null, " +
                CASH_TOTAL + " integer not null, " +
                CASH_TABLE_RECORDS + " text not null, " +
                CASH_COMMENT + " text);";
        db.execSQL(script_employee);
        fillEmployeeDataBase(db);
        db.execSQL(script_salaries);
        fillSalaryDatabase(db);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + EMPLOYEES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + SALARIES_TABLE);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + EMPLOYEES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + SALARIES_TABLE);
        onCreate(db);
    }

    public void fillEmployeeDataBase(SQLiteDatabase db) {
        //ToDO
//        ContentValues values = new ContentValues();
//        values.put(BaseColumns._ID, 0);
//        values.put(EMPLOYEE_NAME, "Roman");
//        values.put(EMPLOYEE_COEF, 1f);
//        values.put(EMPLOYEE_IS_ACTIVE, true);
//        values.put(EMPLOYEE_HAS_FIXED_SALARY, false);
//        values.put(EMPLOYEE_FIXED_SALARY, 0);
//        values.put(EMPLOYEE_COMMENT, "");
//
//        db.insert(EMPLOYEES_TABLE, null, values);
//
//        values.put(BaseColumns._ID, 1);
//        values.put(EMPLOYEE_NAME, "Viktor");
//        values.put(EMPLOYEE_COEF, 1f);
//        values.put(EMPLOYEE_IS_ACTIVE, true);
//        values.put(EMPLOYEE_HAS_FIXED_SALARY, false);
//        values.put(EMPLOYEE_FIXED_SALARY, 0);
//        values.put(EMPLOYEE_COMMENT, "");
//
//        db.insert(EMPLOYEES_TABLE, null, values);
        Filler.fillEmployeeDataBase(db);
    }

    public void fillSalaryDatabase(SQLiteDatabase db) {
        //ToDO
//        Employee employee = Employee.create(1, "I", 1f, true, false, 0, "");
//        SalaryTableRecord record = SalaryTableRecord.create(0, employee, 100, 10);
//        List<SalaryTableRecord> records = new ArrayList<>();
//        records.add(record);
//
//        Gson gson = new GsonBuilder()
//                .registerTypeAdapterFactory(MyAdapterFactory.create())
//                .create();
//
//        ContentValues values = new ContentValues();
//        values.put(_ID, 0);
//        values.put(CASH_DATE, System.currentTimeMillis());
//        values.put(CASH_TOTAL, 100000);
//        values.put(CASH_TABLE_RECORDS, gson.toJson(records));
//        values.put(CASH_COMMENT, "");
//        db.insert(SALARIES_TABLE, null, values);
        Filler.fillSalaryDatabase(db);
    }

    private static class Filler{

        private static List<Employee> employees;

        private static void createEmployeeList(){
            employees = new ArrayList<>();
            employees.add(createEmployee(0, "Viktor", 2.5f));
            employees.add(createEmployee(1, "Leha", 1.5f));
            employees.add(createEmployee(2, "Shurik", 2.5f));
        }

        public static void fillEmployeeDataBase(SQLiteDatabase db) {
            createEmployeeList();
            ContentValues values = new ContentValues();
            for (Employee employee : employees) {
                values.clear();

                values.put(BaseColumns._ID, employee.id());
                values.put(EMPLOYEE_NAME, employee.name());
                values.put(EMPLOYEE_COEF, employee.coefficient());
                values.put(EMPLOYEE_IS_ACTIVE, employee.isActive());
                values.put(EMPLOYEE_HAS_FIXED_SALARY, employee.hasFixedSalary());
                values.put(EMPLOYEE_FIXED_SALARY, employee.dailySalary());
                values.put(EMPLOYEE_COMMENT, employee.comment());

                db.insert(EMPLOYEES_TABLE, null, values);
            }
        }

        public static void fillSalaryDatabase(SQLiteDatabase db) {
            if(employees == null){
                createEmployeeList();
            }
            ContentValues values = new ContentValues();
            Salary salary = createSalary();

            Gson gson = new GsonBuilder()
                    .registerTypeAdapterFactory(MyAdapterFactory.create())
                    .create();

            values.clear();
            values.put(_ID, salary.id());
            values.put(CASH_DATE, salary.date());
            values.put(CASH_TOTAL, salary.total());
            values.put(CASH_TABLE_RECORDS, gson.toJson(createTableRecords()));
            values.put(CASH_COMMENT, "");
            db.insert(SALARIES_TABLE, null, values);

        }

        private static Employee createEmployee(int id, String name, float coef){
            return Employee.create(id, name, coef, true, false, 0, "");
        }

        private static Salary createSalary(){
            List<SalaryTableRecord> records = createTableRecords();
            return Salary.create(0, System.currentTimeMillis(), 100000, records, "");
        }

        private static List<SalaryTableRecord> createTableRecords() {
            List<SalaryTableRecord> records = new ArrayList<>();
            for (Employee employee : employees) {
                records.add(SalaryTableRecord.create(0, employee, 10000, 20));
            }
            return records;
        }
    }
}
