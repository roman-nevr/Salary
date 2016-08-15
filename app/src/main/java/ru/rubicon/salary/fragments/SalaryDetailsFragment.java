package ru.rubicon.salary.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import ru.rubicon.salary.DAO.SalaryDataSource;
import ru.rubicon.salary.R;
import ru.rubicon.salary.adapter.EmployeeSalariesListAdapter;
import ru.rubicon.salary.entity.Salary;

import static ru.rubicon.salary.utils.utils.log;

/**
 * Created by roma on 22.06.2016.
 */
public class SalaryDetailsFragment extends Fragment {

    SalaryDataSource dataSource;

    OnItemClickObserver mCallback;
    public interface OnItemClickObserver {
        public void onSalaryDetailsItemClick(int position);
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

    ListView lvEmployeesSalaryList;
    ArrayList<Salary> salaries;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        //salaries = new ArrayList<>(Arrays.asList(new Salary(140000, new Date()), new Salary(160000, new Date())));
        dataSource = new SalaryDataSource(getContext());
        dataSource.open();
        salaries = dataSource.readAllSalaries();
        dataSource.close();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Intent launchingIntent = getActivity().getIntent();
        //String catName = launchingIntent.getStringExtra("catname");
        View viewer = inflater.inflate(R.layout.salary_details, container, false);
        lvEmployeesSalaryList = (ListView) viewer.findViewById(R.id.lvEmployeesSalaryList);
        lvEmployeesSalaryList.setAdapter(new EmployeeSalariesListAdapter(getContext(), salaries));
        //mCatDescriptionTextView = (TextView) viewer.findViewById(R.id.textViewDescription);

        log("details");
        return viewer;
    }
}
