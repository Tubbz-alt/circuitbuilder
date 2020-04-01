package fwcd.circuitbuilder.model.utils;

import java.util.function.BiPredicate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;

import fwcd.circuitbuilder.model.grid.CircuitItemModel;
import fwcd.circuitbuilder.model.grid.cable.CableColorEqualityChecker;
import fwcd.circuitbuilder.model.grid.cable.CableModel;
import fwcd.circuitbuilder.model.grid.components.ClockModel;
import fwcd.circuitbuilder.model.grid.components.DLatchModel;
import fwcd.circuitbuilder.model.grid.components.DMasterSlaveModel;
import fwcd.circuitbuilder.model.grid.components.DemultiplexerModel;
import fwcd.circuitbuilder.model.grid.components.EqvGateModel;
import fwcd.circuitbuilder.model.grid.components.FullAdderModel;
import fwcd.circuitbuilder.model.grid.components.HybridComponent;
import fwcd.circuitbuilder.model.grid.components.InputComponentModel;
import fwcd.circuitbuilder.model.grid.components.InverterModel;
import fwcd.circuitbuilder.model.grid.components.JkFlipFlopModel;
import fwcd.circuitbuilder.model.grid.components.LampModel;
import fwcd.circuitbuilder.model.grid.components.LeverModel;
import fwcd.circuitbuilder.model.grid.components.MultiplexerModel;
import fwcd.circuitbuilder.model.grid.components.NandGateModel;
import fwcd.circuitbuilder.model.grid.components.NorGateModel;
import fwcd.circuitbuilder.model.grid.components.OrGateModel;
import fwcd.circuitbuilder.model.grid.components.OutputComponentModel;
import fwcd.circuitbuilder.model.grid.components.RsFlipFlopModel;
import fwcd.circuitbuilder.model.grid.components.RsLatchModel;
import fwcd.circuitbuilder.model.grid.components.RsMasterSlaveModel;
import fwcd.circuitbuilder.model.grid.components.TFlipFlopModel;
import fwcd.circuitbuilder.model.grid.components.XorGateModel;
import fwcd.circuitbuilder.utils.ConcurrentMultiKeyHashMap;
import fwcd.circuitbuilder.utils.MultiKeyMap;
import fwcd.fructose.structs.ArraySetStack;
import fwcd.fructose.structs.SetStack;

public class GsonFactory {
    private static final Gson GSON = new GsonBuilder()
        .setVersion(0.1)
        .enableComplexMapKeySerialization()
        .registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(CircuitItemModel.class)
            .registerSubtype(InverterModel.class)
            .registerSubtype(LampModel.class)
            .registerSubtype(LeverModel.class)
            .registerSubtype(ClockModel.class)
            .registerSubtype(CableModel.class)
            .registerSubtype(InputComponentModel.class)
            .registerSubtype(OutputComponentModel.class)
            .registerSubtype(HybridComponent.class)
            .registerSubtype(XorGateModel.class)
            .registerSubtype(EqvGateModel.class)
            .registerSubtype(OrGateModel.class)
            .registerSubtype(NandGateModel.class)
            .registerSubtype(NorGateModel.class)
            .registerSubtype(RsFlipFlopModel.class)
            .registerSubtype(RsLatchModel.class)
            .registerSubtype(RsMasterSlaveModel.class)
            .registerSubtype(DLatchModel.class)
            .registerSubtype(DMasterSlaveModel.class)
            .registerSubtype(TFlipFlopModel.class)
            .registerSubtype(JkFlipFlopModel.class)
            .registerSubtype(MultiplexerModel.class)
            .registerSubtype(DemultiplexerModel.class)
            .registerSubtype(FullAdderModel.class))
        .registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(SetStack.class)
            .registerSubtype(ArraySetStack.class))
        .registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(BiPredicate.class)
            .registerSubtype(CableColorEqualityChecker.class))
        .registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(MultiKeyMap.class)
            .registerSubtype(ConcurrentMultiKeyHashMap.class))
        .create();

    public static Gson newGson() {
        return GSON;
    }
}
