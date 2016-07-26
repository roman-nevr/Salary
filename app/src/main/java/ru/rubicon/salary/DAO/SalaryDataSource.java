package ru.rubicon.salary.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import ru.rubicon.salary.entity.Employee;
import ru.rubicon.salary.entity.Salary;
import ru.rubicon.salary.utils.interfaces;

/**
 * Created by admin on 26.07.2016.
 */
public class SalaryDataSource {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    private static final String CHAR = ";";
    private String[] allColumns = {DatabaseHelper._ID, DatabaseHelper.CASH_DATE, DatabaseHelper.CASH_TOTAL,
                    DatabaseHelper.CASH_EMPLOYEE_IDS, DatabaseHelper.CASH_COEFS, DatabaseHelper.CASH_DAYS,
                    DatabaseHelper.CASH_SUMS, DatabaseHelper.CASH_COMMENT};

    public SalaryDataSource(Context context) {
        this.dbHelper = DatabaseHelper.getInstance(context);
    }

    public void open() throws SQLException{
        database = dbHelper.getWritableDatabase();
    }

    public void openForRead() throws SQLException{
        database = dbHelper.getReadableDatabase();
    }

    public void close(){
        database.close();
    }

    public void saveSalary(Salary salary){
        ContentValues values = new ContentValues();
        //values.put(DatabaseHelper._ID, salary.getId()); //id have to form himself
        values.put(DatabaseHelper.CASH_DATE, salary.getDate());
        values.put(DatabaseHelper.CASH_TOTAL, salary.getTotal());
        values.put(DatabaseHelper.CASH_EMPLOYEE_IDS, employeeIdsToString(salary));
        values.put(DatabaseHelper.CASH_COEFS, employeeCoefsToString(salary));
        values.put(DatabaseHelper.CASH_DAYS, floatArrayListToString(salary.getAmountsOfDays()));
        values.put(DatabaseHelper.CASH_SUMS, integerArrayListToString(salary.getEmployeeSalary()));
        values.put(DatabaseHelper.CASH_COMMENT, salary.getComment());
        database.insert(DatabaseHelper.DATABASE_TABLE_FINANCE, null, values);
    }

    public ArrayList<Salary> readAllSalaries(){
        ArrayList<Salary> salaries = new ArrayList<>();
        Cursor cursor = database.query(DatabaseHelper.DATABASE_TABLE_FINANCE,
                new String[]{DatabaseHelper._ID, DatabaseHelper.CASH_DATE, DatabaseHelper.CASH_TOTAL},
                null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            salaries.add(new Salary(cursor.getInt(0), cursor.getLong(1), cursor.getInt(2)));
            cursor.moveToNext();
        }
        cursor.close();
        return  salaries;
    }

    public void updateSalary(Salary salary){
    }

    public Salary readSalary(int id, ArrayList<Employee> all){
        Salary salary = new Salary();
        Cursor cursor = database.query(DatabaseHelper.DATABASE_TABLE_FINANCE,
                allColumns, null, null, null, null, null);

        salary.setId(cursor.getInt(0));
        salary.setTotal(cursor.getInt(2));
        salary.setEmployees(cursorToEmployeesArrayList(cursor, all));
        salary.setAmountsOfDays(stringToFloatArrayList(cursor.getString(5)));
        salary.setEmployeesSalary(stringToIntegerArrayList(cursor.getString(6)));
        salary.setComment(cursor.getString(7));
        cursor.close();
        return salary;
    }

    private String floatArrayListToString (ArrayList<Float> list){
        StringBuilder builder = new StringBuilder();
        for (Number item : list ) {
            builder.append(item);
            builder.append(CHAR);
        }
        return builder.toString();
    }

    private String integerArrayListToString (ArrayList<Integer> list){
        StringBuilder builder = new StringBuilder();
        for (Integer item: list ) {
            builder.append(item);
            builder.append(CHAR);
        }
        return builder.toString();
    }

    private ArrayList<Integer> stringToIntegerArrayList(String string){
        ArrayList<Integer> list = new ArrayList<>();
        String items[] = string.split(CHAR);
        for (String item : items) {
            list.add(Integer.parseInt(item));
        }
        return list;
    }

    private ArrayList<Float> stringToFloatArrayList(String string){
        ArrayList<Float> list = new ArrayList<>();
        String items[] = string.split(CHAR);
        for (String item : items) {
            list.add(Float.parseFloat(item));
        }
        return list;
    }

    private String employeeIdsToString(Salary salary){
        ArrayList<Employee> employees = salary.getEmployees();
        StringBuilder builder = new StringBuilder();
        for (Employee employee: employees){
            builder.append(employee.getId());
            builder.append(CHAR);
        }
        return builder.toString();
    }

    private String employeeCoefsToString(Salary salary){
        ArrayList<Employee> employees = salary.getEmployees();
        StringBuilder builder = new StringBuilder();
        for (Employee employee: employees){
            builder.append(employee.getCoefficient());
            builder.append(CHAR);
        }
        return builder.toString();
    }

    private ArrayList<Employee> cursorToEmployeesArrayList(Cursor cursor, ArrayList<Employee> all){
        ArrayList<Employee> list = new ArrayList<>();
        String[] ids = cursor.getString(3).split(CHAR);
        String[] coefs = cursor.getString(4).split(CHAR);
        for (int k = 0; k < ids.length; k++){
            Employee employee = all.get(Integer.parseInt(ids[k]));
            employee.setCoefficient(Float.parseFloat(coefs[k]));
            list.add(employee);
        }
        return list;
        /*
        private Employee cursorToEmployee(Cursor cursor){
        Employee employee = new Employee(0,"", 0f);
        employee.setId(cursor.getInt(0));
        employee.setName(cursor.getString(1));
        employee.setCoefficient(cursor.getFloat(2));
        employee.setActive(cursor.getInt(3));
        employee.setComment(cursor.getString(4));
        return employee;
    }
         */
    }


}
