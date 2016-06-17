package ru.rubicon.salary.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
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

    ArrayList<Employee> employees;
    ListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         employees = new ArrayList<Employee>(Arrays.asList(new Employee("Roman", 16000), new Employee("Shurik", -16000)));

        adapter = new MainActivityAdapter(getContext(), employees);
        setListAdapter(adapter);



        log("list_fragment");

        /*FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.*/
    }

    public void addEmployee(Employee employee){
        employees.add(employee);
        adapter = new MainActivityAdapter(getContext(), employees);
    }



}
