package ru.rubicon.salary.presentation;

public interface NavigationRouter {
    void addSalary();

    void addEmployee();

    void editSalary(int id);

    void editEmployee(int id);
}
