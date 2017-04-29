package ru.rubicon.salary;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import ru.rubicon.salary.data.deserealizer.MyAdapterFactory;
import ru.rubicon.salary.di.MainModule;
import ru.rubicon.salary.domain.entity.Employee;
import ru.rubicon.salary.domain.entity.SalaryTableRecord;

public class CommonTests {

    private Gson gson;

    @Before
    public void before(){

        gson = new GsonBuilder()
                .registerTypeAdapterFactory(MyAdapterFactory.create())
                .create();
    }

    @Test
    public void record(){
        Employee employee = Employee.create(1, "I", 1f, true, false, 0, "");
        SalaryTableRecord record = SalaryTableRecord.create(0, employee, 100, 10);
        String json = gson.toJson(record, SalaryTableRecord.class);
        System.out.println(json);
        SalaryTableRecord record2 = gson.fromJson(json, SalaryTableRecord.class);
        System.out.println(record2);

    }

    @Test
    public void records(){
        Employee employee = Employee.create(1, "I", 1f, true, false, 0, "");
        SalaryTableRecord record = SalaryTableRecord.create(0, employee, 100, 10);
        List<SalaryTableRecord> records = new ArrayList<>();
        records.add(record);
        String json = gson.toJson(records);
        List<SalaryTableRecord> records2 = gson.fromJson(json, new TypeToken<List<SalaryTableRecord>>(){}.getType());
        System.out.println(records2);
    }

}
