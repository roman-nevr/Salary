package ru.rubicon.salary.presentation.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import ru.rubicon.salary.R;
import ru.rubicon.salary.presentation.DetailsRouter;
import ru.rubicon.salary.presentation.fragments.EmployeeDetailsFragment;
import ru.rubicon.salary.presentation.fragments.SalaryDetailsFragment;

public class DetailsActivity extends AppCompatActivity implements DetailsRouter{

    public static final String TYPE = "type";
    public static final String ID = "id";

    private int type;
    private int id;

    public static final String TAG = "tag";

    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUi();
        readType();
        readId();
        fragmentManager = getSupportFragmentManager();
    }

    @Override protected void onStart() {
        super.onStart();
        showFragment(type);
    }

    private void initUi() {
        setContentView(R.layout.details_layout);
    }

    private void showFragment(int type){
        beginTransaction();
        showEnterFragment(type);
        commitTransaction();
    }
    private void beginTransaction(){
        transaction = fragmentManager.beginTransaction();
    }

    private void commitTransaction(){
        transaction.commit();
    }


    private void showEnterFragment(int type) {
        transaction.replace(R.id.container, getFragment(type), TAG + type);
    }

    @NonNull private Fragment getFragment(int type) {
        switch (type){
            case R.id.add_employee:
                return EmployeeDetailsFragment.getInstance();
            case R.id.add_salary:
                return SalaryDetailsFragment.getInstance();
            case R.id.edit_employee:
                return EmployeeDetailsFragment.getInstance(id);
            case R.id.edit_salary:
                return SalaryDetailsFragment.getInstance(id);
            default:
                throw new IllegalArgumentException();
        }
    }

    public static void startActivity(Context context, int type) {
        startActivity(context, type, -1);
    }

    public static void startActivity(Context context, int type, int id) {
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra(TYPE, type);
        intent.putExtra(ID, id);
        context.startActivity(intent);
    }

    private void readType(){
        type = getIntent().getIntExtra(TYPE, -1);
    }

    private void readId(){
        id = getIntent().getIntExtra(ID, -1);
    }

    @Override public void close() {
        finish();
    }
}
