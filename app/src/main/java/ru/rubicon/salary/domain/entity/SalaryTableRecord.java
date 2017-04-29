package ru.rubicon.salary.domain.entity;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

@AutoValue
public abstract class SalaryTableRecord {

    abstract public int salaryId();

    abstract public Employee employee();

    abstract public int salary();

    abstract public float amountsOfDays();

    public static SalaryTableRecord create(int salaryId, Employee employee, int salary, float amountsOfDays) {
        return builder()
                .salaryId(salaryId)
                .employee(employee)
                .salary(salary)
                .amountsOfDays(amountsOfDays)
                .build();
    }

    public static Builder builder() {
        return new AutoValue_SalaryTableRecord.Builder();
    }



    @AutoValue.Builder public abstract static class Builder {
        public abstract Builder salaryId(int salaryId);

        public abstract Builder employee(Employee employee);

        public abstract Builder salary(int salary);

        public abstract Builder amountsOfDays(float amountsOfDays);

        public abstract SalaryTableRecord build();
    }

    public static TypeAdapter<SalaryTableRecord> typeAdapter(Gson gson) {
        return new AutoValue_SalaryTableRecord.GsonTypeAdapter(gson);
    }
}
