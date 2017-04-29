package ru.rubicon.salary.presentation.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import ru.rubicon.salary.R;
import ru.rubicon.salary.data.entity.Salary;
import ru.rubicon.salary.utils.utils;

/**
 * Created by roma on 22.06.2016.
 */
public class EmployeeItemListAdapter extends BaseAdapter {

    Context context;
    LayoutInflater lInflater;
    Salary salary;

    OnTextChangedObserver mCallback;
    private int focusedViewId;
    private int selectedPosition;
    private EditText focusedView;

    public interface OnTextChangedObserver {
        public void onCoefTextChanged(int id, float value);
        public void onDaysTextChanged(int id, float value);
    }

    public EmployeeItemListAdapter(Context context, OnTextChangedObserver observer, Salary salary) {
        this.mCallback = observer;
        this.context = context;
        this.salary = salary;
        focusedViewId = -1;
        selectedPosition = -1;
        focusedView = null;

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
    EditText tvCoef, tvDays;*/

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
        /*holder.tvCoef.addTextChangedListener(new CoefTextChangedListener(id));
        holder.tvDays.addTextChangedListener(new DaysTextChangedListener(id));*/
       holder.etCoef.setOnFocusChangeListener(new CoefFocusChangedListener(id, holder));
        holder.etDays.setOnFocusChangeListener(new DaysFocusChangedListener(id, holder));
        //holder.tvDays.setOnKeyListener(new DaysKeyListener());

        if ((id * 2) == focusedViewId){
            int s = selectedPosition;
            holder.etCoef.requestFocus();
            selectedPosition = s;
            focusedView = holder.etCoef;
            moveCursorToTheEnd(holder.etCoef);
        }
        if ((id * 2 + 1) == focusedViewId){
            int s = selectedPosition;
            holder.etDays.requestFocus();
            selectedPosition = s;
            focusedView = holder.etDays;
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
            } else {
                focusedViewId = id * 2;
                selectedPosition = holder.etCoef.getSelectionStart();
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
            }else {
                int s;
                s = holder.etDays.getSelectionStart();
                s = holder.etDays.getSelectionEnd();
                focusedViewId = id * 2 + 1;
                selectedPosition = holder.etDays.getSelectionStart();
            }
        }
    }

    private class DaysKeyListener implements View.OnKeyListener{
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (event.getAction() == event.ACTION_DOWN){
                if (keyCode == event.KEYCODE_ENTER){
                    hideKeyboard(v);
                    return true;
                }
            }
            return false;
        }
    }

    private class ViewHolder {
        TextView tvName, tvSum;
        EditText etCoef, etDays;
        int ref;
    }

    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void notifyDataSetChanged() {
        focusedViewId = -1;
        selectedPosition = -1;
        if (focusedView != null){
            focusedView.clearFocus();
        }
        super.notifyDataSetChanged();
    }

    private void moveCursorToTheEnd(EditText editText){
        if (selectedPosition >= 0){
            editText.setSelection(selectedPosition);
        }else {
            editText.setSelection(editText.getText().length());
        }
    }

}
