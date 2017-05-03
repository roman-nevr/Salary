package ru.rubicon.salary.domain.entity;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

@AutoValue
public abstract class SalaryTableRecord{

    abstract public int id();

    abstract public int salaryId();

    abstract public String employee();

    abstract public float coefficient();

    abstract public float amountsOfDays();

    abstract public int salary();

    public static Builder builder() {
        return new AutoValue_SalaryTableRecord.Builder();
    }

    public abstract Builder toBuilder();

    public static SalaryTableRecord create(int id, int salaryId, String employee, float coefficient, float amountsOfDays, int salary) {
        return builder()
                .id(id)
                .salaryId(salaryId)
                .employee(employee)
                .coefficient(coefficient)
                .amountsOfDays(amountsOfDays)
                .salary(salary)
                .build();
    }


    public static TypeAdapter<SalaryTableRecord> typeAdapter(Gson gson) {
        return new AutoValue_SalaryTableRecord.GsonTypeAdapter(gson);
    }

    @AutoValue.Builder public abstract static class Builder {
        public abstract Builder id(int id);

        public abstract Builder salaryId(int salaryId);

        public abstract Builder employee(String employee);

        public abstract Builder coefficient(float coefficient);

        public abstract Builder amountsOfDays(float amountsOfDays);

        public abstract Builder salary(int salary);

        public abstract SalaryTableRecord build();
    }
}
