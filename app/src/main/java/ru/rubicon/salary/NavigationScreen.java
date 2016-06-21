package ru.rubicon.salary;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import java.util.List;
import ru.rubicon.salary.entity.Employee;
import ru.rubicon.salary.fragments.EmployeeDetailsFragment;
import ru.rubicon.salary.fragments.EmployeesListFragment;
import ru.rubicon.salary.fragments.SalaryListFragment;
import ru.rubicon.salary.utils.utils;

/**
 * Created by roma on 07.06.2016.
 */
public class NavigationScreen extends AppCompatActivity implements EmployeesListFragment.OnItemClickObserver,
                        SalaryListFragment.OnItemClickObserver,
                        EmployeeDetailsFragment.OnItemClickObserver {

    private DrawerLayout mDrawerLayout;
    private NavigationView mDrawerView;
    private FloatingActionButton actionButton;
    private CoordinatorLayout clMainList;
    private final static String TAG_1 = "employees_list";
    private final static String TAG_2 = "salary_list";
    private final static String TAG_3 = "employee_details";
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
        actionButton = (FloatingActionButton) findViewById(R.id.fab);
        clMainList = (CoordinatorLayout) findViewById(R.id.clMainList);

        fragmentManager = getSupportFragmentManager();

        showFragmentOnStart(savedInstanceState);

        actionButton.setOnClickListener(new ActionButtonOnClickListener());
    }

    public static void startActivity(Context context){
        Intent intent = new Intent(context, NavigationScreen.class);
        context.startActivity(intent);
    }

    @Override
    public void onEmployeeItemClick(int position) {
        //utils.snackBarShort(clMainList, "Employee id "+position);
        showEmployeesDetailsFragment();

    }

    @Override
    public void onSalaryItemClick(int position) {
        utils.snackBarShort(clMainList, "Salary id "+position);
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

    private class ActionButtonOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            utils.snackBarShort(view, "FAB clicked");
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
        showNewFragment(getFragmentByTag(TAG_2), TAG_2);
        actionButton.show();
    }

    private void showEmployeesFragment() {
        showNewFragment(getFragmentByTag(TAG_1), TAG_1);
        actionButton.show();
    }

    private void showEmployeesDetailsFragment() {
        showNewFragmentWithBackStack(getFragmentByTag(TAG_3), TAG_3);
        actionButton.hide();
    }

    private void showNewFragment(Fragment fragment, String tag){
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment, tag).commit();
        currentTag = tag;
    }

    private void showNewFragmentWithBackStack(Fragment fragment, String tag){
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment, tag).addToBackStack(FRAGMENT).commit();
        currentTag = tag;
    }

    private void showFragmentOnStart(Bundle data){
        actionButton.show();
        if (data == null){
            getSupportFragmentManager().beginTransaction().add(R.id.container, getFragmentByTag(TAG_1), TAG_1).commit();
            currentTag = TAG_1;
        } else{
            String tag = data.getString(FRAGMENT);
            showNewFragment(getFragmentByTag(tag),tag);
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
        outState.putString(FRAGMENT, fragmentManager.findFragmentById(R.id.container).getTag());
        //fragmentManager.putFragment(outState, TAG_1, fragmentManager.findFragmentByTag());
        super.onSaveInstanceState(outState);
        //utils.toastShort(getApplicationContext(), ""+fragmentManager.findFragmentById(R.id.container).getClass());
    }

    private Fragment getFragmentByTag(String tag){
        List<Fragment> fragmentList = fragmentManager.getFragments();
        if(fragmentList != null){
            for (Fragment fragment : fragmentList) {
                if (fragment.getTag().equals(tag)){
                    return fragment;
                }
            }
        }
        if(tag.equals(TAG_1)){
            return new EmployeesListFragment();
        }
        if(tag.equals(TAG_2)) {
            return new SalaryListFragment();
        }
        if(tag.equals(TAG_3)) {
            return new EmployeeDetailsFragment();
        }
        return null;
    }


}
