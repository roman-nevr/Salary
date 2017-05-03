package ru.rubicon.salary.di;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.rubicon.salary.data.sqlite.DatabaseHelper;
import ru.rubicon.salary.data.DAO.EmployeeDataSource;
import ru.rubicon.salary.data.DAO.SalaryDataSource;
import ru.rubicon.salary.data.deserealizer.MyAdapterFactory;
import ru.rubicon.salary.data.sqlite.EmployeeRepositoryImpl;
import ru.rubicon.salary.data.sqlite.SalaryDataRepositoryImpl;
import ru.rubicon.salary.domain.EmployeeRepository;
import ru.rubicon.salary.domain.SalaryDataRepository;

@Module
public class MainModule {

    private Context context;

    public MainModule(Context context) {
        this.context = context;
    }

    @Singleton
    @Provides
    public Gson provideGson(){
        return new GsonBuilder()
                .registerTypeAdapterFactory(MyAdapterFactory.create())
                .create();
    }

    @Singleton
    @Provides
    public Context provideContext(){
        return context;
    }

    @Singleton
    @Provides
    public DatabaseHelper provideDatabaseHelper(Context context){
        return new DatabaseHelper(context);
    }

//    @Singleton
//    @Provides
//    public EmployeeDataSource provideEmployeeDataSource(DatabaseHelper databaseHelper){
//        return new EmployeeDataSource(databaseHelper);
//    }
//
//    @Singleton
//    @Provides
//    public SalaryDataSource provideSalaryDataSource(DatabaseHelper databaseHelper){
//        return new SalaryDataSource(databaseHelper);
//    }

    @Singleton
    @Provides
    public SalaryDataRepository provideSalaryDataRepository(DatabaseHelper databaseHelper){
        return new SalaryDataRepositoryImpl(databaseHelper);
    }

    @Singleton
    @Provides
    public EmployeeRepository provideEmployeeRepository(DatabaseHelper databaseHelper){
        return new EmployeeRepositoryImpl(databaseHelper);
    }
}
