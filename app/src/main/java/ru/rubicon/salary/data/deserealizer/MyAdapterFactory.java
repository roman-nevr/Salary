package ru.rubicon.salary.data.deserealizer;

import com.google.gson.TypeAdapterFactory;
import com.ryanharter.auto.value.gson.GsonTypeAdapterFactory;

@GsonTypeAdapterFactory
public abstract class MyAdapterFactory implements TypeAdapterFactory {

    public static MyAdapterFactory create() {
        return new AutoValueGson_MyAdapterFactory();
    }
}
