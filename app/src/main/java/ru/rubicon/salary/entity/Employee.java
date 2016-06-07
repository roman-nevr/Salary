package ru.rubicon.salary.entity;

import java.util.ArrayList;

/**
 * Created by roma on 03.06.2016.
 */
public class Employee {

    private String name;
    private String comment;
    private boolean active;
    private int balance;
    private int startBalance;

    public Employee(String name, boolean active, int startBalance, String comment) {
        this.name = name;
        this.comment = comment;
        this.active = active;
        this.startBalance = startBalance;
    }

    public Employee(String name, int startBalance) {
        this.name = name;
        this.active = true;
        this.comment = "";
        this.startBalance = startBalance;
    }

    public Employee(String name, boolean active, int startBalance){
        this.name = name;
        this.active = active;
        this.comment = "";
        this.startBalance = startBalance;
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

    public void setActive(boolean active) {
        this.active = active;
    }
}
