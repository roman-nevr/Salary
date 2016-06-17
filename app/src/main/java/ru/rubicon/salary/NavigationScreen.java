package ru.rubicon.salary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;


import ru.rubicon.salary.fragments.EmployeesListFragment;
import ru.rubicon.salary.fragments.SalaryListFragment;

/**
 * Created by roma on 07.06.2016.
 */
public class NavigationScreen extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private NavigationView mDrawerView;
    final static String TAG_1 = "FRAGMENT_1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer);

        mDrawerView = (NavigationView) findViewById(R.id.nav_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerView.setNavigationItemSelectedListener(new SalaryOnNavigationItemSelectedListener());
        Fragment fragment = new EmployeesListFragment();
        showFragmentOnStart(fragment);

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
                    addEmployee
                }
            }
            mDrawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }
    }

    @Override
    public void onBackPressed() {
       // DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /*private void selectItem(int position) {
        // update the main content by replacing fragments
        Fragment fragment = new PlanetFragment();
        Bundle args = new Bundle();
        args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
        fragment.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mPlanetTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }*/

    private void showSalaryFragment() {
        //Fragment fragment = new SalaryListFragment();
        showNewFragment(new SalaryListFragment());
        /*getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment).commit();*/
    }

    private void showEmployeesFragment() {

        showNewFragment(new EmployeesListFragment());
        /*Fragment fragmentOld = getSupportFragmentManager().findFragmentById(R.id.fragment);

        Fragment fragmentNew = new EmployeesListFragment();
        getSupportFragmentManager().beginTransaction().hide(fragmentOld).add(R.id.fragment, fragmentNew).commit();*/
    }

    private void showNewFragment(Fragment fragment){
        Fragment fragmentOld = getSupportFragmentManager().findFragmentById(R.id.container);
        getSupportFragmentManager().beginTransaction()./*hide(fragmentOld).add*/replace(R.id.container, fragment).commit();
    }

    private void showFragmentOnStart(Fragment fragment){
        getSupportFragmentManager().beginTransaction().add(R.id.container, fragment, TAG_1).commit();
    }
}
