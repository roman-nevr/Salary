package ru.rubicon.salary.presentation;

import android.app.Application;

import com.facebook.stetho.Stetho;

import ru.rubicon.salary.BuildConfig;
import ru.rubicon.salary.di.DaggerMainComponent;
import ru.rubicon.salary.di.MainComponent;
import ru.rubicon.salary.di.MainModule;


public class App extends Application {

    private MainComponent mainComponent;
    private static App instance;

    @Override public void onCreate() {
        super.onCreate();
        instance = this;
        initDi();
        initStetho();
    }

    private void initDi() {
        mainComponent = DaggerMainComponent.builder().mainModule(new MainModule(getApplicationContext())).build();
    }

    public MainComponent getMainComponent(){
        return mainComponent;
    }

    public static App getInstance(){
        return instance;
    }

    private void initStetho(){
        if(!BuildConfig.DEBUG){
            return;
        }
        // Create an InitializerBuilder
        Stetho.InitializerBuilder initializerBuilder =
                Stetho.newInitializerBuilder(this);

        // Enable Chrome DevTools
        initializerBuilder.enableWebKitInspector(
                Stetho.defaultInspectorModulesProvider(this)
        );

        // Enable command line interface
        initializerBuilder.enableDumpapp(
                Stetho.defaultDumperPluginsProvider(this)
        );

        // Use the InitializerBuilder to generate an Initializer
        Stetho.Initializer initializer = initializerBuilder.build();

        // Initialize Stetho with the Initializer
        Stetho.initialize(initializer);
    }
}
