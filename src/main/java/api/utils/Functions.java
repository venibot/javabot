package api.utils;

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
}
