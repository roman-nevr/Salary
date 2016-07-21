package ru.rubicon.salary;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class MainScreen extends Activity {
    /**
     * Called when the activity is first created.
     */

    ListView lvNames;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_dialog);

        //lvNames = (ListView) findViewById(R.id.lvNames);

        NavigationScreen.startActivity(this);
    }
}
