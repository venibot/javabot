package api.utils;

import java.util.List;

public class Functions {

    public static void debug(Object[] array) {
        String output = "[";

        for (Object object: array) {
            output += object + ", ";
        }

        output = output.replaceAll(", $", "");
        output += "]";

        System.out.println(output);
    }

    public static void debug(List array) {
        String output = "[";
        for (Object object: array) {
            output += object + ", ";
        }
        output = output.replaceAll(", $", "");
        output += "]";
        System.out.println(output);
    }

    public static String debug(Object[] array, boolean toReturn) {
        String output = "[";
        for (Object object: array) {
            output += object + ", ";
        }
        output = output.replaceAll(", $", "");
        output += "]";
        if (toReturn) {
            return output;
        } else {
            System.out.println(output);
            return null;
        }
    }

    public static String debug(List array, boolean toReturn) {
        String output = "[";
        for (Object object: array) {
            output += object + ", ";
        }
        output = output.replaceAll(", $", "");
        output += "]";
        if (toReturn) {
            return output;
        } else {
            System.out.println(output);
            return null;
        }
    }

}
