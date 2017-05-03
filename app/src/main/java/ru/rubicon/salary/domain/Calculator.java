package ru.rubicon.salary.domain;

import java.util.ArrayList;
import java.util.List;

import ru.rubicon.salary.domain.entity.Salary;
import ru.rubicon.salary.domain.entity.SalaryTableRecord;

public class Calculator {

    public static Salary calculateSalaries(Salary salary){

        float totalWorkWeight = calcTotalWorkWeight(salary.salaryTableRecords());
        List<SalaryTableRecord> newRecords = new ArrayList<>();

        for (SalaryTableRecord record : salary.salaryTableRecords()) {
            int res = Math.round(record.coefficient() * record.amountsOfDays()
                    / totalWorkWeight * salary.total());
            newRecords.add(record.toBuilder().salary(res).build());
        }
        return salary.toBuilder().salaryTableRecords(newRecords).build();

    }

    private static float calcTotalWorkWeight(List<SalaryTableRecord> salaryTableRecords) {
        float sum = 0f;
        for (SalaryTableRecord record : salaryTableRecords) {
            sum = sum + record.coefficient() * record.amountsOfDays();
        }
        return sum;
    }
}
