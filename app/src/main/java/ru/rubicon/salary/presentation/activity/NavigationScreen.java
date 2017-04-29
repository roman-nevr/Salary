package ru.rubicon.salary.presentation.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.rubicon.salary.R;
import ru.rubicon.salary.presentation.Router;
import ru.rubicon.salary.presentation.adapter.NavigationPagerAdapter;

public class NavigationScreen extends AppCompatActivity implements Router {

    @BindView(R.id.tabs) TabLayout tabLayout;
    @BindView(R.id.view_pager) ViewPager viewPager;

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
    }

    public static void startActivity(Context context){
        Intent intent = new Intent(context, NavigationScreen.class);
        context.startActivity(intent);
    }

    @Override public void addSalary() {

    }

    @Override public void addEmployee() {

    }

    @Override public void editSalary(int id) {

    }

    @Override public void editEmployee(int id) {

    }
}
