package ben.risk.irs;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

/**
 * IRS Type Helper.
 */
public final class IrsTypeHelper {

    @NotNull
    public Serializable createIrsType(@NotNull String typeName, @NotNull Map<String, String> values) throws Exception {
        Class<?> type = Class.forName(typeName);
        Constructor<?> constructor = type.getDeclaredConstructors()[0];
        Object[] parameters = new Object[constructor.getParameters().length];
        for (int i = 0; i < constructor.getParameters().length; i++) {
            Parameter parameter = constructor.getParameters()[i];
            parameters[i] = convertStringToValue(parameter.getClass(), values.get(parameter.getName()));
        }
        return (Serializable) constructor.newInstance(parameters);
    }

    @NotNull
    public Object convertStringToValue(@NotNull Class<?> objectType, @NotNull String string) throws Exception {
        Object value = null;
        if (objectType.equals(Integer.class) || objectType.equals(int.class)) {
            value = Integer.parseInt(string);
        }
        else if (objectType.equals(String.class)) {
            value = string;
        }
        if (value == null) {
            throw new Exception("Could not convert " + string + " into a " + objectType.getSimpleName());
        }
        return value;
    }

    @NotNull
    public String getFieldValue(@NotNull Object object, @NotNull String fieldName) throws Exception {
        Method method = object.getClass().getDeclaredMethod(fieldName);
        Object value = method.invoke(object);
        return convertValueToString(value);
    }

    @NotNull
    public String convertValueToString(@NotNull Object value) throws Exception {
        String string = null;
        if (string == null) {
            throw new Exception("Could not convert " + value + " into a string");
        }
        return string;
    }
}
