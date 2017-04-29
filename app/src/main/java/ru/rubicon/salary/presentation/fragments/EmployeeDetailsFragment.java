package ru.rubicon.salary.presentation.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import ru.rubicon.salary.presentation.presenter.EmployeeDetailsPresenter;

import static ru.rubicon.salary.presentation.activity.DetailsActivity.ID;

/**
 * Created by roma on 21.06.2016.
 */
public class EmployeeDetailsFragment extends Fragment {

    @BindView(R.id.etName) EditText etName;
    @BindView(R.id.etCoef) EditText etCoef;
    @BindView(R.id.etComment) EditText etComment;
    @BindView(R.id.checkBox) CheckBox cbActive;
    @BindView(R.id.btnSave) Button btnSave;
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
    }

    private void initUi(View view){
        ButterKnife.bind(this, view);

        btnSave.setOnClickListener(v -> {
           //todo
        });
    }

    public void showEmployee(Employee employee){
        etName.setText(employee.name());
        etCoef.setText(String.valueOf(employee.coefficient()));
        etComment.setText(employee.comment());
        cbActive.setActivated(employee.isActive());

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

    private void showError(EditText editText){
        editText.requestFocus();
        editText.setSelection(editText.length());
        editText.setError(getResources().getText(R.string.error_message));
        throw new RuntimeException();
    }
    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        //imm.hideSoftInputFromWindow(getActivity()., 0);
        imm.toggleSoftInput(0,0);
    }
}
