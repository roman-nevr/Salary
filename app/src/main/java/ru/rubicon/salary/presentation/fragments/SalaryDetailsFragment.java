package ru.rubicon.salary.presentation.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.rubicon.salary.data.DAO.SalaryDataSource;
import ru.rubicon.salary.R;
import ru.rubicon.salary.presentation.adapter.SalariesAdapter;
import ru.rubicon.salary.data.entity.Salary;

import static ru.rubicon.salary.presentation.activity.DetailsActivity.ID;
import static ru.rubicon.salary.utils.utils.log;

/**
 * Created by roma on 22.06.2016.
 */
public class SalaryDetailsFragment extends Fragment {

    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.total) EditText total;
    @BindView(R.id.date) TextView date;

    private int id;

    @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.salary_details, container, false);
        initUi(view);
        readId();
        return view;
    }

    private void initUi(View view) {
        ButterKnife.bind(this, view);

        btnSave.setOnClickListener(v -> {
            //todo
        });
    }

    public static Fragment getInstance(int id){
        Fragment fragment = new SalaryDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ID, id);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static Fragment getInstance(){
        return getInstance(-1);
    }

    private void readId(){
        id = getArguments().getInt(ID, -1);
    }
}
