package ru.rubicon.salary;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by admin on 21.07.2016.
 */
public class EmployeeDialogActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_calc_dialog);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }
}
