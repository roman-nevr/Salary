package ru.rubicon.salary.entity;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by roma on 17.06.2016.
 */
public class Salary {
    private Date date;
    private int total;
    private ArrayList<Employee> employees;
    private ArrayList<Integer> employeesSalary;
    private ArrayList<Integer> amountsOfDays;

    public Salary(int total, Date date) {
        this.total = total;
        this.date = date;
    }

    public Date getDate() {return date;}

    public void setDate(Date date) {this.date = date;}

    public int getTotal() {return total;}

    public void setTotal(int total) {this.total = total;}

    public ArrayList<Employee> getEmployees() {return employees;}

    public void setEmployees(ArrayList<Employee> employees) {this.employees = employees;}

    public ArrayList<Integer> getEmployeeSalary() {return employeesSalary;}

    public void setEmployeeSalary(Employee employee, int employeeSalary) throws NullPointerException{
        int id = employees.indexOf(employee);
        employeesSalary.set(id, employeeSalary);
    }

    public void setAmountsOfDays(ArrayList<Integer> amountsOfDays) {this.amountsOfDays = amountsOfDays;}

    public void setAmountOfDays(Employee employee, int amount) throws NullPointerException{
        int id = employees.indexOf(employee);
        amountsOfDays.set(id, amount);
    }

    private float calcTotalBase(){
        float sum = 0f;
        /*for (Employee e: employees) {
            sum = sum + e.getCoefficient()*amountsOfDays.get(employees.indexOf(e));
        }*/
        for (int id = 0; id < employees.size(); id++){
            sum = sum + employees.get(id).getCoefficient() + amountsOfDays.get(id);
        }
        return sum;
    }

    public void calculateSalary(){
        employeesSalary = new ArrayList<>(employees.size());
        float totalBase = calcTotalBase();
        int concreteSalary;
        for (int id = 0; id < employees.size(); id++){
            concreteSalary = Math.round(employees.get(id).getCoefficient() * amountsOfDays.get(id)
                    / totalBase);
            employeesSalary.set(id, concreteSalary);
        }
    }
}
