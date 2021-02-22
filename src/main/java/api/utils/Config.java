package api.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public class Config {

    private static final String PATH_TO_BOT_CONFIG = "src/main/resources/bot.properties";
    private static final String PATH_TO_DB_CONFIG = "src/main/resources/db.properties";

    public static HashMap<String, String> BOT_CONFIG = new HashMap<String, String>() {
        {
            Properties props = new Properties();

            try {
                FileInputStream config_file = new FileInputStream(PATH_TO_BOT_CONFIG);
                props.load(config_file);
                for (String key: props.stringPropertyNames()) {
                    BOT_CONFIG.put(key, props.getProperty(key));
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    };
    public static HashMap<String, String> DB_CONFIG = new HashMap<String, String>() {
        {
            Properties props = new Properties();

            try {
                FileInputStream config_file = new FileInputStream(PATH_TO_DB_CONFIG);
                props.load(config_file);
                for (String key: props.stringPropertyNames()) {
                    DB_CONFIG.put(key, props.getProperty(key));
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    };
    public static HashMap<Integer, String> MONTHS = new HashMap<Integer, String>() {
        {
            put(1, "января");
            put(2, "февраля");
            put(3, "марта");
            put(4, "апреля");
            put(5, "мая");
            put(6, "июня");
            put(7, "июля");
            put(8, "августа");
            put(9, "сентября");
            put(10, "октября");
            put(11, "ноября");
            put(12, "декабря");
        }
    };
    public static HashMap<String, String> USER_FLAGS = new HashMap<String, String>() {
        {
            put("STAFF", "<:discord_staff:777516108260704256>");
            put("PARTNER", "<:discord_partner:777513164912328706>");
            put("BUG_HUNTER_LEVEL_1", "<:bug_hunter:777543195483570197>");
            put("HYPESQUAD_BRAVERY", "<:hypesquad_bravery:777540499858653195>");
            put("HYPESQUAD_BRILLIANCE", "<:hypesquad_brilliance:777540500035076127>");
            put("HYPESQUAD_BALANCE", "<:hypesquad_balance:777540500026294272>");
            put("EARLY_SUPPORTER", "<:early_supporter:777504637094985758>");
            put("SYSTEM", "<:discord:777505535930007593>");
            put("BUG_HUNTER_LEVEL_2", "<:bug_hunter:777543195483570197>");
            put("VERIFIED_BOT", "<:verified_bot:777507474017615884>");
            put("VERIFIED_DEVELOPER", "<:verified_bot_developer:777510397316956170>");
        }
    };

}
