package ru.rubicon.salary.di;

import javax.inject.Singleton;

import dagger.Component;
import ru.rubicon.salary.data.DAO.EmployeeDataSource;
import ru.rubicon.salary.data.DAO.SalaryDataSource;

@Singleton
@Component(modules = MainModule.class)
public interface MainComponent {
//    EmployeeDataSource provideEmployeeDataSource();

//    SalaryDataSource provideSalaryDataSource();

    SalaryComponent plusSalaryComponent();

    EmployeeComponent plusEmployeeComponent();
}
