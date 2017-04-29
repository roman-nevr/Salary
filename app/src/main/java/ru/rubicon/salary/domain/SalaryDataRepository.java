package ru.rubicon.salary.domain;

import java.util.List;

import ru.rubicon.salary.domain.entity.Salary;

public interface SalaryDataRepository {
    void saveSalary(Salary salary);

    Salary getSalary(int id);

    List<Salary> getAllSalaries();
}
