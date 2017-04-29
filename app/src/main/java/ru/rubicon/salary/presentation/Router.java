package ru.rubicon.salary.presentation;

public interface Router {
    void addSalary();

    void addEmployee();

    void editSalary(int id);

    void editEmployee(int id);
}
