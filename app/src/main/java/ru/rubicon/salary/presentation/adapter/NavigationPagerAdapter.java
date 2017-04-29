package ru.rubicon.salary.presentation.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import ru.rubicon.salary.R;
import ru.rubicon.salary.presentation.fragments.EmployeesListFragment;
import ru.rubicon.salary.presentation.fragments.SalaryListFragment;

public class NavigationPagerAdapter extends FragmentStatePagerAdapter {

    private final Context context;

    public NavigationPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new SalaryListFragment();
            case 1:
                return new EmployeesListFragment();
        }
        return null;
    }

    @Override public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return context.getResources().getString(R.string.salary_tab_label);
            case 1:
                return context.getResources().getString(R.string.employees_tab_label);
        }
        return "";
    }

    @Override public int getCount() {
        return 2;
    }
}
