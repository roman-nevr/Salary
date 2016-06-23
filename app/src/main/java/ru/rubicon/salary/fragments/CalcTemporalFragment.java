package ru.rubicon.salary.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

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
    private ListView lvEmployeesList;
    private Button btnCalc;
    private Salary salary;
    private ArrayList<Employee> employees;
    private ArrayList<Integer> salaries;
    private ArrayList<Integer> amountOfDays;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewer = inflater.inflate(R.layout.calc_temp, container, false);
        etTotal = (EditText) viewer.findViewById(R.id.etTotal);
        lvEmployeesList = (ListView) viewer.findViewById(R.id.lvEmployeesList);
        employees = new ArrayList<Employee>(Arrays.asList(new Employee("Roman", 16000), new Employee("Shurik", -16000)));
        salaries = new ArrayList<>(Arrays.asList(new Integer(20000), new Integer(10000)));

        lvEmployeesList.setAdapter(new EmployeeItemListAdapter(getContext(), this ,employees, salaries));
        btnCalc = (Button) viewer.findViewById(R.id.btnCalc);
        btnCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        return viewer;
    }


    @Override
    public void onCoefTextChanged(int id, float value) {
        utils.snackBarShort(this.getView(), "coef id " + id + " and value " + value);
    }

    @Override
    public void onDaysTextChanged(int id, int value) {
        utils.snackBarShort(this.getView(), "days id " + id + " and value " + value);
    }
}
