package ru.rubicon.salary.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by roma on 17.06.2016.
 */
public class Salary {
    private Date date;
    private int total;
    private ArrayList<Employee> employees;
    private ArrayList<Integer> employeesSalary;
    private ArrayList<Float> amountsOfDays;

    public Salary(int total, Date date) {
        this.total = total;
        this.date = date;
    }

    public Salary(Date date, int total, ArrayList<Employee> employees, ArrayList<Integer> employeesSalary, ArrayList<Float> amountsOfDays) {
        this.date = date;
        this.total = total;
        this.employees = employees;
        this.employeesSalary = employeesSalary;
        this.amountsOfDays = amountsOfDays;
    }

    public Date getDate() {return date;}

    public void setDate(Date date) {this.date = date;}

    public int getTotal() {return total;}

    public void setTotal(int total) {this.total = total;}

    public ArrayList<Employee> getEmployees() {return employees;}

    public void setEmployees(ArrayList<Employee> employees) {this.employees = employees;}

    public Employee getEmployee(int id){
        return employees.get(id);
    }

    public ArrayList<Integer> getEmployeeSalary() {return employeesSalary;}

    public void setEmployeeSalary(Employee employee, int employeeSalary) throws NullPointerException{
        int id = employees.indexOf(employee);
        employeesSalary.set(id, employeeSalary);
    }

    public void setAmountsOfDays(ArrayList<Float> amountsOfDays) {this.amountsOfDays = amountsOfDays;}

    public void setAmountOfDays(Employee employee, float amount) throws NullPointerException{
        int id = employees.indexOf(employee);
        amountsOfDays.set(id, amount);
    }

    public float getAmountOfDays(int id){
        return amountsOfDays.get(id);
    }

    public void setAmountOfDays(int id, float value){
        amountsOfDays.set(id, value);
    }

    private float calcTotalBase(){
        float sum = 0f;
        /*for (Employee e: employees) {
            sum = sum + e.getCoefficient()*amountsOfDays.get(employees.indexOf(e));
        }*/
        for (int id = 0; id < employees.size(); id++){
            sum = sum + employees.get(id).getCoefficient() * amountsOfDays.get(id);
        }
        return sum;
    }

    public void calculateSalary(){
        employeesSalary = new ArrayList<>();
        float totalBase = calcTotalBase();
        int concreteSalary = 0;
        for (int id = 0; id < employees.size(); id++){
            float c = employees.get(id).getCoefficient();
            float a = amountsOfDays.get(id);
            concreteSalary = Math.round(employees.get(id).getCoefficient() * amountsOfDays.get(id)
                    / totalBase * total);
            employeesSalary.add(concreteSalary);
        }
    }

    public void setCoefficient (int id, float value){
        employees.get(id).setCoefficient(value);
    }

    public Salary (){

        this.employees = new ArrayList<>(Arrays.asList(new Employee("Roman",1.2f, 16000), new Employee("Vitek", 1.2f, -16000), new Employee("Leha", 1.1f, 13000),
                                                             new Employee("Shurik", 1.2f, -16000), new Employee("Ivan", 1.2f, -16000), new Employee("Den", 1.2f, 13000)));
        this.employeesSalary = new ArrayList<>(Arrays.asList(new Integer(20000), new Integer(10000),new Integer(20000), new Integer(12000),new Integer(12000),new Integer(12000)));
        this.amountsOfDays = new ArrayList<>(Arrays.asList(new Float(22), new Float(22),new Float(22), new Float(22), new Float(22), new Float(22)));
        this.date = new Date();
        this.total = 140000;
        /*amountOfDays.add(new Float(20.0));
        amountOfDays.add(new Float(5.5));*/

    }
}
