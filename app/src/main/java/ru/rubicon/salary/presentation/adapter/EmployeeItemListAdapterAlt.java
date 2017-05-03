package ru.rubicon.salary.presentation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import ru.rubicon.salary.R;
import ru.rubicon.salary.data.entity.Salary;

/**
 * Created by roma on 22.06.2016.
 */
public class EmployeeItemListAdapterAlt extends BaseAdapter {

    Context context;
    LayoutInflater lInflater;
    Salary salary;

    OnListItemClickListener mCallback;
    private int focusedViewId;
    private int selectedPosition;
    private EditText focusedView;

    public interface OnListItemClickListener {
        public void OnListItemClick(int id);
    }

    public EmployeeItemListAdapterAlt(Context context, OnListItemClickListener observer, Salary salary) {
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



    @Override
    public View getView(final int id, View convertView, ViewGroup viewGroup) {
        TextView tvName, tvSum;
        TextView tvCoef, tvDays;
        final ViewHolder holder;
        View view = convertView;
        //holder = new ViewHolder();
        if (true){
            view = lInflater.inflate(R.layout.employees_salary_list_item_alt, viewGroup, false);
            tvName = (TextView) view.findViewById(R.id.tvName);
            tvSum = (TextView) view.findViewById(R.id.salary);
            tvCoef = (TextView) view.findViewById(R.id.tvCoef);
            tvDays = (TextView) view.findViewById(R.id.tvDays);

        }

        tvName.setText(salary.getEmployee(id).getName().toCharArray(), 0, salary.getEmployee(id).getName().length());
        tvSum.setText(salary.getEmployeeSalary().get(id).toString().toCharArray(), 0, salary.getEmployeeSalary().get(id).toString().length());
        tvCoef.setText(""+salary.getEmployee(id).getCoefficient());
        tvDays.setText(""+salary.getAmountOfDays(id));
        //tvName.setText(salary.getEmployee(id).getName().toCharArray(), 0, salary.getEmployee(id).getName().length());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.OnListItemClick(id);
            }
        });

        return view;
    }

    private class ViewHolder {
        TextView tvName, tvSum;
        TextView tvCoef, tvDays;
    }


}
