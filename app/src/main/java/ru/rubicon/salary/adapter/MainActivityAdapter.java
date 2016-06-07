package ru.rubicon.salary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import ru.rubicon.salary.R;
import ru.rubicon.salary.entity.Employee;

import java.util.ArrayList;

/**
 * Created by roma on 03.06.2016.
 */
public class MainActivityAdapter extends BaseAdapter {

    Context context;
    LayoutInflater lInflater;
    ArrayList<Employee> names;

    public MainActivityAdapter(Context context, ArrayList<Employee> names) {
        this.context = context;
        this.names = names;
        lInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return names.size();
    }

    @Override
    public Object getItem(int position) {
        return names.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null){
            view = lInflater.inflate(R.layout.main_item, parent, false);
        }
        Employee employee = (Employee) getItem (position);
        TextView tvName = (TextView) view.findViewById(R.id.tvName);

        tvName.setText(employee.getName().toCharArray(), 0, employee.getName().length());

        return view;
    }
}
