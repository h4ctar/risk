package ben.risk.irs;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.Map;

/**
 * IRS Type Helper.
 */
public final class IrsTypeHelper {

    private static final int DEFAULT_INT = 0;
    
    private static final String DEFAULT_STRING = "";

    private static final boolean DEFAULT_BOOLEAN = false;

    private static final Class DEFAULT_CLASS = Object.class;

    @NotNull
    public Serializable createIrsType(@NotNull String typeName, @NotNull Map<String, String> values) throws Exception {
        Class<?> type = Class.forName(typeName);
        Node tree = flatToTree(values);
        return convertNodeToValue(type, tree);
    }

    private Node flatToTree(Map<String, String> values) {
        Node root = new Node();

        for (Map.Entry<String, String> entry : values.entrySet()) {
            String[] fieldPath = entry.getKey().split("\\.");
            String fieldValue = entry.getValue();

            Node node = root;
            for (String nodeName : fieldPath) {
                Node nextNode = node.getNode(nodeName);
                if (nextNode == null) {
                    nextNode = new Node();
                    node.putNode(nodeName, nextNode);
                }
                node = nextNode;
            }
            node.setValue(fieldValue);
        }

        return root;
    }

    @NotNull
    private Serializable convertNodeToValue(@NotNull Class<?> type, @Nullable Node node) throws Exception {
        System.out.println();
        if (node == null) {
            System.out.println("convertNodeToValue(" + type.getSimpleName() + ", null)");
        }
        else {
            System.out.println("convertNodeToValue(" + type.getSimpleName() + ", ");
            node.print(1);
            System.out.println(")");
        }

        Serializable value;
        String string = node == null ? null : node.getValue();

        if (type.equals(Integer.class) || type.equals(int.class)) {
            value = string == null ? DEFAULT_INT : Integer.parseInt(string);
        }
        else if (type.equals(String.class)) {
            value = string == null ? DEFAULT_STRING : string;
        }
        else if (type.equals(Boolean.class) || type.equals(boolean.class)) {
            value = string == null ? DEFAULT_BOOLEAN : Boolean.parseBoolean(string);
        }
        else if (type.isEnum()) {
            Class<? extends Enum> enumType = (Class<? extends Enum>) type;
            value = string == null ? enumType.getEnumConstants()[0] : Enum.valueOf(enumType, string);
        }
        else if (type.equals(Class.class)) {
            value = string == null ? DEFAULT_CLASS : Class.forName(string);
        }
        else {
            Constructor<?> constructor = type.getDeclaredConstructors()[0];
            Object[] parameters = new Object[constructor.getParameters().length];

            for (int i = 0; i < constructor.getParameters().length; i++) {
                Parameter parameter = constructor.getParameters()[i];

                Class<?> parameterType = parameter.getType();
                Node parameterNode = node == null ? null : node.getNode(parameter.getName());

                if (parameterType.isInterface()) {
                    assert parameterNode != null;
                    parameterType = Class.forName(parameterNode.getNode("type").getValue());
                    assert parameterType.isAssignableFrom(parameter.getType());
                }

                parameters[i] = convertNodeToValue(parameterType, parameterNode);
            }

            System.out.print("new " + type.getSimpleName() + "(");
            for (Object parameter : parameters) {
                System.out.print(parameter + ", ");
            }
            System.out.println(")");

            value = (Serializable) constructor.newInstance(parameters);
        }

        System.out.println("value = " + value);
        System.out.println();

        return value;
    }
}
