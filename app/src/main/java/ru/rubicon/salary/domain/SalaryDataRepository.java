package ru.rubicon.salary.domain;

import java.util.List;

import ru.rubicon.salary.domain.entity.Salary;
import ru.rubicon.salary.domain.entity.SalaryTableRecord;

public interface SalaryDataRepository {

    void saveSalary(Salary salary);

    Salary getSalary(int id);

    List<Salary> getAllSalaries();

    void saveSalaryTableRecord(SalaryTableRecord record);

    SalaryTableRecord getSalaryTableRecord(int id);
}
