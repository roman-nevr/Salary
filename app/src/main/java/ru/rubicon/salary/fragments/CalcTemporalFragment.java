package ru.rubicon.salary.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import ru.rubicon.salary.R;
import ru.rubicon.salary.adapter.EmployeeItemListAdapter;
import ru.rubicon.salary.entity.Employee;
import ru.rubicon.salary.entity.Salary;
import ru.rubicon.salary.utils.utils;

/**
 * Created by roma on 22.06.2016.
 */
public class CalcTemporalFragment extends Fragment implements EmployeeItemListAdapter.OnTextChangedObserver{

    private TextView tvRomanSum,  tvVitekSum,  tvShurikSum,  tvLehaSum,  tvIvanSum,  tvDenSum;
    private EditText etRomanCoef, etVitekCoef, etShurikCoef, etLehaCoef, etIvanCoef, etDenCoef;
    private EditText etRomanDays, etVitekDays, etShurikDays, etLehaDays, etIvanDays, etDenDays;
    /*tvRomanSum = (TextView) viewer.findViewById(R.id.tvRomanSum);
        tvVitekSum = (TextView) viewer.findViewById(R.id.tvVitekSum);
        tvLehaSum = (TextView) viewer.findViewById(R.id.tvLehaSum);
        tvShurikSum = (TextView) viewer.findViewById(R.id.tvShurikSum);
        tvIvanSum = (TextView) viewer.findViewById(R.id.tvIvanSum);
        tvDenSum = (TextView) viewer.findViewById(R.id.tvDenSum);
        etRomanCoef = (EditText) viewer.findViewById(R.id.etRomanCoef);
        etVitekCoef = (EditText) viewer.findViewById(R.id.etVitekCoef);
        etLehaCoef = (EditText) viewer.findViewById(R.id.etLehaCoef);
        etShurikCoef = (EditText) viewer.findViewById(R.id.etShurikCoef);
        etIvanCoef = (EditText) viewer.findViewById(R.id.etIvanCoef);
        etDenCoef = (EditText) viewer.findViewById(R.id.etDenCoef);
        etRomanDays = (EditText) viewer.findViewById(R.id.etRomanDays);
        etVitekDays = (EditText) viewer.findViewById(R.id.etVitekDays);
        etLehaDays = (EditText) viewer.findViewById(R.id.etLehaDays);
        etShurikDays = (EditText) viewer.findViewById(R.id.etShurikDays);
        etIvanDays = (EditText) viewer.findViewById(R.id.etIvanDays);
        etDenDays = (EditText) viewer.findViewById(R.id.etDenDays);*/
    private EditText etTotal;
    private boolean isEtTotalFocused;
    private ListView lvEmployeesList;
    private Button btnCalc;
    private Salary salary;
    private ArrayList<Employee> employees;
    private ArrayList<Integer> salaries;
    private ArrayList<Float> amountOfDays;
    private BaseAdapter adapter;
    private View viewer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        salary = new Salary();
        isEtTotalFocused = false;


    }

     @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewer = inflater.inflate(R.layout.calc_temp, container, false);
        etTotal = (EditText) viewer.findViewById(R.id.etTotal);
        lvEmployeesList = (ListView) viewer.findViewById(R.id.lvEmployeesList);

        etTotal.setText(""+salary.getTotal());
        etTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isEtTotalFocused){
                    etTotal.setCursorVisible(true);
                    etTotal.setSelection(etTotal.getText().length());
                }

            }
        });
        etTotal.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    etTotal.setCursorVisible(false);
                }
            }
        });
        adapter = new EmployeeItemListAdapter(getContext(), this ,salary);
        lvEmployeesList.setAdapter(adapter);
        btnCalc = (Button) viewer.findViewById(R.id.btnCalc);
        btnCalc.setOnClickListener(new View.OnClickListener() {
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
        });

        lvEmployeesList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                hideKeyboard();
                removeFocus();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                nop();
            }
        });

        return viewer;
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(viewer.getWindowToken(), 0);
    }


    @Override
    public void onCoefTextChanged(int id, float value) {
        Employee employee = salary.getEmployee(id);
        salary.getEmployee(id).setCoefficient(value);

    }

    @Override
    public void onDaysTextChanged(int id, float value) {
        salary.setAmountOfDays(id, value);
    }

    private void nop(){
    }

    private void removeFocus(){
        View current = getActivity().getCurrentFocus();
        if (current != null) current.clearFocus();
    }


}
