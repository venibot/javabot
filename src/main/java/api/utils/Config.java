package api.utils;

import com.mongodb.MongoClient;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.sharding.ShardManager;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

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
    public static Long COMMANDS_COMPLETED = 0L;
    public static ShardManager BOT;
    public static HashMap<TimeUnit, String[]> TIMES = new HashMap<>();
    public static MongoClient MONGO = null;

    public static void init() {

        getPermissions();
        getTimes();
        getUserFlagsAsEmojis();
        getMonthsConfig();
        try {
            getBotConfig();
            getDatabaseConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }
        getLogActions();
    }

    private static void getBotConfig() throws Exception {

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

    private static void getDatabaseConfig() throws Exception {
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

    private static void getMonthsConfig() {

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

    private static void getUserFlagsAsEmojis() {

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

    private static void getPermissions() {

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

    private static void getLogActions() {

        LOG_ACTIONS.put("удаление_сообщения", "messageDelete");
        LOG_ACTIONS.put("изменение_сообщения", "messageEdit");
        LOG_ACTIONS.put("закрепление_сообщения", "messagePin");
        LOG_ACTIONS.put("добавление_роли", "roleAdd");
        LOG_ACTIONS.put("снятие_роли", "roleRemove");
        LOG_ACTIONS.put("вход_участника", "memberJoin");
        LOG_ACTIONS.put("выход_участника", "memberLeave");
        LOG_ACTIONS.put("смена_ника", "nicknameUpdate");
        LOG_ACTIONS.put("вход_в_канал", "voiceJoin");
        LOG_ACTIONS.put("выход_из_канала", "voiceLeave");
        LOG_ACTIONS.put("стрим", "voiceStreamStart");
        LOG_ACTIONS.put("конец_стрима", "voiceStreamStop");
        LOG_ACTIONS.put("войс_мьют", "voiceMuteEvent");
        LOG_ACTIONS.put("войс_размьют", "voiceUnMuteEvent");
        LOG_ACTIONS.put("включение_звука", "voiceDeafenEvent");
        LOG_ACTIONS.put("отключение_звука", "voiceUnDeafenEvent");
        LOG_ACTIONS.put("создание_роли", "roleCreate");
        LOG_ACTIONS.put("удаление_роли", "roleDelete");
        LOG_ACTIONS.put("изменение_роли", "roleUpdate");
        LOG_ACTIONS.put("создание_категории", "categoryCreate");
        LOG_ACTIONS.put("изменение_категории", "categoryUpdate");
        LOG_ACTIONS.put("удаление_категории", "categoryDelete");
        LOG_ACTIONS.put("создание_канала", "channelCreate");
        LOG_ACTIONS.put("изменение_канала", "channelUpdate");
        LOG_ACTIONS.put("удаление_канала", "channelDelete");
        LOG_ACTIONS.put("создание_эмодзи", "emoteAdd");
        LOG_ACTIONS.put("изменение_эмодзи", "emoteUpdate");
        LOG_ACTIONS.put("удаление_эмодзи", "emoteRemove");
        LOG_ACTIONS.put("бан", "userBan");
        LOG_ACTIONS.put("разбан", "userUnban");
        LOG_ACTIONS.put("изменение_сервера", "guildUpdate");
        LOG_ACTIONS.put("создание_приглашения", "inviteCreate");
        LOG_ACTIONS.put("удаление_приглашения", "inviteDelete");
    }

    private static void getTimes() {

        TIMES.put(TimeUnit.MINUTES, new String[]{"м", "мин", "минут", "m", "min", "minutes"});
        TIMES.put(TimeUnit.HOURS, new String[]{"ч", "час", "h", "hours"});
        TIMES.put(TimeUnit.DAYS, new String[]{"д", "дн", "дни", "d", "days"});
    }
}
