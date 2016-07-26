package ru.rubicon.salary.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

import ru.rubicon.salary.DAO.SalaryDataSource;
import ru.rubicon.salary.adapter.MainActivityAdapter;
import ru.rubicon.salary.entity.Employee;

import static ru.rubicon.salary.utils.utils.log;
import static ru.rubicon.salary.utils.utils.nop;

/**
 * Created by roma on 17.06.2016.
 */
public class SalaryListFragment extends ListFragment {

    OnItemClickObserver mCallback;
    private SalaryDataSource dataSource;

    // Container Activity must implement this interface
    public interface OnItemClickObserver {
        public void onSalaryItemClick(int position);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
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

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        mCallback.onSalaryItemClick(position);
    }

}
