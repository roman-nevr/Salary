package ru.rubicon.salary.presentation.presenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ru.rubicon.salary.domain.Calculator;
import ru.rubicon.salary.domain.EmployeeRepository;
import ru.rubicon.salary.domain.SalaryDataRepository;
import ru.rubicon.salary.domain.entity.Employee;
import ru.rubicon.salary.domain.entity.Salary;
import ru.rubicon.salary.domain.entity.SalaryTableRecord;
import ru.rubicon.salary.presentation.OnItemClickListener;
import ru.rubicon.salary.presentation.fragments.SalaryDetailsFragment;

public class SalaryDetailsPresenter implements OnItemClickListener{

    @Inject SalaryDataRepository salaryRepository;
    @Inject EmployeeRepository employeeRepository;

    private SalaryDetailsFragment view;
    private int id;
    private Salary salary;

    @Inject
    public SalaryDetailsPresenter() {}

    public void start() {
        salary = null;
        if(id == -1){
            salary = createNewSalary();
        }else {
            salary = salaryRepository.getSalary(id);
        }
        view.showSalary(salary);
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

    public void setView(SalaryDetailsFragment view) {
        this.view = view;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override public void onItemClick(int id) {
        SalaryTableRecord record = salaryRepository.getSalaryTableRecord(id);
        view.showTableRecord(record);
    }

    public void onCalcButtonClick() {
        salary = Calculator.calculateSalaries(salary);
        salaryRepository.saveSalary(salary);
        view.showSalary(salary);
    }

    public void onRecordSave(int id, float coef, float days) {
        List<SalaryTableRecord> records = salary.salaryTableRecords();
        for (int index = 0; index < records.size(); index++) {
            if(id == records.get(index).id()){
                SalaryTableRecord record = records.get(index).toBuilder()
                        .coefficient(coef)
                        .amountsOfDays(days)
                        .build();
                records.set(index, record);
            }
        }
        salaryRepository.saveSalary(salary);
        view.showSalary(salary);
    }
}
