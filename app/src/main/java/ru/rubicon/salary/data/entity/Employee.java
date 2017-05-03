package ru.rubicon.salary.data.entity;

import java.util.ArrayList;

import ru.rubicon.salary.utils.interfaces;

public class Employee implements interfaces.id {

    private int id;
    private String name;
    private float coefficient;
    private int startBalance;
    private int balance;
    private String comment;
    private boolean active;
    private int fixedSalary;

    public Employee(String name, float coefficient, int startBalance, int balance, String comment, boolean active, int fixedSalary) {
        this.name = name;
        this.coefficient = coefficient;
        this.startBalance = startBalance;
        this.balance = balance;
        this.comment = comment;
        this.active = active;
        this.fixedSalary = fixedSalary;
    }

    public Employee(String name, boolean active, int startBalance, String comment) {
        this.name = name;
        this.comment = comment;
        this.active = active;
        this.startBalance = startBalance;
        fixedSalary = 0;
    }

    public Employee(String name, int startBalance) {
        this.name = name;
        this.active = true;
        this.comment = "";
        this.balance = startBalance;
        fixedSalary = 0;
    }

    public Employee(String name, boolean active, int startBalance){
        this.name = name;
        this.active = active;
        this.comment = "";
        this.startBalance = startBalance;
        fixedSalary = 0;
    }

    public Employee(String name, float coefficient, int balance) {
        this.name = name;
        this.coefficient = coefficient;
        this.balance = balance;
    }

    public Employee(int id, String name, float coefficient) {
        this.id = id;
        this.name = name;
        this.coefficient = coefficient;
    }

    public String getComment() {
        return comment;
    }

    public void calcBalance(ArrayList<Integer> money){
        balance = startBalance;
        if (money != null){
            for (Integer cash : money) {
                balance = balance + cash;
            }
        }
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {this.active = active;}

    public void setActive(int active) { this.active = (active == 1);}

    public int getBalance() {return balance;}

    public float getCoefficient() {return coefficient;}

    public void setCoefficient(float coefficient) {this.coefficient = coefficient;}

    public int getId() {return id; }

    public void setId(int id) {this.id = id; }

}
