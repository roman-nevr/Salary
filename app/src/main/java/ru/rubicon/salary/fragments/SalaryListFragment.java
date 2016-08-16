package ru.rubicon.salary.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

import ru.rubicon.salary.DAO.DatabaseHelper;
import ru.rubicon.salary.DAO.EmployeeDataSource;
import ru.rubicon.salary.DAO.SalaryDataSource;
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

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        mCallback.onSalaryItemClick(salaries.get(position).getId());
        /*dataSource.openForRead();
        employeeDataSource.openForRead();
        employees = employeeDataSource.readAllEmployees();
        Salary salary = dataSource.readSalary(salaries.get(position).getId(), employees);*/

        /*CalcTemporalFragmentAlt calcFragment = new CalcTemporalFragmentAlt();

        Bundle bundle = new Bundle();
        bundle.putInt(DatabaseHelper._ID, salaries.get(position).getId());
        calcFragment.setArguments(bundle);
        calcFragment.setTargetFragment(this, position);*/

        /*
        Employee employee = employees.get(position);
        int employeeId = employee.getId();
        EmployeeDetailsDialogFragment detailsFragment = new EmployeeDetailsDialogFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(DIALOG_ID, employeeId);
        bundle.putString(DIALOG_NAME, employee.getName());
        bundle.putFloat(DIALOG_COEF, employee.getCoefficient());
        bundle.putString(DIALOG_COMMENT, employee.getComment());
        bundle.putBoolean(DIALOG_ACTIVE, employee.isActive());

        detailsFragment.setArguments(bundle);
        detailsFragment.setTargetFragment(this, position);
        detailsFragment.show(getFragmentManager(), "ask_details");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            dataSource.openForRead();
            employees = dataSource.readAllEmployees();
            dataSource.close();
            adapter.notifyDataSetInvalidated();
        }
    }
         */
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
}
