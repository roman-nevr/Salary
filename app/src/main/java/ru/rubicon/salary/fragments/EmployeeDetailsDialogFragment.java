package ru.rubicon.salary.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.rubicon.salary.R;

import static ru.rubicon.salary.utils.utils.log;

/**
 * Created by roma on 21.06.2016.
 */
public class EmployeeDetailsDialogFragment extends DialogFragment {

    View form;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View viewer = inflater.inflate(R.layout.employee_details, container, false);


        log("details");
        return viewer;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        form = getActivity().getLayoutInflater().inflate(R.layout.employee_details, null, true);
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setView(form);
        return builder.create();
    }
}
