package ru.rubicon.salary.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.widget.ListAdapter;
import java.util.ArrayList;
import java.util.Arrays;
import ru.rubicon.salary.adapter.MainActivityAdapter;
import ru.rubicon.salary.entity.Employee;

import static ru.rubicon.salary.utils.utils.log;

/**
 * Created by roma on 17.06.2016.
 */
public class SalaryListFragment extends ListFragment {

    OnItemClickObserver mCallback;

    // Container Activity must implement this interface
    public interface OnItemClickObserver {
        public void onSalaryItemClick(int position);
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList<Employee> employees = new ArrayList<Employee>(Arrays.asList(new Employee("Salary", 16000), new Employee("Salary2", 16000)));

        ListAdapter adapter = new MainActivityAdapter(getContext(), employees);
        setListAdapter(adapter);
        setRetainInstance(true);

        log("Salary_list_fragment");

        /*FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.*/
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        log("SalaryListFragment " + this.isVisible());
    }
}
