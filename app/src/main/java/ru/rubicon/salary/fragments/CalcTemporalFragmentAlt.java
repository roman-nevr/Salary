package ru.rubicon.salary.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import ru.rubicon.salary.DAO.DatabaseHelper;
import ru.rubicon.salary.DAO.EmployeeDataSource;
import ru.rubicon.salary.DAO.SalaryDataSource;
import ru.rubicon.salary.R;
import ru.rubicon.salary.adapter.EmployeeItemListAdapterAlt;
import ru.rubicon.salary.entity.Employee;
import ru.rubicon.salary.entity.Salary;
import ru.rubicon.salary.utils.utils;

/**
 * Created by admin on 21.07.2016.
 */
public class CalcTemporalFragmentAlt extends Fragment implements EmployeeItemListAdapterAlt.OnListItemClickListener {

    private Salary salary;
    private View viewer;
    private ListView lvEmployeesList;
    private EditText etTotal;
    private boolean isEtTotalFocused;
    private BaseAdapter adapter;
    private Button btnCalc;
    private SaveOrUpdate action;

    private EmployeeDataSource employeeDataSource;
    private SalaryDataSource salaryDataSource;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        isEtTotalFocused = false;
        employeeDataSource = new EmployeeDataSource(getContext());
        salaryDataSource = new SalaryDataSource(getContext());
        Bundle bundle = getArguments();
        salary = performSalary(bundle);
    }

    private Salary performSalary(Bundle data) { //1st - new calculation; 2nd - edit salary; 3rd - add new calculation with total and days
        employeeDataSource.openForRead();
        ArrayList<Employee> employees = employeeDataSource.readAllEmployees();
        employeeDataSource.close();
        salary = new Salary();
        action = new SaveOrUpdate();
        if (data == null){
            //1st
            salary.setEmployees(employees);
            salary.calculateSalary();
            action.setCommand(new Save());
        }else {
            int salaryId = data.getInt(DatabaseHelper._ID, -1);
            if (salaryId == -1){
                //3rd
                action.setCommand(new Save());
                int total = data.getInt(DatabaseHelper.CASH_TOTAL);
                float days = data.getFloat(DatabaseHelper.CASH_DAYS);
                salary.setTotal(total);
                salary.setEmployees(employees);
                ArrayList<Float> amountOfDays = new ArrayList<>(2);
                for (int k = 0; k < employees.size(); k++){
                    amountOfDays.add(days);
                }
                salary.setAmountsOfDays(amountOfDays);
            }else {
                salaryDataSource.openForRead();
                salary = salaryDataSource.readSalary(salaryId, employees);
                salaryDataSource.close();
                action.setCommand(new Update());
                //2nd - load salary from database

            }
        }
        return salary;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewer = inflater.inflate(R.layout.calc_temp_alt, container, false);
        etTotal = (EditText) viewer.findViewById(R.id.etTotal);
        lvEmployeesList = (ListView) viewer.findViewById(R.id.lvEmployeesList);

        etTotal.setText(""+salary.getTotal());
        etTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isEtTotalFocused){
                    etTotal.setCursorVisible(true);
                    etTotal.setFocusable(true);
                    //etTotal.setSelection(etTotal.getText().length());
                }
            }
        });
        /*etTotal.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    etTotal.setCursorVisible(false);
                    etTotal.clearFocus();
                    etTotal.setFocusable(false);
                }else {
                    etTotal.setSelection(etTotal.getText().length());
                }
            }
        });*/
        adapter = new EmployeeItemListAdapterAlt(getContext() , this,salary);
        lvEmployeesList.setAdapter(adapter);

        btnCalc = (Button) viewer.findViewById(R.id.btnCalc);
        btnCalc.setOnClickListener(new CalcButtonClickListener());
        hideKeyboard();

        return viewer;
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(viewer.getWindowToken(), 0);
    }

    private void removeFocus(){
        View current = getActivity().getCurrentFocus();
        if (current != null) current.clearFocus();
    }

    private void nop(){
        /*dummy operation*/
    }

    @Override
    public void OnListItemClick(int position) {
        EmployeeCalcDialogFragment employeeDialogFragment = new EmployeeCalcDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(EmployeeCalcDialogFragment.DIALOG_ID, salary.getEmployee(position).getId());
        bundle.putFloat(EmployeeCalcDialogFragment.DIALOG_COEF, salary.getEmployee(position).getCoefficient());
        bundle.putFloat(EmployeeCalcDialogFragment.DIALOG_DAYS, salary.getAmountOfDays(position));
        bundle.putString(EmployeeCalcDialogFragment.DIALOG_NAME, salary.getEmployee(position).getName());
        employeeDialogFragment.setArguments(bundle);
        employeeDialogFragment.setTargetFragment(this, position);
        employeeDialogFragment.show(getFragmentManager(), "ask");
    }

    private class CalcButtonClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            try{
                removeFocus();
                salary.setTotal(Integer.parseInt(etTotal.getText().toString()));
                hideKeyboard();
                salary.calculateSalary();
                adapter.notifyDataSetChanged();
                action.execute();
                action.setCommand(new Update());
            }catch (NumberFormatException e){
                utils.toastShort(getContext(), "Input error");
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK){
            int id = data.getIntExtra(EmployeeCalcDialogFragment.DIALOG_ID,-1);
            float coef = data.getFloatExtra(EmployeeCalcDialogFragment.DIALOG_COEF, 0.0f);
            salary.getEmployee(id).setCoefficient(coef);
            salary.setAmountOfDays(id, data.getFloatExtra(EmployeeCalcDialogFragment.DIALOG_DAYS, 0.0f));
            //employeeDataSource.updateEmployee(id, coef);
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {

        super.onResume();
    }



    @Override
    public void onPause() {
        super.onPause();
        hideKeyboard();
    }

    private interface ICommand{
        void execute();
    }

    private class SaveOrUpdate{
        ICommand command;
        void execute(){
            salaryDataSource.open();
            command.execute();
            salaryDataSource.close();
        }
        void setCommand(ICommand command){
            this.command = command;
        }
    }

    private class Save implements ICommand{
        @Override
        public void execute() {
            salaryDataSource.saveSalary(salary);
        }
    }

    private class Update implements ICommand{
        @Override
        public void execute() {
            salaryDataSource.updateSalary(salary);
        }
    }

}
