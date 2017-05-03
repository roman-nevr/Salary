package ru.rubicon.salary.presentation.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.rubicon.salary.R;
import ru.rubicon.salary.domain.entity.Employee;
import ru.rubicon.salary.presentation.App;
import ru.rubicon.salary.presentation.DetailsRouter;
import ru.rubicon.salary.presentation.presenter.EmployeeDetailsPresenter;
import ru.rubicon.salary.utils.Utils;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static ru.rubicon.salary.presentation.activity.DetailsActivity.ID;

/**
 * Created by roma on 21.06.2016.
 */
public class EmployeeDetailsFragment extends Fragment {

    @BindView(R.id.etName) EditText etName;
    @BindView(R.id.etCoef) EditText etCoef;
    @BindView(R.id.etComment) EditText etComment;
    @BindView(R.id.fixed_salary) EditText etFixedSalary;
    @BindView(R.id.checkBox) CheckBox cbActive;
    @BindView(R.id.has_fixed_salary) CheckBox cbHasFixed;
    @BindView(R.id.btnSave) Button btnSave;

    @BindView(R.id.etCoef_layout) TextInputLayout coefLayout;
    @BindView(R.id.fixed_salary_layout) TextInputLayout fixedLayout;

    private int id;

    @Inject EmployeeDetailsPresenter presenter;

    @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.employee_details, container, false);
        initUi(view);
        readId();
        return view;
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initDi();
    }

    private void initDi() {
        App.getInstance().getMainComponent().plusEmployeeComponent().inject(this);
        presenter.setView(this);
        presenter.setId(id);
        presenter.setRouter((DetailsRouter) getActivity());
    }

    private void initUi(View view){
        ButterKnife.bind(this, view);

        btnSave.setOnClickListener(v -> {
            String name = etName.getText().toString();
            String coef = etCoef.getText().toString();
            String comment = etComment.getText().toString();
            boolean isActive = cbActive.isChecked();
            boolean hasFixed = cbHasFixed.isChecked();
            String fixedSalary = etFixedSalary.getText().toString();
            presenter.onSaveClick(name, coef, isActive, comment, hasFixed, fixedSalary);
        });

        cbHasFixed.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                showFixed();
                hideCoef();
            }else {
                showCoef();
                hideFixed();
            }
        });
    }

    private void hideCoef(){
        coefLayout.setEnabled(false);
    }

    private void hideFixed(){
        fixedLayout.setEnabled(false);
//        fixedLayout.setVisibility(GONE);
//        Utils.animateSize(etFixedSalary, etFixedSalary.getHeight(), 0);
    }

    private void showCoef(){
        coefLayout.setEnabled(true);
    }

    private void showFixed(){
        fixedLayout.setEnabled(true);
//        fixedLayout.setVisibility(VISIBLE);
    }

    public void showEmployee(Employee employee){
        etName.setText(employee.name());
        etCoef.setText(String.valueOf(employee.coefficient()));
        etComment.setText(employee.comment());
        cbActive.setChecked(employee.isActive());
        cbHasFixed.setChecked(employee.hasFixedSalary());
        etFixedSalary.setText(String.valueOf(employee.dailySalary()));
        if(!cbHasFixed.isChecked()){
            hideFixed();
        }
    }

    @Override public void onStart() {
        super.onStart();
        presenter.start();
    }

    @Override public void onStop() {
        super.onStop();
        hideKeyboard();
    }

    public static Fragment getInstance(int id){
        Fragment fragment = new EmployeeDetailsFragment();
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

    public void showCoefError(){
        showEditTextError(etCoef);
    }

    public void showFixedError() {
        showEditTextError(etFixedSalary);
    }

    private void showEditTextError(EditText editText){
        editText.requestFocus();
        editText.setSelection(editText.length());
        editText.setError(getResources().getText(R.string.error_message));
    }

    private void hideKeyboard() {
        Utils.hideKeyboard(getActivity(), etName);
    }
}
