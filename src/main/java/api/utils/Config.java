package api.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public class Config {

    public static final String PATH_TO_BOT_CONFIG = "src/main/resources/bot.properties";
    public static final String PATH_TO_DB_CONFIG = "src/main/resources/db.properties";

    public static HashMap<String, String> BOT_CONFIG = new HashMap<>();
    public static HashMap<String, String> DB_CONFIG = new HashMap<>();
    public static HashMap<Integer, String> MONTHS = new HashMap<>();
    public static HashMap<String, String> USER_FLAGS = new HashMap<>();

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

    public static void getDatabaseConfig() throws Exception {
        Properties props = new Properties();

        try {
            FileInputStream config_file = new FileInputStream(PATH_TO_DB_CONFIG);
            props.load(config_file);
            for (String key: props.stringPropertyNames()) {
                DB_CONFIG.put(key, props.getProperty(key));
            }
        }
        catch (IOException e) {
            throw new Exception("Конфигурационного файла " + PATH_TO_DB_CONFIG + " не обнаружено!");
        }
    }

    public static void getMonthsConfig() {
        MONTHS.put(1, "января");
        MONTHS.put(2, "февраля");
        MONTHS.put(3, "марта");
        MONTHS.put(4, "апреля");
        MONTHS.put(5, "мая");
        MONTHS.put(6, "июня");
        MONTHS.put(7, "июля");
        MONTHS.put(8, "августа");
        MONTHS.put(9, "сентября");
        MONTHS.put(10, "октября");
        MONTHS.put(11, "ноября");
        MONTHS.put(12, "декабря");
    }

    public static void getUserFlagsAsEmojis() {
        USER_FLAGS.put("STAFF", "<:discord_staff:777516108260704256>");
        USER_FLAGS.put("PARTNER", "<:discord_partner:777513164912328706>");
        USER_FLAGS.put("BUG_HUNTER_LEVEL_1", "<:bug_hunter:777543195483570197>");
        USER_FLAGS.put("HYPESQUAD_BRAVERY", "<:hypesquad_bravery:777540499858653195>");
        USER_FLAGS.put("HYPESQUAD_BRILLIANCE", "<:hypesquad_brilliance:777540500035076127>");
        USER_FLAGS.put("HYPESQUAD_BALANCE", "<:hypesquad_balance:777540500026294272>");
        USER_FLAGS.put("EARLY_SUPPORTER", "<:early_supporter:777504637094985758>");
        USER_FLAGS.put("SYSTEM", "<:discord:777505535930007593>");
        USER_FLAGS.put("BUG_HUNTER_LEVEL_2", "<:bug_hunter:777543195483570197>");
        USER_FLAGS.put("VERIFIED_BOT", "<:verified_bot:777507474017615884>");
        USER_FLAGS.put("VERIFIED_DEVELOPER", "<:verified_bot_developer:777510397316956170>");
    }

}
