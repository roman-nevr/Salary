package ru.rubicon.salary.di;

import dagger.Subcomponent;
import ru.rubicon.salary.presentation.fragments.EmployeeDetailsFragment;
import ru.rubicon.salary.presentation.fragments.EmployeesListFragment;

@Subcomponent
public interface EmployeeComponent {
    void inject(EmployeesListFragment fragment);
    void inject(EmployeeDetailsFragment fragment);
}
