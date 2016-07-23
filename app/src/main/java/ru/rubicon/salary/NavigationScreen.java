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
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import java.util.List;
import ru.rubicon.salary.entity.Employee;
import ru.rubicon.salary.fragments.CalcTemporalFragment;
import ru.rubicon.salary.fragments.CalcTemporalFragmentAlt;
import ru.rubicon.salary.fragments.EmployeeDetailsDialogFragment;
import ru.rubicon.salary.fragments.EmployeesListFragment;
import ru.rubicon.salary.fragments.SalaryDetailsFragment;
import ru.rubicon.salary.fragments.SalaryListFragment;
import ru.rubicon.salary.utils.utils;

/**
 * Created by roma on 07.06.2016.
 */
public class NavigationScreen extends AppCompatActivity implements
                        SalaryListFragment.OnItemClickObserver,

                        SalaryDetailsFragment.OnItemClickObserver{

    private DrawerLayout mDrawerLayout;
    private NavigationView mDrawerView;
    private FloatingActionButton actionButton;
    private CoordinatorLayout clMainList;
    private Toolbar toolbar;
    private final static String TAG_1 = "List of employees";
    private final static String TAG_2 = "List of salaries";
    private final static String TAG_3 = "Employee details";
    private final static String TAG_4 = "Salary details";
    private final static String TAG_5 = "Temporal calculation";
    private final static String TAG_6 = "Temporal calculation alt";
    private static final String FRAGMENT = "fragment";
    private String currentTag;
    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.navigation_drawer);
        setContentView(R.layout.main);

        mDrawerView = (NavigationView) findViewById(R.id.nav_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerView.setNavigationItemSelectedListener(new SalaryOnNavigationItemSelectedListener());
        actionButton = (FloatingActionButton) findViewById(R.id.fab);
        clMainList = (CoordinatorLayout) findViewById(R.id.clMainList);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        fragmentManager = getSupportFragmentManager();
        fragmentManager.addOnBackStackChangedListener(new BackStackChangedListener());

        showFragmentOnStart(savedInstanceState);

        actionButton.setOnClickListener(new ActionButtonOnClickListener());

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    public static void startActivity(Context context){
        Intent intent = new Intent(context, NavigationScreen.class);
        context.startActivity(intent);
    }


    @Override
    public void onSalaryItemClick(int position) {
        utils.snackBarShort(clMainList, "Salary id "+position);
        showSalaryDetailsFragment();
    }

    @Override
    public void onSalaryDetailsItemClick(int position) {

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
                    break;
                }
                case R.id.nav_temp_calc:{
                    showTempCalcFragment();
                    break;
                }
                case R.id.nav_temp_calc_alt:{
                    showTempCalcFragmentAlt();
                    break;
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

    private class BackStackChangedListener implements FragmentManager.OnBackStackChangedListener{
        @Override
        public void onBackStackChanged() {
            toolbar.setTitle(fragmentManager.findFragmentById(R.id.container).getTag());
            int backStackEntryCount = fragmentManager.getBackStackEntryCount();
            if(backStackEntryCount > 0){
                actionButton.hide();
                //getActionBar().setDisplayHomeAsUpEnabled(true);
            }else{
                //getActionBar().setDisplayHomeAsUpEnabled(false);
                actionButton.show();
            }
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
    }

    private void showEmployeesFragment() {
        showNewFragment(getFragmentByTag(TAG_1), TAG_1);
    }

    private void showEmployeesDetailsFragment() {
        showNewFragmentWithBackStack(getFragmentByTag(TAG_3), TAG_3);
    }

    private void showSalaryDetailsFragment() {
        showNewFragmentWithBackStack(getFragmentByTag(TAG_4), TAG_4);
    }

    private void showTempCalcFragment() {
        showNewFragmentWithBackStack(getFragmentByTag(TAG_5), TAG_5);
    }

    private void showTempCalcFragmentAlt() {
        showNewFragmentWithBackStack(getFragmentByTag(TAG_6), TAG_6);
    }

    private void showNewFragment(Fragment fragment, String tag){
        while (fragmentManager.popBackStackImmediate()){
        }
        fragmentManager.beginTransaction().replace(R.id.container, fragment, tag).commit();
        currentTag = tag;
        toolbar.setTitle(tag);

    }

    private void showNewFragmentWithBackStack(Fragment fragment, String tag){
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment, tag).addToBackStack(FRAGMENT).commit();
        getSupportFragmentManager().executePendingTransactions();
        currentTag = tag;
        toolbar.setTitle(tag);
    }

    private void showFragmentOnStart(Bundle data){
        if (data == null){
            getSupportFragmentManager().beginTransaction().add(R.id.container, getFragmentByTag(TAG_1), TAG_1).commit();
            currentTag = TAG_1;
            toolbar.setTitle(TAG_1);
        } else{
            String tag = data.getString(FRAGMENT);
            showNewFragment(getFragmentByTag(tag), tag);
            toolbar.setTitle(tag);
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
                if ((fragment != null) && (fragment.getTag().equals(tag))){
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
            return new EmployeeDetailsDialogFragment();
        }
        if(tag.equals(TAG_4)) {
            return new SalaryDetailsFragment();
        }
        if(tag.equals(TAG_5)) {
            return new CalcTemporalFragment();
        }
        if(tag.equals(TAG_6)){
            return new CalcTemporalFragmentAlt();
        }
        return null;
    }




}
