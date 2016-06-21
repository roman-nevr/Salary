package ru.rubicon.salary.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Arrays;
import ru.rubicon.salary.adapter.MainActivityAdapter;
import ru.rubicon.salary.entity.Employee;
import static ru.rubicon.salary.utils.utils.log;

/**
 * Created by roma on 16.06.2016.
 */
public class EmployeesListFragment extends ListFragment {

    OnItemClickObserver mCallback;

    // Container Activity must implement this interface
    public interface OnItemClickObserver {
        public void onEmployeeItemClick(int position);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnItemClickObserver) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnItemClickObserver");
        }
    }

    ArrayList<Employee> employees;
    MainActivityAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         employees = new ArrayList<Employee>(Arrays.asList(new Employee("Roman", 16000), new Employee("Shurik", -16000)));

        adapter = new MainActivityAdapter(getContext(), employees);
        setListAdapter(adapter);
        setRetainInstance(true);
        log("list_fragment");
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        mCallback.onEmployeeItemClick(position);
    }

    public void addEmployee(Employee employee){
        employees.add(employee);
        adapter.notifyDataSetChanged();
    }

}
