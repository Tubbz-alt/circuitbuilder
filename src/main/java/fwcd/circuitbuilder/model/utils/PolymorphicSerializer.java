package fwcd.circuitbuilder.model.utils;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class PolymorphicSerializer<T> implements JsonSerializer<T>, JsonDeserializer<T> {
    private static final String CLASS_KEY = "_class";
    private static final String VALUE_KEY = "_value";
    
    @Override
    public JsonElement serialize(T src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObj = new JsonObject();
        String className = src.getClass().getName();
        try {
            Type runtimeType = Class.forName(className);
            if (typeOfSrc.getTypeName().equals(runtimeType.getTypeName())) {
                throw new RuntimeException("Cannot serialize concrete type " + className + " with PolymorphicSerializer");
            }
            jsonObj.addProperty(CLASS_KEY, className);
            jsonObj.add(VALUE_KEY, context.serialize(src, runtimeType));
            return jsonObj;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObj = json.getAsJsonObject();
        String className = jsonObj.get(CLASS_KEY).getAsString();
        Class<T> actualClass;
        try {
            actualClass = (Class<T>) Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new JsonParseException("Could not find class: " + className);
        }
        return context.deserialize(jsonObj.getAsJsonObject(VALUE_KEY), actualClass);
    }
}
