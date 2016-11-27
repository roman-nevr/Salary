package ru.rubicon.salary.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

import ru.rubicon.salary.DAO.DatabaseHelper;
import ru.rubicon.salary.DAO.EmployeeDataSource;
import ru.rubicon.salary.DAO.SalaryDataSource;
import ru.rubicon.salary.R;
import ru.rubicon.salary.adapter.EmployeeSalariesListAdapter;
import ru.rubicon.salary.adapter.MainActivityAdapter;
import ru.rubicon.salary.entity.Employee;
import ru.rubicon.salary.entity.Salary;

import static ru.rubicon.salary.utils.utils.log;
import static ru.rubicon.salary.utils.utils.nop;

/**
 * Created by roma on 17.06.2016.
 */
public class SalaryListFragment extends ListFragment {

    OnItemClickObserver mCallback;
                // Container Activity must implement this interface
                public interface OnItemClickObserver {
                public void onSalaryItemClick(int position);
            }
        @Override
        public void onAttach(Context context) {
                super.onAttach(context);
                try {
                        mCallback = (OnItemClickObserver) context;
                    } catch (ClassCastException e) {
                        throw new ClassCastException(context.toString()
                                        + " must implement OnItemClickObserver");
                    }
            }


    private SalaryDataSource dataSource;
    private EmployeeDataSource employeeDataSource;
    private ArrayList<Salary> salaries;
    private ArrayList<Employee> employees;
    private BaseAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataSource = new SalaryDataSource(getContext());

        setRetainInstance(true);

        setHasOptionsMenu(true);

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        mCallback.onSalaryItemClick(salaries.get(position).getId());
    }

    @Override
    public void onResume() {
        super.onResume();
        //employeeDataSource = new EmployeeDataSource(getContext());
        dataSource.openForRead();
        //employees = employeeDataSource.readAllEmployees();
        salaries = dataSource.readAllSalaries();
        dataSource.close();

        adapter = new EmployeeSalariesListAdapter(getContext(), salaries);
        setListAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }
}
