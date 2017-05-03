package ru.rubicon.salary.data.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

import ru.rubicon.salary.domain.entity.Employee;
import ru.rubicon.salary.domain.entity.Salary;
import ru.rubicon.salary.domain.entity.SalaryTableRecord;

/**
 * Created by roma on 03.06.2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper implements BaseColumns {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "salary.db";

    static final String EMPLOYEES_TABLE = "employees";
    static final String EMPLOYEE_NAME = "name";
    static final String EMPLOYEE_COEF = "coef";
    static final String EMPLOYEE_IS_ACTIVE = "is_active";
    static final String EMPLOYEE_HAS_FIXED_SALARY = "has_fixed";
    static final String EMPLOYEE_FIXED_SALARY = "fixed_salary";
    static final String EMPLOYEE_COMMENT = "comment";

    static final String SALARIES_TABLE = "salaries";
    static final String CASH_DATE = "date";
    static final String CASH_TOTAL = "total";
    static final String CASH_COMMENT = "comment";

    static final String RECORDS_TABLE = "records_table";
    static final String RECORDS_SALARY_ID = "salary_id";
    static final String RECORDS_EMPLOYEE = "employee_name";
    static final String RECORDS_COEF = "employee_coef";
    static final String RECORDS_DAYS = "days";
    static final String RECORDS_SALARY = "salary_sum";


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
                CASH_COMMENT + " text);";
        String script_records = "create table " + RECORDS_TABLE + " (" +
                BaseColumns._ID + " integer primary key autoincrement, " +
                RECORDS_SALARY_ID + " integer not null, " +
                RECORDS_EMPLOYEE + " text not null, " +
                RECORDS_COEF + " real not null, " +
                RECORDS_DAYS + " real not null, " +
                RECORDS_SALARY + " integer not null);";
        db.execSQL(script_employee);
        fillEmployeeDataBase(db);
        db.execSQL(script_salaries);
        fillSalaryDatabase(db);
        db.execSQL(script_records);
        fillRecordsDataBase(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + EMPLOYEES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + SALARIES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + RECORDS_TABLE);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + EMPLOYEES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + SALARIES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + RECORDS_TABLE);
        onCreate(db);
    }

    public void fillEmployeeDataBase(SQLiteDatabase db) {
        Filler.fillEmployeeDataBase(db);
    }

    public void fillSalaryDatabase(SQLiteDatabase db) {
        Filler.fillSalaryDatabase(db);
    }

    private void fillRecordsDataBase(SQLiteDatabase db) {
        Filler.fillRecordsDatabse(db);
    }

    private static class Filler{

        private static List<Employee> employees;
        private static Salary salary;

        private static void createEmployeeList(){
            employees = new ArrayList<>();
            employees.add(createEmployee(0, "Viktor", 2.5f));
            employees.add(createEmployee(1, "Leha", 1.5f));
            employees.add(createEmployee(2, "Shurik", 2.5f));
            employees.add(createEmployee(3, "Ivan", 2.5f));
            employees.add(createEmployee(4, "Roman", 2.5f));
            employees.add(createEmployee(5, "Evgenia", 2.5f));
            employees.add(createEmployee(6, "Shurik", 2.5f));
            employees.add(createEmployee(7, "Shurik", 2.5f));
        }

        public static void fillEmployeeDataBase(SQLiteDatabase db) {
            createEmployeeList();
            ContentValues values = new ContentValues();
            for (Employee employee : employees) {
                save(employee, db);
            }
        }

        private static void save(Employee employee, SQLiteDatabase db) {
            ContentValues values = new ContentValues();

//                values.put(BaseColumns._ID, employee.id());
            values.put(EMPLOYEE_NAME, employee.name());
            values.put(EMPLOYEE_COEF, employee.coefficient());
            values.put(EMPLOYEE_IS_ACTIVE, employee.isActive());
            values.put(EMPLOYEE_HAS_FIXED_SALARY, employee.hasFixedSalary());
            values.put(EMPLOYEE_FIXED_SALARY, employee.dailySalary());
            values.put(EMPLOYEE_COMMENT, employee.comment());

            db.insert(EMPLOYEES_TABLE, null, values);
        }

        public static void fillSalaryDatabase(SQLiteDatabase db) {
            if(employees == null){
                createEmployeeList();
            }
            Salary salary = createSalary();

            save(salary, db);

        }

        private static void save(Salary salary, SQLiteDatabase db) {
            ContentValues values = new ContentValues();
            values.clear();
            values.put(_ID, salary.id());
            values.put(CASH_DATE, salary.date());
            values.put(CASH_TOTAL, salary.total());
            values.put(CASH_COMMENT, "");
            db.insert(SALARIES_TABLE, null, values);
        }

        public static void fillRecordsDatabse(SQLiteDatabase db) {
            if(salary == null){
                salary = createSalary();
            }
            ContentValues values = new ContentValues();
            List<SalaryTableRecord> records = salary.salaryTableRecords();
            for (SalaryTableRecord record : records) {
                save(record, db);
            }
        }

        private static void save(SalaryTableRecord record, SQLiteDatabase db) {
            ContentValues values = new ContentValues();
            values.clear();
                values.put(_ID, record.id());
            values.put(RECORDS_SALARY_ID, record.salaryId());
            values.put(RECORDS_EMPLOYEE, record.employee());
            values.put(RECORDS_COEF, record.coefficient());
            values.put(RECORDS_DAYS, record.amountsOfDays());
            values.put(RECORDS_SALARY, record.salary());

            db.insert(RECORDS_TABLE, null, values);
        }

        private static Employee createEmployee(int id, String name, float coef){
            return Employee.create(id, name, coef, true, false, 0, "");
        }

        private static Salary createSalary(){
            int total = 100000;
            int salaryId = 0;
            List<SalaryTableRecord> records = createTableRecords(salaryId, total);
            return Salary.create(salaryId, System.currentTimeMillis(), total, records, "");
        }

        private static List<SalaryTableRecord> createTableRecords(int salaryId, int total) {
            if(employees == null){
                createEmployeeList();
            }
            List<SalaryTableRecord> records = new ArrayList<>();
            int id = 0;
            for (Employee employee : employees) {
                records.add(SalaryTableRecord.create(id++, salaryId, employee.name(), employee.coefficient(), 20, 10000));
            }
            return records;
        }
    }
}
