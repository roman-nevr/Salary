package ru.rubicon.salary;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;


import java.util.List;

import ru.rubicon.salary.entity.Employee;
import ru.rubicon.salary.fragments.EmployeesListFragment;
import ru.rubicon.salary.fragments.SalaryListFragment;

/**
 * Created by roma on 07.06.2016.
 */
public class NavigationScreen extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private NavigationView mDrawerView;
    private final static String TAG_1 = "employee_list";
    private final static String TAG_2 = "salary_list";
    private static final String FRAGMENT = "fragment";
    private String currentTag;
    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer);

        mDrawerView = (NavigationView) findViewById(R.id.nav_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerView.setNavigationItemSelectedListener(new SalaryOnNavigationItemSelectedListener());

        fragmentManager = getSupportFragmentManager();

        showFragmentOnStart(savedInstanceState);
    }


    public static void startActivity(Context context){
        Intent intent = new Intent(context, NavigationScreen.class);
        context.startActivity(intent);
    }

    private class SalaryOnNavigationItemSelectedListener implements NavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            int itemId = item.getItemId();
            switch (itemId){
                case R.id.nav_employee:{
                    showEmployeesFragment();
                    break;
                }
                case R.id.nav_salary:{
                    showSalaryFragment();
                    break;
                }
                case R.id.nav_send:{
                    addEmployee(new Employee("Еще один", 10000));
                }
            }
            mDrawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }
    }



    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    private void showSalaryFragment() {
        showNewFragment(new SalaryListFragment(), TAG_2);
    }

    private void showEmployeesFragment() {
        showNewFragment(new EmployeesListFragment(), TAG_1);
    }

    private void showNewFragment(Fragment fragment, String tag){
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment, tag).commit();
        currentTag = tag;
    }

    private void showFragmentOnStart(Bundle data){
        if (data == null){
            getSupportFragmentManager().beginTransaction().add(R.id.container, getFragmentByTag(TAG_1), TAG_1).commit();
        } else{
            String tag = data.getString(FRAGMENT);
        }
    }

    private void addEmployee(Employee employee){
        EmployeesListFragment fragment = (EmployeesListFragment) getSupportFragmentManager().findFragmentByTag(TAG_1);
        if ((fragment != null) && (fragment.isVisible())){
            fragment.addEmployee(employee);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(FRAGMENT, currentTag);
        //fragmentManager.putFragment(outState, TAG_1, fragmentManager.findFragmentByTag());
        super.onSaveInstanceState(outState);
    }

    private Fragment getFragmentByTag(String tag){
        List<Fragment> fragmentList = fragmentManager.getFragments();
        for (Fragment fragment : fragmentList) {
            if (fragment.getTag().equals(tag)){
                return fragment;
            }
        }
        if(tag.equals(TAG_1)){
            return new EmployeesListFragment();
        }
        if(tag.equals(TAG_2)) {
            return new SalaryListFragment();
        }
        return null;
    }

}
