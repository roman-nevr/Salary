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
import ru.rubicon.salary.domain.entity.SalaryTableRecord;
import ru.rubicon.salary.presentation.OnItemClickListener;
import ru.rubicon.salary.presentation.presenter.SalaryDetailsPresenter;

public class SalaryTableRecordAdapter extends RecyclerView.Adapter<SalaryTableRecordAdapter.EmployeeRecordViewHolder> {

    private List<SalaryTableRecord> records;
    private OnItemClickListener listener;

    public SalaryTableRecordAdapter(List<SalaryTableRecord> records, OnItemClickListener listener) {
        this.records = records;
        this.listener = listener;
    }

    @Override public EmployeeRecordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.employees_salary_list_item_alt, parent, false);
        return new EmployeeRecordViewHolder(view);
    }

    @Override public void onBindViewHolder(EmployeeRecordViewHolder holder, int position) {
        SalaryTableRecord record = records.get(position);
        holder.name.setText(record.employee());
        if(record.coefficient() != 0){
            holder.coef.setText(String.valueOf(record.coefficient()));
        } else {
            holder.coef.setText(String.valueOf(record.dailySalary()));
        }
        holder.days.setText(String.valueOf(record.amountsOfDays()));
        holder.salary.setText(String.valueOf(record.salary()));
    }

    @Override public int getItemCount() {
        return records.size();
    }

    public void update(List<SalaryTableRecord> salaryTableRecords) {
        records = salaryTableRecords;
        notifyDataSetChanged();
    }

    class EmployeeRecordViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tvName) TextView name;
        @BindView(R.id.tvCoef) TextView coef;
        @BindView(R.id.tvDays) TextView days;
        @BindView(R.id.salary) TextView salary;

        public EmployeeRecordViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> {
                int adapterPosition = getAdapterPosition();
                if(adapterPosition != RecyclerView.NO_POSITION){
                    listener.onItemClick(records.get(adapterPosition).id());
                }
            });
        }
    }
}
