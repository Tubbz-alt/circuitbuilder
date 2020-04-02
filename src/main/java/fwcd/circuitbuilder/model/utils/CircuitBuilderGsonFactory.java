package fwcd.circuitbuilder.model.utils;

import java.util.function.BiPredicate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fwcd.circuitbuilder.model.grid.CircuitItemModel;
import fwcd.circuitbuilder.model.grid.components.Circuit1x1ComponentModel;
import fwcd.circuitbuilder.utils.MultiKeyMap;
import fwcd.fructose.structs.SetStack;

public class CircuitBuilderGsonFactory {
    private static final Gson INSTANCE;
    
    static {
        GsonBuilder gson = new GsonBuilder()
            .setVersion(0.1)
            .enableComplexMapKeySerialization()
            .registerTypeAdapter(CircuitItemModel.class, new PolymorphicSerializer<>())
            .registerTypeAdapter(Circuit1x1ComponentModel.class, new PolymorphicSerializer<>())
            .registerTypeAdapter(BiPredicate.class, new PolymorphicSerializer<>())
            .registerTypeAdapter(SetStack.class, new PolymorphicSerializer<>())
            .registerTypeAdapter(MultiKeyMap.class, new PolymorphicSerializer<>());

        INSTANCE = gson.create();
    }

    public static Gson newGson() {
        return INSTANCE;
    }
}
