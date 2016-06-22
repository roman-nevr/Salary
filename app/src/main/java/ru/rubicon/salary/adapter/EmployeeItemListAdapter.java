package ru.rubicon.salary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import ru.rubicon.salary.R;
import ru.rubicon.salary.entity.Employee;

/**
 * Created by roma on 22.06.2016.
 */
public class EmployeeItemListAdapter extends BaseAdapter {

    Context context;
    ArrayList<Employee> employees;
    LayoutInflater lInflater;

    public EmployeeItemListAdapter(Context context, ArrayList<Employee> employees) {
        this.context = context;
        this.employees = employees;
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

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if (view == null){
            view = lInflater.inflate(R.layout.employees_salary_list_item, viewGroup, false);
        }
        return view;
    }
}
