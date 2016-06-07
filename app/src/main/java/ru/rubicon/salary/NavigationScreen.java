package ru.rubicon.salary;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by roma on 07.06.2016.
 */
public class NavigationScreen extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    }
}
