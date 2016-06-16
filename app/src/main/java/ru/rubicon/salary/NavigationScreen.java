package ru.rubicon.salary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by roma on 07.06.2016.
 */
public class NavigationScreen extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private NavigationView mDrawerView;
    private String[] mMenuPoints;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer);

        mDrawerView = (NavigationView) findViewById(R.id.nav_view);

        mDrawerView.setNavigationItemSelectedListener(new SalaryOnNavigationItemSelectedListener());
    }


    public static void startActivity(Context context){
        Intent intent = new Intent(context, NavigationScreen.class);
        context.startActivity(intent);
    }

    private class SalaryOnNavigationItemSelectedListener implements NavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            int id = item.getItemId();
            int id1 = R.id.nav_share;
            int id_emp = R.id.nav_employee;
            return false;
        }
    }
}
