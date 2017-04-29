package ru.rubicon.salary.data.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import ru.rubicon.salary.domain.SalaryDataRepository;
import ru.rubicon.salary.domain.entity.Salary;
import ru.rubicon.salary.domain.entity.SalaryTableRecord;

import static ru.rubicon.salary.data.sqlite.DatabaseHelper.CASH_COMMENT;
import static ru.rubicon.salary.data.sqlite.DatabaseHelper.CASH_DATE;
import static ru.rubicon.salary.data.sqlite.DatabaseHelper.CASH_TABLE_RECORDS;
import static ru.rubicon.salary.data.sqlite.DatabaseHelper.CASH_TOTAL;
import static ru.rubicon.salary.data.sqlite.DatabaseHelper.SALARIES_TABLE;

public class SalaryDataRepositoryImpl implements SalaryDataRepository, BaseColumns {

    private final Gson gson;
    private SQLiteDatabase database;
    private ContentValues contentValues;

    public SalaryDataRepositoryImpl(DatabaseHelper databaseHelper, Gson gson) {
        database = databaseHelper.getWritableDatabase();
        contentValues = new ContentValues();
        this.gson = gson;
    }

    @Override public void saveSalary(Salary salary) {
        fillContentValues(salary);
        database.insertWithOnConflict(SALARIES_TABLE, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
    }

    @Override public Salary getSalary(int id) {
        String selection = _ID + " = ?";
        String[] selectionArgs = {"" + id};
        Cursor cursor = database.query(SALARIES_TABLE, null, selection, selectionArgs, null, null, null, null);
        if(cursor.moveToNext()){
            return getSalaryFromCursor(cursor);
        }else {
            return null;
        }
    }

    @Override public List<Salary> getAllSalaries() {
        List<Salary> salaries = new ArrayList<>();
        Cursor cursor = database.query(SALARIES_TABLE, null, null, null, null, null, null, null);
        while (cursor.moveToNext()){
            salaries.add(getSalaryFromCursor(cursor));
        }
        return salaries;
    }

    private void fillContentValues(Salary salary){
        contentValues.clear();
        contentValues.put(_ID, salary.id());
        contentValues.put(CASH_DATE, salary.date());
        contentValues.put(CASH_TOTAL, salary.total());
        contentValues.put(CASH_TABLE_RECORDS, gson.toJson(salary.salaryTableRecords()));
        contentValues.put(CASH_COMMENT, salary.comment());
    }

    private Salary getSalaryFromCursor(Cursor cursor) {
        int idIndex = cursor.getColumnIndex(_ID);
        int dateIndex = cursor.getColumnIndex(CASH_DATE);
        int totalIndex = cursor.getColumnIndex(CASH_TOTAL);
        int recordsIndex = cursor.getColumnIndex(CASH_TABLE_RECORDS);
        int commentIndex = cursor.getColumnIndex(CASH_COMMENT);
        List<SalaryTableRecord> records = gson.fromJson(cursor.getString(recordsIndex), new TypeToken<List<SalaryTableRecord>>(){}.getType());
        return Salary.create(cursor.getInt(idIndex),
                cursor.getLong(dateIndex),
                cursor.getInt(totalIndex),
                records,
                cursor.getString(commentIndex));
    }
}
