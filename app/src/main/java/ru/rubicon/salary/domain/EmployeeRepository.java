package ru.rubicon.salary.domain;

import java.util.List;

import ru.rubicon.salary.domain.entity.Employee;

public interface EmployeeRepository {
    void saveEmployee(Employee employee);

    Employee getEmployee(int id);

    List<Employee> getAllEmployees();
}
