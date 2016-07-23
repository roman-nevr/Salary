package ru.rubicon.salary.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.ArrayList;

import ru.rubicon.salary.entity.Employee;
import ru.rubicon.salary.utils.utils;

/**
 * Created by roma on 23.07.2016.
 */
public class EmployeeDataSource {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    private String[] allColumns = {DatabaseHelper._ID, DatabaseHelper.EMPLOYEE_NAME, DatabaseHelper.EMPLOYEE_COEF};

    public EmployeeDataSource(Context context) {
        dbHelper = DatabaseHelper.getInstance(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
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
        return employee;
    }

    public Employee readEmployee(){
        return new Employee("name", 1);
    }

    public void updateEmployee(Employee employee){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.EMPLOYEE_NAME, employee.getName());
        contentValues.put(DatabaseHelper.EMPLOYEE_COEF, employee.getCoefficient());
        long id = database.update(DatabaseHelper.DATABASE_TABLE_EMPLOYEES, contentValues, ""+BaseColumns._ID + " = ?", new String[]{""+employee.getId()});
        utils.nop();
    }

    public void updateEmployee(int id, float coef){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.EMPLOYEE_COEF, coef);
        long long_id = database.update(DatabaseHelper.DATABASE_TABLE_EMPLOYEES, contentValues, ""+BaseColumns._ID + " = ?", new String[]{""+id});
        utils.nop();
    }


}
