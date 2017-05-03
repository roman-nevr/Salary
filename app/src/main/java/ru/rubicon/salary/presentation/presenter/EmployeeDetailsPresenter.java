package ru.rubicon.salary.presentation.presenter;

import javax.inject.Inject;

import ru.rubicon.salary.domain.EmployeeRepository;
import ru.rubicon.salary.domain.entity.Employee;
import ru.rubicon.salary.presentation.DetailsRouter;
import ru.rubicon.salary.presentation.fragments.EmployeeDetailsFragment;

public class EmployeeDetailsPresenter {

    @Inject EmployeeRepository repository;

    private int id;
    private Employee employee;
    private EmployeeDetailsFragment view;
    private DetailsRouter router;

    @Inject
    public EmployeeDetailsPresenter() {
    }

    public void start() {
        if (id != -1) {
            employee = repository.getEmployee(id);
        } else {
            employee = Employee.DEFAULT;
        }
        view.showEmployee(employee);
    }

    public void onSaveClick(String name, String rawCoef, boolean isActive, String comment, boolean hasFixed, String rawFixedSalary) {
        // TODO: 03.05.17 make fixed salary
        try {

            float coef = parseCoef(rawCoef);
            int fixedSalary = parseFixedSalary(rawFixedSalary);
            employee = employee.toBuilder()
                    .name(name)
                    .coefficient(coef)
                    .isActive(isActive)
                    .hasFixedSalary(hasFixed)
                    .dailySalary(fixedSalary)
                    .comment(comment)
                    .build();
            repository.saveEmployee(employee);
            router.close();
        } catch (IllegalArgumentException e) {
            view.showCoefError();
        }
    }

    private float parseCoef(String coef) throws IllegalArgumentException{
        try {
            return Float.valueOf(coef);
        }catch (NumberFormatException e){
            view.showCoefError();
            throw new IllegalArgumentException(e);
        }
    }

    private int parseFixedSalary(String coef) throws IllegalArgumentException{
        try {
            return Integer.valueOf(coef);
        }catch (NumberFormatException e){
            view.showFixedError();
            throw new IllegalArgumentException(e);
        }
    }

    public void setView(EmployeeDetailsFragment view) {
        this.view = view;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRouter(DetailsRouter router) {
        this.router = router;
    }
}
