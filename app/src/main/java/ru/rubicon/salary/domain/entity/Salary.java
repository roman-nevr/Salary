package ru.rubicon.salary.domain.entity;

import com.google.auto.value.AutoValue;

import java.util.List;

@AutoValue
public abstract class Salary {

    abstract public int id();

    abstract public long date();

    abstract public int total();

    abstract public List<SalaryTableRecord> salaryTableRecords();

    abstract public String comment();

    public static Salary create(int id, long date, int total, List<SalaryTableRecord> salaryTableRecords, String comment) {
        return builder()
                .id(id)
                .date(date)
                .total(total)
                .salaryTableRecords(salaryTableRecords)
                .comment(comment)
                .build();
    }

    public static Builder builder() {
        return new AutoValue_Salary.Builder();
    }

    abstract public Builder toBuilder();

    @AutoValue.Builder public abstract static class Builder {
        public abstract Builder id(int id);

        public abstract Builder date(long date);

        public abstract Builder total(int total);

        public abstract Builder salaryTableRecords(List<SalaryTableRecord> salaryTableRecords);

        public abstract Builder comment(String comment);

        public abstract Salary build();
    }
}
