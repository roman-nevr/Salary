package ru.rubicon.salary.presentation.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.rubicon.salary.R;
import ru.rubicon.salary.presentation.NavigationRouter;
import ru.rubicon.salary.presentation.adapter.NavigationPagerAdapter;

public class NavigationScreen extends AppCompatActivity implements NavigationRouter {

    @BindView(R.id.tabs) TabLayout tabLayout;
    @BindView(R.id.view_pager) ViewPager viewPager;
    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabs_layout);
        ButterKnife.bind(this);

        initUi();
    }

    private void initUi() {
        viewPager.setAdapter(new NavigationPagerAdapter(getSupportFragmentManager(), getApplicationContext()));
        tabLayout.setupWithViewPager(viewPager);
        toolbar.setTitle(R.string.app_name);
    }

    public static void startActivity(Context context){
        Intent intent = new Intent(context, NavigationScreen.class);
        context.startActivity(intent);
    }

    @Override public void addSalary() {
        DetailsActivity.startActivity(this, R.id.add_salary);
    }

    @Override public void addEmployee() {
        DetailsActivity.startActivity(this, R.id.add_employee);
    }

    @Override public void editSalary(int id) {
        DetailsActivity.startActivity(this, R.id.edit_salary, id);
    }

    @Override public void editEmployee(int id) {
        DetailsActivity.startActivity(this, R.id.edit_employee, id);
    }
}
