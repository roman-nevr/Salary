package ru.rubicon.salary.presentation.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.rubicon.salary.R;
import ru.rubicon.salary.domain.entity.Salary;
import ru.rubicon.salary.domain.entity.SalaryTableRecord;
import ru.rubicon.salary.presentation.App;
import ru.rubicon.salary.presentation.DetailsRouter;
import ru.rubicon.salary.presentation.adapter.SalaryTableRecordAdapter;
import ru.rubicon.salary.presentation.dialog.EditRecordDialog;
import ru.rubicon.salary.presentation.dialog.EditRecordDialog.EditRecordDialogListener;
import ru.rubicon.salary.presentation.presenter.SalaryDetailsPresenter;

import static ru.rubicon.salary.presentation.activity.DetailsActivity.ID;

/**
 * Created by roma on 22.06.2016.
 */
public class SalaryDetailsFragment extends Fragment implements EditRecordDialogListener {

    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.etTotal) EditText total;
    @BindView(R.id.btnCalc) Button btnCalc;

    private int id;
    private SalaryTableRecordAdapter adapter;

    @Inject SalaryDetailsPresenter presenter;

    @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calc_temp_alt, container, false);
        ButterKnife.bind(this, view);
        initUi(view);
        readId();
        initDi();
        return view;
    }

    private void initDi() {
        App.getInstance().getMainComponent().plusSalaryComponent().inject(this);
        presenter.setId(id);
        presenter.setView(this);
    }

    @Override public void onStart() {
        super.onStart();
        presenter.start();
    }

    private void initUi(View view) {
        btnCalc.setOnClickListener(v -> {
            presenter.onCalcButtonClick();
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(layoutManager);
    }

    public void showSalary(Salary salary){
        total.setText(String.valueOf(salary.total()));
        showTable(salary.salaryTableRecords());
    }

    private void showTable(List<SalaryTableRecord> salaryTableRecords) {
        if(adapter == null){
            adapter = new SalaryTableRecordAdapter(salaryTableRecords, presenter);
            recyclerView.setAdapter(adapter);
        }else {
            adapter.update(salaryTableRecords);
        }
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

    public void showTableRecord(SalaryTableRecord record){
        DialogFragment dialog = EditRecordDialog.getInstance(record);
        dialog.show(getChildFragmentManager(), "ask");
    }

    @Override public void save(int id, float coef, float days) {
        presenter.onRecordSave(id, coef, days);
    }
}
