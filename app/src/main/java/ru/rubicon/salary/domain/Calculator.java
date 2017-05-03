package ru.rubicon.salary.domain;

import java.util.ArrayList;
import java.util.List;

import ru.rubicon.salary.domain.entity.Salary;
import ru.rubicon.salary.domain.entity.SalaryTableRecord;

public class Calculator {

    public static Salary calculateSalaries(Salary salary){

        float totalWorkWeight = 0f;
        int fixedSalariesSum = 0;
        for (SalaryTableRecord record : salary.salaryTableRecords()) {
            if(record.coefficient() != 0){
                totalWorkWeight = totalWorkWeight + record.coefficient() * record.amountsOfDays();
            } else {
                fixedSalariesSum = fixedSalariesSum + Math.round(record.dailySalary() * record.amountsOfDays());
            }
        }

        int total = salary.total();
        if (total < fixedSalariesSum) {
            total = fixedSalariesSum;
        }

        List<SalaryTableRecord> newRecords = new ArrayList<>();

        for (SalaryTableRecord record : salary.salaryTableRecords()) {
            int res;
            if(record.coefficient() != 0){
                res = Math.round(record.coefficient() * record.amountsOfDays()
                        / totalWorkWeight * (total - fixedSalariesSum));
            }else {
                res = Math.round(record.dailySalary() * record.amountsOfDays());
            }

            newRecords.add(record.toBuilder().salary(res).build());
        }
        return salary.toBuilder().total(total).salaryTableRecords(newRecords).build();
    }
}
