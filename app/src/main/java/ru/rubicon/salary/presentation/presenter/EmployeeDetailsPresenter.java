package ru.rubicon.salary.presentation.presenter;

import javax.inject.Inject;

import ru.rubicon.salary.domain.EmployeeRepository;
import ru.rubicon.salary.domain.entity.Employee;
import ru.rubicon.salary.presentation.fragments.EmployeeDetailsFragment;

public class EmployeeDetailsPresenter {

    @Inject EmployeeRepository repository;

    private int id;
    private Employee employee;
    private EmployeeDetailsFragment view;

    @Inject
    public EmployeeDetailsPresenter() {}

    public void start(){
        if(id != -1){
            employee = repository.getEmployee(id);
        }else {
            employee = Employee.DEFAULT;
        }
    }

    public void onSaveClick(){

    }

    public void setView(EmployeeDetailsFragment view) {
        this.view = view;
    }

    public void setId(int id) {
        this.id = id;
    }
}
