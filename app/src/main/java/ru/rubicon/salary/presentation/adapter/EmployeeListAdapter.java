package ru.rubicon.salary.presentation.adapter;

import android.support.v7.widget.RecyclerView;
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

/**
 * Created by roma on 16.06.2016.
 */
public class EmployeeListAdapter extends RecyclerView.Adapter<EmployeeListAdapter.EmployeeViewHolder> {

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

    @Override public EmployeeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.employees_salary_list, parent, false);
        return new EmployeeViewHolder(view);
    }

    @Override public void onBindViewHolder(EmployeeViewHolder holder, int position) {
        holder.employeeName.setText(employees.get(position).name());
        holder.employeeCoef.setText(String.valueOf(employees.get(position).coefficient()));
    }

    @Override public int getItemCount() {
        return employees.size();
    }

    class EmployeeViewHolder extends RecyclerView.ViewHolder{

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
}