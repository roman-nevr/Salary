package ru.rubicon.salary.presentation.presenter;

import android.view.View;

import java.util.List;

import javax.inject.Inject;

import ru.rubicon.salary.domain.EmployeeRepository;
import ru.rubicon.salary.domain.entity.Employee;
import ru.rubicon.salary.presentation.OnItemClickListener;
import ru.rubicon.salary.presentation.NavigationRouter;
import ru.rubicon.salary.presentation.fragments.EmployeesListFragment;

public class EmployeeListPresenter implements OnItemClickListener {

    private EmployeesListFragment view;
    private NavigationRouter navigationRouter;

    @Inject EmployeeRepository employeeRepository;

    @Inject
    public EmployeeListPresenter() {}

    public void setView(EmployeesListFragment view) {
        this.view = view;
    }

    public void setRouter(NavigationRouter navigationRouter) {
        this.navigationRouter = navigationRouter;
    }

    public void start() {
        List<Employee> employees = employeeRepository.getAllEmployees();
        view.showEmployees(employees);
    }

    @Override public void onItemClick(int id) {
        navigationRouter.editEmployee(id);
    }

    public void onFabClick() {
        navigationRouter.addEmployee();
    }
}
