package ru.rubicon.salary.di;

import dagger.Subcomponent;
import ru.rubicon.salary.presentation.fragments.SalaryDetailsFragment;
import ru.rubicon.salary.presentation.fragments.SalaryListFragment;

@Subcomponent
public interface SalaryComponent {
    void inject(SalaryListFragment fragment);

    void inject(SalaryDetailsFragment fragment);
}
