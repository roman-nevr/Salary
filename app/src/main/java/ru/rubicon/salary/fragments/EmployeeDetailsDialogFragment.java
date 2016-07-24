package ru.rubicon.salary.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import ru.rubicon.salary.DAO.EmployeeDataSource;
import ru.rubicon.salary.R;
import ru.rubicon.salary.entity.Employee;

import static ru.rubicon.salary.utils.utils.log;

/**
 * Created by roma on 21.06.2016.
 */
public class EmployeeDetailsDialogFragment extends DialogFragment {

    private interface ICommand{
        void execute();
    };

    private View form;
    private EditText etName, etCoef, etComment;
    private CheckBox cbActive;
    Button btnSave;
    private int id;
    private Employee employee;
    private EmployeeDataSource dataSource;
    private SaveOrUpdate action;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        form = getActivity().getLayoutInflater().inflate(R.layout.employee_details, null, true);
        etName = (EditText) form.findViewById(R.id.etName);
        etCoef = (EditText) form.findViewById(R.id.etCoef);
        etComment = (EditText) form.findViewById(R.id.etComment);
        cbActive = (CheckBox) form.findViewById(R.id.checkBox);
        btnSave = (Button) form.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new ButtonSaveOnClickListener());
        action = new SaveOrUpdate();
        dataSource = new EmployeeDataSource(getContext());

        Bundle bundle = getArguments();
        if (bundle != null){
            action.setCommand(new Update());
            id = bundle.getInt(EmployeesListFragment.DIALOG_ID);
            etName.setText(bundle.getString(EmployeesListFragment.DIALOG_NAME));
            etCoef.setText(""+bundle.getFloat(EmployeesListFragment.DIALOG_COEF));
            etComment.setText(bundle.getString(EmployeesListFragment.DIALOG_COMMENT));
            cbActive.setChecked(bundle.getBoolean(EmployeesListFragment.DIALOG_ACTIVE));
        }else {
            action.setCommand(new Save());
        }

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setView(form);
        return builder.create();
    }

    private class SaveOrUpdate{
        ICommand command;
        void execute(){
            dataSource.open();
            command.execute();
            dataSource.close();
        };
        void setCommand(ICommand command){
            this.command = command;
        }
    }

    private class Save implements ICommand{
        @Override
        public void execute() {
            dataSource.saveEmployee(employee);
        }
    }

    private class Update implements ICommand{
        @Override
        public void execute() {
            dataSource.updateEmployee(employee);
        }
    }

    private class ButtonSaveOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String name;
            employee = new Employee(id,"",0f);
            try {
                try {
                    name = etName.getText().toString();
                    if (name.equals("")){
                        throw new RuntimeException();
                    }
                    employee.setName(name);
                }catch (RuntimeException e){
                    showError(etName);
                }
                try {
                    employee.setCoefficient(Float.parseFloat(etCoef.getText().toString()));
                }catch (NumberFormatException e){
                    showError(etCoef);
                }
                try{
                    employee.setComment(etComment.getText().toString());
                }catch (RuntimeException e){
                    showError(etComment);
                }
                employee.setActive(cbActive.isChecked());
                action.execute();
                //hideKeyboard();
                Intent intent = new Intent();
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                dismiss();

            }catch (RuntimeException e){

            }
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        hideKeyboard();
        super.onDismiss(dialog);
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
