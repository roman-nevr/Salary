package ru.rubicon.salary.presentation.presenter;

import java.util.List;

import javax.inject.Inject;

import ru.rubicon.salary.domain.SalaryDataRepository;
import ru.rubicon.salary.domain.entity.Salary;
import ru.rubicon.salary.presentation.OnItemClickListener;
import ru.rubicon.salary.presentation.NavigationRouter;
import ru.rubicon.salary.presentation.fragments.SalaryListFragment;

public class SalaryListPresenter implements OnItemClickListener{
    @Inject SalaryDataRepository repository;
    private NavigationRouter navigationRouter;
    private SalaryListFragment view;

    @Inject
    public SalaryListPresenter() {}

    public void start(){
        List<Salary> salaries = repository.getAllSalaries();
        view.showSalaries(salaries);
    }

    public void stop(){

    }

    public void setRouter(NavigationRouter navigationRouter) {
        this.navigationRouter = navigationRouter;
    }

    public void setView(SalaryListFragment view) {
        this.view = view;
    }

    public void onFabClick() {
        navigationRouter.addSalary();
    }

    @Override public void onItemClick(int id) {
        navigationRouter.editSalary(id);
    }
}
