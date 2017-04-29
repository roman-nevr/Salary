package ru.rubicon.salary.presentation.presenter;

import java.util.List;

import javax.inject.Inject;

import ru.rubicon.salary.data.DAO.SalaryDataSource;
import ru.rubicon.salary.domain.SalaryDataRepository;
import ru.rubicon.salary.domain.entity.Salary;
import ru.rubicon.salary.presentation.Router;
import ru.rubicon.salary.presentation.fragments.SalaryListFragment;

public class SalaryListPresenter {
    @Inject SalaryDataRepository repository;
    private Router router;
    private SalaryListFragment view;

    @Inject
    public SalaryListPresenter() {}

    public void start(){
        List<Salary> salaries = repository.getAllSalaries();
        view.showSalaries(salaries);
    }

    public void stop(){

    }

    public void setRouter(Router router) {
        this.router = router;
    }

    public void setView(SalaryListFragment view) {
        this.view = view;
    }
}
