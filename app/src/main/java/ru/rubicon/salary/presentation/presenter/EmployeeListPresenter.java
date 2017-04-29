package ru.rubicon.salary.presentation.presenter;

import java.util.List;

import javax.inject.Inject;

import ru.rubicon.salary.domain.EmployeeRepository;
import ru.rubicon.salary.domain.entity.Employee;
import ru.rubicon.salary.presentation.Router;
import ru.rubicon.salary.presentation.fragments.EmployeesListFragment;

public class EmployeeListPresenter {

    private EmployeesListFragment view;
    private Router router;

    @Inject EmployeeRepository employeeRepository;

    @Inject
    public EmployeeListPresenter() {}

    public void setView(EmployeesListFragment view) {
        this.view = view;
    }

    public void setRouter(Router router) {
        this.router = router;
    }

    public void start() {
        List<Employee> employees = employeeRepository.getAllEmployees();
        view.showEmployees(employees);
    }
}
