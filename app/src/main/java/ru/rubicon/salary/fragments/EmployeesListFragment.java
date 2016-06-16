package ru.rubicon.salary.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.Arrays;

import ru.rubicon.salary.adapter.MainActivityAdapter;
import ru.rubicon.salary.entity.Employee;

import static ru.rubicon.salary.utils.utils.log;

/**
 * Created by roma on 16.06.2016.
 */
public class EmployeesListFragment extends ListFragment {
    private final String[] mCatNames = new String[]{"Рыжик", "Барсик",
            "Мурзик", "Мурка", "Васька", "Томасина", "Бобик", "Кристина",
            "Пушок", "Дымка", "Кузя", "Китти", "Барбос", "Масяня", "Симба"};


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList<Employee> employees = new ArrayList<Employee>(Arrays.asList(new Employee("Roman", 16000), new Employee("Shurik", -16000)));

        ListAdapter adapter = new MainActivityAdapter(getContext(), employees);
        setListAdapter(adapter);

        log("list_fragment");
    }



}
