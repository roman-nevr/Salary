package ru.rubicon.salary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import ru.rubicon.salary.R;
import ru.rubicon.salary.entity.Employee;
import ru.rubicon.salary.entity.Salary;

/**
 * Created by roma on 22.06.2016.
 */
public class EmployeesSalaryListAdapter extends BaseAdapter {

    Context context;
    LayoutInflater lInflater;
    ArrayList<Salary> salaries;

    public EmployeesSalaryListAdapter(Context context, ArrayList<Salary> salaries) {
        this.context = context;
        lInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        this.salaries = salaries;
    }

    @Override
    public int getCount() {
        return salaries.size();
    }

    @Override
    public Object getItem(int position) {
        return salaries.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if (view == null){
            view = lInflater.inflate(R.layout.main_item, viewGroup, false);
        }
        return view;
    }
}
