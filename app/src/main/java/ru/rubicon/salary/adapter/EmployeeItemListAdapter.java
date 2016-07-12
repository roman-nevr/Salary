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
import ru.rubicon.salary.entity.Salary;
import ru.rubicon.salary.utils.utils;

/**
 * Created by roma on 22.06.2016.
 */
public class EmployeeItemListAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater lInflater;
    private Salary salary;

    OnTextChangedObserver mCallback;
    private int focusedViewId;

    public interface OnTextChangedObserver {
        public void onCoefTextChanged(int id, float value);
        public void onDaysTextChanged(int id, float value);
    }

    public interface KeyboardCallback{
        public void showKeyboard();
        public void hideKeyboard();
    }

    public EmployeeItemListAdapter(Context context, OnTextChangedObserver observer, Salary salary) {
        this.mCallback = observer;
        this.context = context;
        this.salary = salary;

        focusedViewId = -1;

        lInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return salary.getEmployees().size();
    }

    @Override
    public Object getItem(int id) {
        return salary.getEmployees().get(id);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    /*TextView tvName, tvSum;
    EditText etCoef, etDays;*/

    @Override
    public View getView(int id, View convertView, ViewGroup viewGroup) {
        final ViewHolder holder;
        View view = convertView;
        if (view == null){
            holder = new ViewHolder();
            view = lInflater.inflate(R.layout.employees_salary_list_item, viewGroup, false);
            holder.tvName = (TextView) view.findViewById(R.id.tvName);
            holder.tvSum = (TextView) view.findViewById(R.id.tvSum);
            holder.etCoef = (EditText) view.findViewById(R.id.etCoef);
            holder.etDays = (EditText) view.findViewById(R.id.etDays);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        holder.ref = id;

        holder.tvName.setText(salary.getEmployee(id).getName().toCharArray(), 0, salary.getEmployee(id).getName().length());
        holder.tvSum.setText(salary.getEmployeeSalary().get(id).toString().toCharArray(), 0, salary.getEmployeeSalary().get(id).toString().length());
        holder.etCoef.setText(""+salary.getEmployee(id).getCoefficient());
        holder.etDays.setText(""+salary.getAmountOfDays(id));
        /*holder.etCoef.addTextChangedListener(new CoefTextChangedListener(id));
        holder.etDays.addTextChangedListener(new DaysTextChangedListener(id));*/
        holder.etCoef.setOnFocusChangeListener(new CoefFocusChangedListener(id, holder));
        holder.etDays.setOnFocusChangeListener(new DaysFocusChangedListener(id, holder));

        if((id * 2) == focusedViewId){
            holder.etCoef.requestFocus();
            moveCursorToTheEnd(holder.etCoef);
        }
        if((id * 2 + 1) == focusedViewId){
            holder.etDays.requestFocus();
            moveCursorToTheEnd(holder.etDays);
        }

        return view;
    }

    private class CoefTextChangedListener implements TextWatcher{

        private int id;

        public CoefTextChangedListener(int id){
            this.id = id;
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
                value = Float.parseFloat(s.toString());
                mCallback.onCoefTextChanged(id, value);
            }catch (NumberFormatException e){
                utils.toastShort(context, "Input error");
            }

        }
    }

    private class DaysTextChangedListener implements TextWatcher{
        private int id;


        public DaysTextChangedListener(int id){
            this.id = id;
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
                float value = Float.parseFloat(s.toString());
                mCallback.onDaysTextChanged(id, value);
            }catch (NumberFormatException e){
                utils.toastShort(context, "Input error");
            }
        }
    }

    private class CoefFocusChangedListener implements View.OnFocusChangeListener{
        int id;
        ViewHolder holder;

        public CoefFocusChangedListener(int id, ViewHolder holder) {
            this.id = id;
            this.holder = holder;
        }

        @Override
        public void onFocusChange(View view, boolean hasFocus) {
            if (!hasFocus) {
                float value = Float.parseFloat(holder.etCoef.getText().toString());
                mCallback.onCoefTextChanged(id, value);
                focusedViewId = -1;
            }
            else {
                focusedViewId = holder.ref * 2;
            }
        }
    }

    private class DaysFocusChangedListener implements View.OnFocusChangeListener{
        int id;
        ViewHolder holder;

        public DaysFocusChangedListener(int id, ViewHolder holder) {
            this.id = id;
            this.holder = holder;
        }

        @Override
        public void onFocusChange(View view, boolean hasFocus) {
            if (!hasFocus) {
                float value = Float.parseFloat(holder.etDays.getText().toString());
                mCallback.onDaysTextChanged(id, value);
                focusedViewId = -1;
            } else {
                focusedViewId = holder.ref * 2 + 1;
            }
        }
    }

    private class ViewHolder {
        TextView tvName, tvSum;
        EditText etCoef, etDays;
        int ref;
    }

    @Override
    public void notifyDataSetChanged() {
        focusedViewId = -1;
        super.notifyDataSetChanged();
    }

    private void moveCursorToTheEnd(EditText editText){
        editText.setSelection(editText.getText().length());
    }
}
