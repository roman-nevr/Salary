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

}
