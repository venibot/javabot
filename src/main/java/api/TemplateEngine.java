package api;

import java.util.HashMap;

public class TemplateEngine {

    public static String render(String text, HashMap<String, String> values) {
        for (String key: values.keySet()) {
            text = text.replace("{{" + key + "}}", values.get(key));
        }
        return text;
    }

}
