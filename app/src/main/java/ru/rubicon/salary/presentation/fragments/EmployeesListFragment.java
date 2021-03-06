package ru.rubicon.salary.presentation.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.rubicon.salary.R;
import ru.rubicon.salary.domain.entity.Employee;
import ru.rubicon.salary.presentation.App;
import ru.rubicon.salary.presentation.NavigationRouter;
import ru.rubicon.salary.presentation.adapter.EmployeeListAdapter;
import ru.rubicon.salary.presentation.presenter.EmployeeListPresenter;

/**
 * Created by roma on 16.06.2016.
 */
public class EmployeesListFragment extends Fragment {

    private RecyclerView.Adapter adapter;

    @Inject EmployeeListPresenter presenter;

    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.fab) FloatingActionButton floatingActionButton;

    @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.salary_list, container, false);
        ButterKnife.bind(this, view);

        initUi();

        initDi();
        return view;
    }

    private void initUi() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(layoutManager);

        floatingActionButton.setOnClickListener(v -> presenter.onFabClick());
    }

    @Override public void onStart() {
        super.onStart();
        presenter.start();
    }

    private void initDi() {
        App.getInstance().getMainComponent().plusEmployeeComponent().inject(this);
        presenter.setView(this);
        presenter.setRouter((NavigationRouter)getActivity());
    }

    public void showEmployees(List<Employee> employees){
        adapter = new EmployeeListAdapter(employees, presenter);
        recyclerView.setAdapter(adapter);
    }
}
