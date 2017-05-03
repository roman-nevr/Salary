package ru.rubicon.salary.presentation.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.rubicon.salary.BuildConfig;
import ru.rubicon.salary.R;
import ru.rubicon.salary.domain.entity.Employee;
import ru.rubicon.salary.domain.entity.SalaryTableRecord;


public class EditRecordDialog extends DialogFragment {

    public static final String COEF = "coef";
    public static final String DAYS = "days";
    public static final String NAME = "name";
    private EditRecordDialogListener listener;
    private int id;

    public static final String ID = "id";

    @BindView(R.id.tvName) TextView tvName;
    @BindView(R.id.etCoef) EditText etCoef;
    @BindView(R.id.etDays) EditText etDays;
    @BindView(R.id.btnSave) Button btnSave;
    private float days;
    private float coef;
    private String name;

    @Override public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof EditRecordDialog.EditRecordDialogListener){
            this.listener = (EditRecordDialogListener)context;
        }
        if (getParentFragment() instanceof EditRecordDialog.EditRecordDialogListener){
            this.listener = (EditRecordDialogListener)getParentFragment();
        }

        if (listener == null){
            throw new IllegalArgumentException("context must implement DeleteDialogListener");
        }
    }

    @Override public Dialog onCreateDialog(Bundle savedInstanceState) {
        readRecord();
        View form = createUIView();
        return buildDialog(form);
    }

    private Dialog buildDialog(View form){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(form);
        Dialog dialog = builder.create();
        return dialog;
    }

    private void readRecord(){
        Bundle bundle = getArguments();
        id = bundle.getInt(ID, -1);
        if(id == -1){
            throw new IllegalArgumentException("id must be defined");
        }
        name = bundle.getString(NAME);
        coef = bundle.getFloat(COEF, 0);
        days = bundle.getFloat(DAYS, 0);
    }

    private View createUIView(){
        View form = getActivity().getLayoutInflater()
                .inflate(R.layout.employee_calc_dialog, null, true);
        ButterKnife.bind(this, form);

        tvName.setText(name);
        etCoef.setText(String.valueOf(coef));
        etDays.setText(String.valueOf(days));

        btnSave.setOnClickListener(v -> {
            float readCoef = readFloatFrom(etCoef);
            float readDays = readFloatFrom(etDays);
            if(readCoef != -1 && readDays != -1){
                listener.save(id, readCoef, readDays);
                dismiss();
            }
        });

        return form;
    }

    private float readFloatFrom(EditText editText) {
        try {
            return Float.parseFloat(editText.getText().toString());
        }catch (NumberFormatException e){
            showError(editText);
            return -1f;
        }
    }

    private void showError(EditText editText) {
        editText.requestFocus();
        editText.setSelection(editText.length());
        editText.setError(getResources().getText(R.string.error_message));
    }

    public static DialogFragment getInstance(SalaryTableRecord record){
        EditRecordDialog fragment = new EditRecordDialog();
        Bundle bundle = new Bundle();
        bundle.putInt(ID, record.id());
        bundle.putString(NAME, record.employee());
        bundle.putFloat(COEF, record.coefficient());
        bundle.putFloat(DAYS, record.amountsOfDays());
        fragment.setArguments(bundle);
        return fragment;
    }

    public interface EditRecordDialogListener{
        void save(int id, float coef, float days);
    }
}
