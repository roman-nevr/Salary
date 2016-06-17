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
 * Created by roma on 17.06.2016.
 */
public class SalaryListFragment extends ListFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList<Employee> employees = new ArrayList<Employee>(Arrays.asList(new Employee("Salary", 16000), new Employee("Salary2", 16000)));

        ListAdapter adapter = new MainActivityAdapter(getContext(), employees);
        setListAdapter(adapter);

        log("Salary_list_fragment");

        /*FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.*/
    }
}
