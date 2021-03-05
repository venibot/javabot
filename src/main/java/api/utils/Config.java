package api.utils;

import net.dv8tion.jda.api.entities.Message;

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
    public static HashMap<String, String> PERMISSIONS = new HashMap<>();
    public static HashMap<String, String> LOG_ACTIONS =  new HashMap<>();
    public static HashMap<Long, Message> MESSAGE_CACHE = new HashMap<>();

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
    
    public static void getPermissions() {
        PERMISSIONS.put("CREATE_INSTANT_INVITE", "Создавать приглашения");
        PERMISSIONS.put("MESSAGE_ADD_REACTION", "Добавлять реакции");
        PERMISSIONS.put("ADMINISTRATOR", "Администратор");
        PERMISSIONS.put("MESSAGE_ATTACH_FILES", "Прикреплять файлы");
        PERMISSIONS.put("BAN_MEMBERS", "Блокировать участников");
        PERMISSIONS.put("NICKNAME_CHANGE", "Изменять никнейм");
        PERMISSIONS.put("VOICE_CONNECT", "Подключаться");
        PERMISSIONS.put("VOICE_DEAF_OTHERS", "Отключать звук участникам");
        PERMISSIONS.put("MESSAGE_EMBED_LINKS", "Вставлять ссылки");
        PERMISSIONS.put("MESSAGE_EXT_EMOJI", "Использовать эмодзи с других серверов");
        PERMISSIONS.put("KICK_MEMBERS", "Выгонять участников");
        PERMISSIONS.put("MANAGE_CHANNEL", "Управлять каналами");
        PERMISSIONS.put("MANAGE_EMOTES", "Управлять эмодзи");
        PERMISSIONS.put("MANAGE_SERVER", "Управлять сервером");
        PERMISSIONS.put("MESSAGE_MANAGE", "Управлять сообщениями");
        PERMISSIONS.put("NICKNAME_MANAGE", "Управлять никнеймами");
        PERMISSIONS.put("MANAGE_PERMISSIONS", "Управлять ролями");
        PERMISSIONS.put("MANAGE_ROLES", "Управлять ролями");
        PERMISSIONS.put("MANAGE_WEBHOOKS", "Управлять вебхуками");
        PERMISSIONS.put("MESSAGE_MENTION_EVERYONE", "Упоминать @everyone, @here и все роли");
        PERMISSIONS.put("VOICE_MOVE_OTHERS", "Перемещать участников");
        PERMISSIONS.put("VOICE_MUTE_OTHERS", "Отключать голос участникам");
        PERMISSIONS.put("PRIORITY_SPEAKER", "Приоритетный голос");
        PERMISSIONS.put("MESSAGE_HISTORY", "Читать историю сообщений");
        PERMISSIONS.put("MESSAGE_READ", "Читать сообщения");
        PERMISSIONS.put("MESSAGE_WRITE", "Отправлять сообщения");
        PERMISSIONS.put("MESSAGE_TTS", "Отправлять TTS-сообщения");
        PERMISSIONS.put("VOICE_SPEAK", "Говорить");
        PERMISSIONS.put("VOICE_STREAM", "Видео");
        PERMISSIONS.put("VOICE_USE_VAD", "Активация по голосу");
        PERMISSIONS.put("VIEW_AUDIT_LOGS", "Просмотр журнала аудита");
        PERMISSIONS.put("VIEW_CHANNEL", "Просматривать канал");
        PERMISSIONS.put("VIEW_GUILD_INSIGHTS", "Смотреть аналитику сервера");
    }

    public static void getLogActions() {
        LOG_ACTIONS.put("удаление_сообщения", "messageDelete");
        LOG_ACTIONS.put("изменение_сообщения", "messageEdit");
        LOG_ACTIONS.put("добавление_роли", "roleAdd");
        LOG_ACTIONS.put("снятие_роли", "roleRemove");
        LOG_ACTIONS.put("вход_участника", "memberJoin");
        LOG_ACTIONS.put("выход_участника", "memberLeave");
        LOG_ACTIONS.put("смена_ника", "nicknameUpdate");
        LOG_ACTIONS.put("вход_в_канал", "voiceJoin");
        LOG_ACTIONS.put("выход_из_канала", "voiceLeave");
    }

}
