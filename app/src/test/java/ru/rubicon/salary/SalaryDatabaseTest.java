package ru.rubicon.salary;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import ru.rubicon.salary.data.sqlite.DatabaseHelper;
import ru.rubicon.salary.data.sqlite.EmployeeRepositoryImpl;
import ru.rubicon.salary.data.sqlite.SalaryDataRepositoryImpl;
import ru.rubicon.salary.domain.Calculator;
import ru.rubicon.salary.domain.EmployeeRepository;
import ru.rubicon.salary.domain.SalaryDataRepository;
import ru.rubicon.salary.domain.entity.Employee;
import ru.rubicon.salary.domain.entity.Salary;
import ru.rubicon.salary.domain.entity.SalaryTableRecord;

@RunWith(RobolectricTestRunner.class)
@Config(manifest= Config.NONE)
public class SalaryDatabaseTest {

    private Context context;
    private SalaryDataRepository salaryDataRepository;
    private EmployeeRepository employeeRepository;

    @Before
    public void before() {
        context = RuntimeEnvironment.application.getApplicationContext();
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        salaryDataRepository = new SalaryDataRepositoryImpl(databaseHelper);
        employeeRepository = new EmployeeRepositoryImpl(databaseHelper);
    }

    @Test
    public void saveAndLoad() {
        Salary salary = salaryDataRepository.getSalary(0);
        System.out.println(salary);
        salary = salary.toBuilder().total(120000).build();
        salary = Calculator.calculateSalaries(salary);
        System.out.println("calculated :" + salary);
        salaryDataRepository.saveSalary(salary);
        Salary salary2 = salaryDataRepository.getSalary(0);
        System.out.println("loaded equals calculated: " + salary2.equals(salary));
    }

    @Test
    public void getAll(){
        List<Salary> salaries = salaryDataRepository.getAllSalaries();
        printAllSalaries();
    }

    @Test
    public void saveMinusOne(){
        Salary salary = salaryDataRepository.getSalary(0);
        List<SalaryTableRecord> newRecords = new ArrayList<>();
        for (SalaryTableRecord record : salary.salaryTableRecords()) {
            newRecords.add(record.toBuilder().id(-1).build());
        }
        salary = salary.toBuilder().id(-1).salaryTableRecords(newRecords).total(120000).build();
        salary = Calculator.calculateSalaries(salary);
        salaryDataRepository.saveSalary(salary);

        printAllSalaries();
    }

    @Test
    public void createNew(){
        Salary salary = createNewSalary();
        salaryDataRepository.saveSalary(salary);

        printAllSalaries();
    }

    private Salary createNewSalary() {
        List<SalaryTableRecord> records = createRecords();
        return Salary.create(-1, System.currentTimeMillis(), 0, records, "");
//        return null;
    }

    private List<SalaryTableRecord> createRecords() {
        List<Employee> allEmployees = employeeRepository.getAllEmployees();
        List<SalaryTableRecord> records = new ArrayList<>();
        for (Employee employee : allEmployees) {
            if (employee.isActive()){
                records.add(SalaryTableRecord.create(-1, -1, employee.name(), employee.coefficient(), 20f, 0));
            }
        }
        return records;
    }

    private void printAllSalaries() {
        List<Salary> allSalaries = salaryDataRepository.getAllSalaries();
        for (Salary salary : allSalaries) {
            printSalary(salary);
        }
    }

    private void printSalary(Salary salary){
        System.out.println(String.format("id: %1s, total: %2s, records:", salary.id(), salary.total()));
        for (SalaryTableRecord record : salary.salaryTableRecords()) {
            printsalaryTableRecords(record);
        }
    }

    private void printsalaryTableRecords(SalaryTableRecord record){
        System.out.println(record);
    }
}
