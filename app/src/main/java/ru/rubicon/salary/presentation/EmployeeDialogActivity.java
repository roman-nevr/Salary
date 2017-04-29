package ru.rubicon.salary.presentation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import ru.rubicon.salary.R;


public class EmployeeDialogActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_calc_dialog);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }
}
