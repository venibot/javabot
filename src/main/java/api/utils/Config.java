package api.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public class Config {

    public static final String PATH_TO_BOT_CONFIG = "src/main/resources/bot.properties";

    public static HashMap<String, String> BOT_CONFIG = new HashMap<>();

    public static void getBotConfig() throws Exception {
        Properties props = new Properties();

        try {
            FileInputStream config_file = new FileInputStream(PATH_TO_BOT_CONFIG);
            props.load(config_file);
            for (String key: props.stringPropertyNames()) {
                BOT_CONFIG.put(key, props.getProperty(key));
            }
        }
        catch (IOException e) {
            throw new Exception("Конфигурационного файла " + PATH_TO_BOT_CONFIG + " не обнаружено!");
        }
    }

}
