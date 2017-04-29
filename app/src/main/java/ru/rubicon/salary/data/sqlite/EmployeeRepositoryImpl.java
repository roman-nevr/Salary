package ru.rubicon.salary.data.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

import ru.rubicon.salary.domain.EmployeeRepository;
import ru.rubicon.salary.domain.entity.Employee;

import static ru.rubicon.salary.data.sqlite.DatabaseHelper.EMPLOYEES_TABLE;
import static ru.rubicon.salary.data.sqlite.DatabaseHelper.EMPLOYEE_COEF;
import static ru.rubicon.salary.data.sqlite.DatabaseHelper.EMPLOYEE_COMMENT;
import static ru.rubicon.salary.data.sqlite.DatabaseHelper.EMPLOYEE_FIXED_SALARY;
import static ru.rubicon.salary.data.sqlite.DatabaseHelper.EMPLOYEE_HAS_FIXED_SALARY;
import static ru.rubicon.salary.data.sqlite.DatabaseHelper.EMPLOYEE_IS_ACTIVE;
import static ru.rubicon.salary.data.sqlite.DatabaseHelper.EMPLOYEE_NAME;


public class EmployeeRepositoryImpl implements EmployeeRepository, BaseColumns{

    private SQLiteDatabase database;
    private ContentValues contentValues;

    public EmployeeRepositoryImpl(DatabaseHelper databaseHelper) {
        database = databaseHelper.getWritableDatabase();
        contentValues = new ContentValues();
    }

    @Override public void saveEmployee(Employee employee) {
        fillContentValues(employee);
        database.insertWithOnConflict(EMPLOYEES_TABLE, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
    }

    @Override public Employee getEmployee(int id) {
        String selection = _ID + " = ?";
        String[] selectionArgs = {"" + id};
        Cursor cursor = database.query(EMPLOYEES_TABLE, null, selection, selectionArgs, null, null, null, null);
        if(cursor.moveToNext()){
            return getEmployeeFromCursor(cursor);
        }else {
            return null;
        }
    }

    @Override public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        Cursor cursor = database.query(EMPLOYEES_TABLE, null, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            employees.add(getEmployeeFromCursor(cursor));
        }
        return employees;
    }

    private void fillContentValues(Employee employee){
        contentValues.clear();
        contentValues.put(_ID, employee.id());
        contentValues.put(EMPLOYEE_NAME, employee.name());
        contentValues.put(EMPLOYEE_COEF, employee.coefficient());
        contentValues.put(EMPLOYEE_IS_ACTIVE, employee.isActive());
        contentValues.put(EMPLOYEE_HAS_FIXED_SALARY, employee.hasFixedSalary());
        contentValues.put(EMPLOYEE_FIXED_SALARY, employee.dailySalary());
        contentValues.put(EMPLOYEE_COMMENT, employee.comment());
    }

    private Employee getEmployeeFromCursor(Cursor cursor) {
        int idIndex = cursor.getColumnIndex(_ID);
        int nameIndex = cursor.getColumnIndex(EMPLOYEE_NAME);
        int coefIndex = cursor.getColumnIndex(EMPLOYEE_COEF);
        int isActiveIndex = cursor.getColumnIndex(EMPLOYEE_IS_ACTIVE);
        int hasFixedSalaryIndex = cursor.getColumnIndex(EMPLOYEE_HAS_FIXED_SALARY);
        int fixedSalaryIndex = cursor.getColumnIndex(EMPLOYEE_FIXED_SALARY);
        int commentIndex = cursor.getColumnIndex(EMPLOYEE_COMMENT);
        return Employee.create(cursor.getInt(idIndex),
                cursor.getString(nameIndex),
                cursor.getFloat(coefIndex),
                getBoolean(cursor.getInt(isActiveIndex)),
                getBoolean(cursor.getInt(hasFixedSalaryIndex)),
                cursor.getInt(fixedSalaryIndex),
                cursor.getString(commentIndex));
    }

    private boolean getBoolean(int anInt) {
        return anInt == 1;
    }

    private int getSqlBooleanFromJavaBoolean(boolean bool) {
        return bool ? 1 : 0;
    }
}
