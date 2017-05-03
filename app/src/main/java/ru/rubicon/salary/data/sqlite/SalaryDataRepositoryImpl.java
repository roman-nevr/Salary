package ru.rubicon.salary.data.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import ru.rubicon.salary.domain.SalaryDataRepository;
import ru.rubicon.salary.domain.entity.Salary;
import ru.rubicon.salary.domain.entity.SalaryTableRecord;

import static ru.rubicon.salary.data.sqlite.DatabaseHelper.CASH_COMMENT;
import static ru.rubicon.salary.data.sqlite.DatabaseHelper.CASH_DATE;
import static ru.rubicon.salary.data.sqlite.DatabaseHelper.CASH_TOTAL;
import static ru.rubicon.salary.data.sqlite.DatabaseHelper.RECORDS_COEF;
import static ru.rubicon.salary.data.sqlite.DatabaseHelper.RECORDS_DAILY_SALARY;
import static ru.rubicon.salary.data.sqlite.DatabaseHelper.RECORDS_DAYS;
import static ru.rubicon.salary.data.sqlite.DatabaseHelper.RECORDS_EMPLOYEE;
import static ru.rubicon.salary.data.sqlite.DatabaseHelper.RECORDS_SALARY;
import static ru.rubicon.salary.data.sqlite.DatabaseHelper.RECORDS_SALARY_ID;
import static ru.rubicon.salary.data.sqlite.DatabaseHelper.RECORDS_TABLE;
import static ru.rubicon.salary.data.sqlite.DatabaseHelper.SALARIES_TABLE;

public class SalaryDataRepositoryImpl implements SalaryDataRepository, BaseColumns {

    private SQLiteDatabase database;
    private ContentValues contentValues;

    public SalaryDataRepositoryImpl(DatabaseHelper databaseHelper) {
        database = databaseHelper.getWritableDatabase();
        contentValues = new ContentValues();
    }

    @Override public int saveSalary(Salary salary) {
        long id;
        if(salary.id() < 0){
            fillContentValues(salary);
            id = database.insert(SALARIES_TABLE, null, contentValues);
        }else {
            fillContentValues(salary);
            id = database.insertWithOnConflict(SALARIES_TABLE, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
        }
        for (SalaryTableRecord record : salary.salaryTableRecords()) {
            saveSalaryTableRecord(record.toBuilder().salaryId((int)id).build());
        }
        return (int)id;
    }

    @Override public Salary getSalary(int id) {
        String selection = _ID + " = ?";
        String[] selectionArgs = {"" + id};
        Cursor cursor = database.query(SALARIES_TABLE, null, selection, selectionArgs, null, null, null, null);
        Salary salary = null;
        if(cursor.moveToNext()){
            List<SalaryTableRecord> records = getSalaryTableRecords(cursor.getInt(cursor.getColumnIndex(_ID)));
            salary = getSalaryFromCursor(cursor, records);
        }
        cursor.close();
        return salary;
    }

    @Override public List<Salary> getAllSalaries() {

        List<Salary> salaries = new ArrayList<>();
        Cursor cursor = database.query(SALARIES_TABLE, null, null, null, null, null, null, null);
        while (cursor.moveToNext()){
            List<SalaryTableRecord> records = getSalaryTableRecords(cursor.getInt(cursor.getColumnIndex(_ID)));

            salaries.add(getSalaryFromCursor(cursor, records));
        }
        cursor.close();
        return salaries;
    }

    private List<SalaryTableRecord> getSalaryTableRecords(int id) {
        List<SalaryTableRecord> records = new ArrayList<>();
        String selection = RECORDS_SALARY_ID + " = ?";
        String[] selectionArgs = {"" + id};
        Cursor cursor = database.query(RECORDS_TABLE, null, selection, selectionArgs, null, null, null, null);
        while (cursor.moveToNext()){
             records.add(getSalaryTableRecordFromCursor(cursor));
        }
        cursor.close();
        return records;
    }

    @Override public void saveSalaryTableRecord(SalaryTableRecord record) {
        fillContentValues(record);
        database.insertWithOnConflict(RECORDS_TABLE, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
    }

    @Override public SalaryTableRecord getSalaryTableRecord(int id) {
        String selection = _ID + " = ?";
        String[] selectionArgs = {"" + id};
        Cursor cursor = database.query(RECORDS_TABLE, null, selection, selectionArgs, null, null, null, null);
        SalaryTableRecord record = null;
        if(cursor.moveToNext()){
            record = getSalaryTableRecordFromCursor(cursor);
        }
        cursor.close();
        return record;
    }

    @Override public void deleteSalary(int id) {
        String selection = String.format("%1s = ?", _ID);
        String[] selectionArgs = {String.valueOf(id)};
        database.delete(SALARIES_TABLE, selection, selectionArgs);

        selection = String.format("%1s = ?", RECORDS_SALARY_ID);
        database.delete(RECORDS_TABLE, selection, selectionArgs);
    }

    private SalaryTableRecord getSalaryTableRecordFromCursor(Cursor cursor) {
        int idIndex = cursor.getColumnIndex(_ID);
        int salaryIdIndex = cursor.getColumnIndex(RECORDS_SALARY_ID);
        int employeeIndex = cursor.getColumnIndex(RECORDS_EMPLOYEE);
        int coefIndex = cursor.getColumnIndex(RECORDS_COEF);
        int dailyIndex = cursor.getColumnIndex(RECORDS_DAILY_SALARY);
        int daysIndex = cursor.getColumnIndex(RECORDS_DAYS);
        int salaryIndex = cursor.getColumnIndex(RECORDS_SALARY);
        return SalaryTableRecord.create(cursor.getInt(idIndex),
                cursor.getInt(salaryIdIndex),
                cursor.getString(employeeIndex),
                cursor.getFloat(coefIndex),
                cursor.getInt(dailyIndex),
                cursor.getFloat(daysIndex),
                cursor.getInt(salaryIndex));
    }

    private void fillContentValues(Salary salary){
        contentValues.clear();
        if(salary.id() >= 0){
            contentValues.put(_ID, salary.id());
        }
        contentValues.put(CASH_DATE, salary.date());
        contentValues.put(CASH_TOTAL, salary.total());
        contentValues.put(CASH_COMMENT, salary.comment());
    }

    private void fillContentValues(SalaryTableRecord record){
        contentValues.clear();
        if (record.id() >= 0){
            contentValues.put(_ID, record.id());
        }
        contentValues.put(RECORDS_SALARY_ID, record.salaryId());
        contentValues.put(RECORDS_EMPLOYEE, record.employee());
        contentValues.put(RECORDS_COEF, record.coefficient());
        contentValues.put(RECORDS_DAILY_SALARY, record.dailySalary());
        contentValues.put(RECORDS_DAYS, record.amountsOfDays());
        contentValues.put(RECORDS_SALARY, record.salary());
    }

    private Salary getSalaryFromCursor(Cursor cursor, List<SalaryTableRecord> records) {
        int idIndex = cursor.getColumnIndex(_ID);
        int dateIndex = cursor.getColumnIndex(CASH_DATE);
        int totalIndex = cursor.getColumnIndex(CASH_TOTAL);
        int commentIndex = cursor.getColumnIndex(CASH_COMMENT);
        return Salary.create(cursor.getInt(idIndex),
                cursor.getLong(dateIndex),
                cursor.getInt(totalIndex),
                records,
                cursor.getString(commentIndex));
    }
}
