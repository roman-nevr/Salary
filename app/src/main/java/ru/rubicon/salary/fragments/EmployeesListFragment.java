package ru.rubicon.salary.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Arrays;

import ru.rubicon.salary.DAO.DatabaseHelper;
import ru.rubicon.salary.DAO.EmployeeDataSource;
import ru.rubicon.salary.adapter.MainActivityAdapter;
import ru.rubicon.salary.entity.Employee;
import static ru.rubicon.salary.utils.utils.log;

/**
 * Created by roma on 16.06.2016.
 */
public class EmployeesListFragment extends ListFragment {
    public static final String DIALOG_ID = "id";
    public static final String DIALOG_NAME = "name";
    public static final String DIALOG_COEF = "coef";
    public static final String DIALOG_COMMENT = "comment";
    public static final String DIALOG_ACTIVE = "active";

    ArrayList<Employee> employees;
    MainActivityAdapter adapter;
    EmployeeDataSource dataSource;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // employees = new ArrayList<Employee>(Arrays.asList(new Employee("Roman", 16000), new Employee("Shurik", -16000)));
        dataSource = new EmployeeDataSource(getContext());
        dataSource.open();
        employees = dataSource.readAllEmployees();
        dataSource.close();

        adapter = new MainActivityAdapter(getContext(), employees);
        setListAdapter(adapter);
        setRetainInstance(true);
        log("list_fragment");
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
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
            dataSource.open();
            employees = dataSource.readAllEmployees();
            dataSource.close();
            adapter.notifyDataSetInvalidated();
        }
    }

    public void addEmployee(Employee employee){
        employees.add(employee);
        adapter.notifyDataSetChanged();
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        //imm.hideSoftInputFromWindow(getActivity()., 0);
        imm.toggleSoftInput(0,0);
    }

}
