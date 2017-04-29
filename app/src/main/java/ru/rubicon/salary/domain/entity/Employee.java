package ru.rubicon.salary.domain.entity;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

@AutoValue
public abstract class Employee {

    public static Employee DEFAULT = create(-1, "", 1f, true, false, 0, "");

    public abstract int id();

    public abstract String name();

    public abstract float coefficient();

    public abstract String comment();

    public abstract boolean isActive();

    public abstract int dailySalary();

    public abstract boolean hasFixedSalary();

    public static Employee create(int id, String name, float coefficient, boolean isActive, boolean hasFixedSalary, int dailySalary, String comment) {
        return builder()
                .id(id)
                .name(name)
                .coefficient(coefficient)
                .comment(comment)
                .isActive(isActive)
                .dailySalary(dailySalary)
                .hasFixedSalary(hasFixedSalary)
                .build();
    }

    public static Builder builder() {
        return new AutoValue_Employee.Builder();
    }




    @AutoValue.Builder public abstract static class Builder {
        public abstract Builder id(int id);

        public abstract Builder name(String name);

        public abstract Builder coefficient(float coefficient);

        public abstract Builder comment(String comment);

        public abstract Builder isActive(boolean isActive);

        public abstract Builder dailySalary(int dailySalary);

        public abstract Builder hasFixedSalary(boolean hasFixedSalary);

        public abstract Employee build();
    }

    public static TypeAdapter<Employee> typeAdapter(Gson gson) {
        return new AutoValue_Employee.GsonTypeAdapter(gson);
    }
}
