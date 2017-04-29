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
import ru.rubicon.salary.domain.entity.Salary;

/**
 * Created by roma on 22.06.2016.
 */
public class SalariesAdapter extends RecyclerView.Adapter<SalariesAdapter.SalaryViewHolder> {

    private List<Salary> salaries;

    public SalariesAdapter(List<Salary> salaries) {
        this.salaries = salaries;
        hasStableIds();
    }

    @Override public SalaryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.salary_item, parent, false);
        return new SalaryViewHolder(view);
    }

    @Override public void onBindViewHolder(SalaryViewHolder holder, int position) {
        holder.total.setText(String.valueOf(salaries.get(position).total()));
        holder.date.setText(String.valueOf(salaries.get(position).date()));
    }

    @Override
    public long getItemId(int position) {
        return salaries.get(position).hashCode();
    }



    @Override public int getItemCount() {
        return salaries.size();
    }


    public static class SalaryViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.total) TextView total;
        @BindView(R.id.date) TextView date;

        public SalaryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
