package ru.rubicon.salary.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import ru.rubicon.salary.R;
import ru.rubicon.salary.entity.Employee;
import ru.rubicon.salary.utils.utils;

/**
 * Created by roma on 22.06.2016.
 */
public class EmployeeItemListAdapter extends BaseAdapter {

    Context context;
    ArrayList<Employee> employees;
    ArrayList<Integer> salaries;
    LayoutInflater lInflater;

    OnTextChangedObserver mCallback;

    public interface OnTextChangedObserver {
        public void onCoefTextChanged(int id, float value);
        public void onDaysTextChanged(int id, int value);
    }

    public EmployeeItemListAdapter(Context context, OnTextChangedObserver observer, ArrayList<Employee> employees, ArrayList<Integer> salaries) {
        this.mCallback = observer;
        this.context = context;
        this.employees = employees;
        this.salaries = salaries;
        lInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return employees.size();
    }

    @Override
    public Object getItem(int id) {
        return employees.get(id);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    TextView tvName, tvSum;
    EditText etCoef, etDays;

    @Override
    public View getView(int id, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if (view == null){
            view = lInflater.inflate(R.layout.employees_salary_list_item, viewGroup, false);
        }
        tvName = (TextView) view.findViewById(R.id.tvName);
        tvSum = (TextView) view.findViewById(R.id.tvSum);
        etCoef = (EditText) view.findViewById(R.id.etCoef);
        etDays = (EditText) view.findViewById(R.id.etDays);

        tvName.setText(employees.get(id).getName().toCharArray(), 0, employees.get(id).getName().length());
        tvSum.setText(salaries.get(id).toString().toCharArray(), 0, salaries.get(id).toString().length());
        etCoef.addTextChangedListener(new CoefTextChangedListener(id, etCoef));
        etDays.addTextChangedListener(new DaysTextChangedListener(id, etDays));

        return view;
    }

    private class CoefTextChangedListener implements TextWatcher{

        int id;
        EditText view;

        public CoefTextChangedListener(int id, EditText view){
            this.id = id;
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            float value;
            try {
                value = Float.parseFloat(view.getText().toString());
                mCallback.onCoefTextChanged(id, value);
            }catch (NumberFormatException e){
                utils.toastShort(context, "Input error");
            }

        }
    }

    private class DaysTextChangedListener implements TextWatcher{
        int id;
        EditText view;

        public DaysTextChangedListener(int id, EditText view){
            this.id = id;
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            try {
                int value = Integer.parseInt(view.getText().toString());
                mCallback.onDaysTextChanged(id, value);
            }catch (NumberFormatException e){
                utils.toastShort(context, "Input error");
            }
        }
    }
}
