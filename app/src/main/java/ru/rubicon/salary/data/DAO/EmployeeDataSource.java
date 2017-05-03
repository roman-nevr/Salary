package ru.rubicon.salary.data.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.ArrayList;

import ru.rubicon.salary.data.entity.Employee;
import ru.rubicon.salary.utils.Utils;

public class EmployeeDataSource {
    private SQLiteDatabase database;

    private String[] allColumns = {DatabaseHelper._ID, DatabaseHelper.EMPLOYEE_NAME, DatabaseHelper.EMPLOYEE_COEF, DatabaseHelper.EMPLOYEE_ACTIVITY, DatabaseHelper.CASH_COMMENT};

    public EmployeeDataSource(DatabaseHelper databaseHelper) {
        database = databaseHelper.getWritableDatabase();
    }


    public ArrayList<Employee> readAllEmployees(){
        ArrayList<Employee> employees = new ArrayList<>();
        Cursor cursor = database.query(DatabaseHelper.DATABASE_TABLE_EMPLOYEES,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Employee employee = cursorToEmployee(cursor);
            employees.add(employee);
            cursor.moveToNext();
        }
        cursor.close();
        return employees;
    }

    private Employee cursorToEmployee(Cursor cursor){
        Employee employee = new Employee(0,"", 0f);
        employee.setId(cursor.getInt(0));
        employee.setName(cursor.getString(1));
        employee.setCoefficient(cursor.getFloat(2));
        employee.setActive(cursor.getInt(3));
        employee.setComment(cursor.getString(4));
        return employee;
    }

    public void updateEmployee(Employee employee){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.EMPLOYEE_NAME, employee.getName());
        contentValues.put(DatabaseHelper.EMPLOYEE_COEF, employee.getCoefficient());
        contentValues.put(DatabaseHelper.EMPLOYEE_ACTIVITY, employee.isActive());
        contentValues.put(DatabaseHelper.EMPLOYEE_COMMENT, employee.getComment());
        long id = database.update(DatabaseHelper.DATABASE_TABLE_EMPLOYEES, contentValues, ""+BaseColumns._ID + " = ?", new String[]{""+employee.getId()});
        Utils.nop();
    }

    public void updateEmployee(int id, float coef){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.EMPLOYEE_COEF, coef);
        long long_id = database.update(DatabaseHelper.DATABASE_TABLE_EMPLOYEES, contentValues, ""+BaseColumns._ID + " = ?", new String[]{""+id});
        Utils.nop();
    }

    public void saveEmployee(Employee employee){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.EMPLOYEE_NAME, employee.getName());
        contentValues.put(DatabaseHelper.EMPLOYEE_COEF, employee.getCoefficient());
        contentValues.put(DatabaseHelper.EMPLOYEE_COMMENT, employee.getComment());
        contentValues.put(DatabaseHelper.EMPLOYEE_ACTIVITY, employee.isActive());
        database.insert(DatabaseHelper.DATABASE_TABLE_EMPLOYEES, null, contentValues);
    }
}
