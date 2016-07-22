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
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import ru.rubicon.salary.EmployeeDialogActivity;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        isEtTotalFocused = false;
        salary = new Salary();
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
       /* Intent intent = new Intent(getActivity(), EmployeeDialogActivity.class);
        startActivityForResult(intent, position);*/
        EmployeeDialogFragment employeeDialogFragment = new EmployeeDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putFloat(EmployeeDialogFragment.DIALOG_COEF, salary.getEmployee(position).getCoefficient());
        bundle.putFloat(EmployeeDialogFragment.DIALOG_DAYS, salary.getAmountOfDays(position));
        bundle.putString(EmployeeDialogFragment.DIALOG_NAME, salary.getEmployee(position).getName());
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
            }catch (NumberFormatException e){
                utils.toastShort(getContext(), "Input error");
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int id = requestCode;
        if (resultCode == Activity.RESULT_OK){
            salary.getEmployee(id).setCoefficient(data.getFloatExtra(EmployeeDialogFragment.DIALOG_COEF, 1.0f));
            salary.setAmountOfDays(id, data.getFloatExtra(EmployeeDialogFragment.DIALOG_DAYS, 22.0f));
        }
        adapter.notifyDataSetChanged();
    }
}
