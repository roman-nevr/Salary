package ru.rubicon.salary.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import ru.rubicon.salary.utils.interfaces;

/**
 * Created by roma on 17.06.2016.
 */
public class Salary implements interfaces.id {
    private int id;
    private long date;
    private int total;
    private ArrayList<Employee> employees;
    private ArrayList<Integer> employeesSalary;
    private ArrayList<Float> amountsOfDays;
    private String comment;

    public Salary(int total, long date) {
        this.total = total;
        this.date = date;
    }

    public Salary(int id, long date, int total, ArrayList<Employee> employees, ArrayList<Integer> employeesSalary, ArrayList<Float> amountsOfDays) {
        this.id = id;
        this.date = date;
        this.total = total;
        this.employees = employees;
        this.employeesSalary = employeesSalary;
        this.amountsOfDays = amountsOfDays;
    }

    public long getDate() {return date;}

    public void setDate(long date) {this.date = date;}

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

    public int getId() {        return id;    }

    public void setId(int id) {        this.id = id;    }

    public ArrayList<Float> getAmountsOfDays() {        return amountsOfDays;    }

    public ArrayList<Integer> getEmployeesSalary() {        return employeesSalary;    }

    public String getComment() {        return comment;    }

    public void setComment(String comment) {        this.comment = comment;    }

    public void setEmployeesSalary(ArrayList<Integer> employeesSalary) {        this.employeesSalary = employeesSalary;    }

    public Salary (){

        this.employees = new ArrayList<>(Arrays.asList(new Employee(0,"Roman",1.2f), new Employee(1, "Vitek", 1.2f), new Employee(2, "Leha", 1.1f),
                                                             new Employee(3, "Shurik", 1.2f), new Employee(4, "Ivan", 1.2f), new Employee(5, "Den", 1.2f)));
        this.employeesSalary = new ArrayList<>(Arrays.asList(new Integer(20000), new Integer(10000),new Integer(20000), new Integer(12000),new Integer(12000),new Integer(12000)));
        this.amountsOfDays = new ArrayList<>(Arrays.asList(new Float(22), new Float(22),new Float(22), new Float(22), new Float(22), new Float(22)));
        this.date = new Date().getTime();
        this.total = 140000;
        /*amountOfDays.add(new Float(20.0));
        amountOfDays.add(new Float(5.5));*/
    }

    public Salary(int id, long date, int total) {
        this.id = id;
        this.total = total;
        this.date = date;
    }
}
