package ru.rubicon.salary.presentation.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.rubicon.salary.R;
import ru.rubicon.salary.domain.entity.Employee;
import ru.rubicon.salary.presentation.OnItemClickListener;

public class EmployeeListAdapter extends RecyclerView.Adapter {

    private List<Employee> employees;
    private OnItemClickListener listener;

    public EmployeeListAdapter(List<Employee> employees, OnItemClickListener listener) {
        this.employees = employees;
        this.listener = listener;
        hasStableIds();
    }

    @Override public long getItemId(int position) {
        return employees.get(position).hashCode();
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == 0){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.employees_salary_list, parent, false);
            return new EmployeeViewHolder(view);
        }
        if (viewType == 1){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_item, parent, false);
            return new EmptyHolder(view);
        }
        return null;
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder instanceof EmployeeViewHolder){
            EmployeeViewHolder viewHolder = (EmployeeViewHolder) holder;
            Employee employee = employees.get(position);
            viewHolder.employeeName.setText(employee.name());
            if(employee.coefficient() != 0){
                viewHolder.employeeCoef.setText(String.valueOf(employee.coefficient()));
            }else {
                viewHolder.employeeCoef.setText(String.valueOf(employee.dailySalary()));
            }
            if (!employee.isActive()){
                viewHolder.employeeName.setTextColor(Color.GRAY);
            }
        }

    }

    @Override public int getItemCount() {
        return employees.size() + 1;
    }

    @Override public int getItemViewType(int position) {
        if(position < employees.size()){
            return 0;
        }else {
            return 1;
        }
    }

    class EmployeeViewHolder extends ViewHolder{

        @BindView(R.id.employee_name) TextView employeeName;
        @BindView(R.id.employee_coef) TextView employeeCoef;

        public EmployeeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> {
                int adapterPosition = getAdapterPosition();
                if(adapterPosition != RecyclerView.NO_POSITION){
                    listener.onItemClick(employees.get(adapterPosition).id());
                }
            });
        }
    }

    class EmptyHolder extends ViewHolder{

        public EmptyHolder(View itemView) {
            super(itemView);
        }
    }
}