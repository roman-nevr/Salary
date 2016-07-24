package ru.rubicon.salary.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ru.rubicon.salary.R;
import ru.rubicon.salary.entity.Employee;

/**
 * Created by roma on 22.07.2016.
 */
public class EmployeeCalcDialogFragment extends DialogFragment {

    private View form;
    private Employee employee;
    private float amountOfDays;
    private float coef;
    private int id;
    private EditText etDays, etCoef;
    private TextView tvName;
    private Button btnSave;

    public static final String DIALOG_ID = "id";
    public static final String DIALOG_NAME = "name";
    public static final String DIALOG_DAYS = "days";
    public static final String DIALOG_COEF = "coef";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);
        Bundle bundle = getArguments();
        String name = bundle.getString(DIALOG_NAME);
        if (name != null){
            id = bundle.getInt(DIALOG_ID);
            coef = bundle.getFloat(DIALOG_COEF);
            amountOfDays = bundle.getFloat(DIALOG_DAYS);
        }else {
            id = -1;
            coef = 0f;
            amountOfDays = 0f;
        }
        form = getActivity().getLayoutInflater().inflate(R.layout.employee_calc_dialog, null, true);
        tvName = (TextView) form.findViewById(R.id.etName);
        etCoef = (EditText) form.findViewById(R.id.etCoef);
        etDays = (EditText) form.findViewById(R.id.etDays);
        btnSave = (Button) form.findViewById(R.id.btnSave);
        if (name != null){
            tvName.setText(name.toCharArray(), 0, name.length());
        } else {
            tvName.setText("".toCharArray(),0,0);
        }
        etCoef.setText(""+coef);
        etDays.setText(""+amountOfDays);
        btnSave.setOnClickListener(new ButtonSaveOnClickListener());


        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setView(form)/*.setPositiveButton(R.string.ok_button, this)
                    .setNegativeButton(R.string.cancel_button, null)*/;

        etDays.requestFocus();
        etDays.setSelection(etDays.length());
        showKeyboard(etDays);
        return builder.create();
    }

    private class ButtonSaveOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();

            try {
                try {
                    intent.putExtra(DIALOG_COEF, Float.parseFloat(etCoef.getText().toString()));
                } catch (NumberFormatException e) {
                    showError(etCoef);
                }
                try {
                    intent.putExtra(DIALOG_DAYS, Float.parseFloat(etDays.getText().toString()));
                } catch (NumberFormatException e) {
                    showError(etDays);
                }
                intent.putExtra(DIALOG_ID, id);
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                //hideKeyboard();
                dismiss();
            } catch (RuntimeException e) {
            }

        }

    }

    private void finish(){

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

    private void showKeyboard(View view){
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        /*imm.showSoftInput(view, 1);*/
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }


}
