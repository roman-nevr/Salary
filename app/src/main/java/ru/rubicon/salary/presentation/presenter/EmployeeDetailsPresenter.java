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

    public void onSaveClick(String name, String stringCoef, boolean isActive, String comment) {
        // TODO: 03.05.17 make fixed salary
        try {
            float coef = Float.valueOf(stringCoef);
            employee = employee.toBuilder()
                    .name(name)
                    .coefficient(coef)
                    .isActive(isActive)
                    .comment(comment)
                    .build();
            repository.saveEmployee(employee);
            router.close();
        } catch (NumberFormatException e) {
            view.showCoefError();
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
