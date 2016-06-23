package ru.rubicon.salary.fragments;

import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private ListView lvEmployeesList;
    private Button btnCalc;
    private Salary salary;
    private ArrayList<Employee> employees;
    private ArrayList<Integer> salaries;
    private ArrayList<Float> amountOfDays;
    private BaseAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        /*employees = new ArrayList<Employee>(Arrays.asList(new Employee("Roman",1.2f, 16000), new Employee("Shurik", 1.2f, -16000), new Employee("Leha", 1.1f, 13000), new Employee("Ivan", 1.2f, 13000)));
        salaries = new ArrayList<>(Arrays.asList(new Integer(20000), new Integer(10000),new Integer(20000), new Integer(12000)));
        amountOfDays = new ArrayList<Float>(Arrays.asList(new Float(22), new Float(5.5),new Float(20), new Float(22)));*/
        salary = new Salary();
        /*amountOfDays.add(new Float(20.0));
        amountOfDays.add(new Float(5.5));*/
        //salary = new Salary(new Date(), 15000, employees, salaries, amountOfDays);
    }

     @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewer = inflater.inflate(R.layout.calc_temp, container, false);
        etTotal = (EditText) viewer.findViewById(R.id.etTotal);
        lvEmployeesList = (ListView) viewer.findViewById(R.id.lvEmployeesList);


        adapter = new EmployeeItemListAdapter(getContext(), this ,salary);
        lvEmployeesList.setAdapter(adapter);
        btnCalc = (Button) viewer.findViewById(R.id.btnCalc);
        btnCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    salary.setTotal(Integer.parseInt(etTotal.getText().toString()));
                    salary.calculateSalary();
                    adapter.notifyDataSetChanged();
                }catch (NumberFormatException e){
                    utils.toastShort(getContext(), "Input error");
                }
            }
        });

        return viewer;
    }


    @Override
    public void onCoefTextChanged(int id, float value) {
        //utils.snackBarShort(this.getView(), "coef id " + id + " and value " + value);
        Employee employee = salary.getEmployee(id);
        salary.getEmployee(id).setCoefficient(value);
        id=1;
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDaysTextChanged(int id, float value) {
        //utils.snackBarShort(this.getView(), "days id " + id + " and value " + value);
        salary.setAmountOfDays(id, value);
        id=1;
        adapter.notifyDataSetChanged();

    }
}
