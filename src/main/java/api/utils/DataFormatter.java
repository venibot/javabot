package api.utils;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.User;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DataFormatter {

    public static String datetimeToString(OffsetDateTime dateTime) {
        return String.format("%s %s %s года в %s:%s", dateTime.getDayOfMonth(),
                Config.MONTHS.get(dateTime.getMonthValue()), dateTime.getYear(), dateTime.getHour(),
                ((dateTime.getMinute() + "").length() == 1 ? "0" + dateTime.getMinute(): dateTime
                        .getMinute()));
    }

    public static String unixToLogString(Long unixTime) {
        OffsetDateTime dateTime = DataFormatter.unixToDateTime(unixTime);
        return String.format("[%s:%s:%s %s %s %s]",
                dateTime.getHour(),
                ((dateTime.getMinute() + "").length() == 1 ? "0" + dateTime.getMinute() : dateTime.getMinute()),
                ((dateTime.getSecond() + "").length() == 1 ? "0" + dateTime.getSecond() : dateTime.getSecond()),
                dateTime.getDayOfMonth(),
                Config.MONTHS.get(dateTime.getMonthValue()),
                dateTime.getYear());
    }

    public static String longToUptime(Long uptime) {
        String result = "";
        int days = (int) (uptime / 86400);
        int hours = (int) ((uptime - days * 86400) / 3600);
        int minutes = (int) ((uptime - hours * 3600) / 60);
        return days + " дней, " + hours + " часов, " + minutes + " минут";
    }

    public static String getUserFlags(EnumSet<User.UserFlag> flags) {
        String flagsString = "";

        for (User.UserFlag flag: flags) {
            flagsString += Config.USER_FLAGS.get(flag.toString()) + " ";
        }
        return flagsString;
    }

    public static String getMissingPermissions(Permission permission) {
        return Config.PERMISSIONS.get(permission.toString());
    }

    public static String getMissingPermissions(List<Permission> permissions) {
        String permissionsString = "";

        for (Permission permission: permissions) {
            permissionsString += Config.PERMISSIONS.get(permission.toString()) + "\n";
        }
        return permissionsString;
    }

    public static String getMissingPermissions(Permission[] permissions) {
        String permissionsString = "";

        for (Permission permission: permissions) {
            permissionsString += Config.PERMISSIONS.get(permission.toString()) + "\n";
        }
        return permissionsString;
    }

    public static OffsetDateTime unixToDateTime(Long unixTime) {

        OffsetDateTime dateTime = new Date(unixTime).toInstant().atOffset(ZoneOffset.UTC);
        dateTime = dateTime.plusHours(3);
        return dateTime;
    }

    public static String accessLevelToString(short accessLevel) {

        if (accessLevel == 0) {
            return "пользователь бота";
        } else if (accessLevel == 1) {
            return "участник сервера поддержки";
        } else if (accessLevel == 2) {
            return "тестер бота";
        } else if (accessLevel == 3) {
            return "поддержка бота";
        } else if (accessLevel == 4) {
            return "разработчик бота";
        } else if (accessLevel == 5) {
            return "создатель бота";
        } else {
            return null;
        }
    }

    public static String firstUpperCase(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static class Weather {

        public static String translateWeatherMain(String input) {
            input = input.toLowerCase();
            switch (input) {
                case "rain":
                    return "Дождливо";
                case "clouds":
                    return "Облачно";
                case "clear":
                    return "Ясно";
                case "dust":
                    return "\"Пыльно\"";
                default:
                    return input;
            }
        }

        public static Integer pressureToMillimeters(Double pressure) {
            return Math.toIntExact(Math.round(pressure.intValue() * 100 / 133.3));
        }

    }

    public static String getTrackLength(Long length) {
        long hours = TimeUnit.MILLISECONDS.toHours(length);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(length);
        long seconds = Math.round(TimeUnit.MILLISECONDS.toSeconds(length) / 10);
        return hours + ":" + (String.valueOf(minutes).length() != 1 ? minutes : "0" + minutes)
                + ":" + (String.valueOf(seconds).length() != 1 ? seconds : "0" + seconds);
    }

    public static String getBoticordBotStatus(String status) {
        switch (status) {
            case "APPROVED":
                return "Одобрен";
            case "DECLINED":
                return "Отклонён";
            case "FIRST_CHECKING":
                return "На проверке";
            case "SECOND_CHECKING":
                return "На перепроверке";
            default:
                return status + "(разработчик ещё не успел добавить обозначение этого статуса на русском, пожалуйста сообщите при это при помощи команды `баг`";
        }
    }

    public static String getBoticordBotLibrary(String library) {
        switch (library) {
            case "":
                return "Не указана";
            case "discordpy":
                return "discord.py(Python)";
            case "discordjs":
                return "discord.js(JavaScript)";
            case "eris":
                return "Eris(JavaScript)";
            case "javacord":
                return "Javacord(Java)";
            case "jda":
                return "JDA(Java)";
            case "discord4j":
                return "Discord4J(Java)";
            case "discordcr":
                return "discordcr(Crystal)";
            case "discord.net":
                return "Discord.Net(C#)";
            case "discordgo":
                return "discord.go(Golang)";
            case "dsharpplus":
                return "DSharpPlus(C#)";
            case "restcord":
                return "RestCord(PHP)";
            case "discordia":
                return "Discordia(Lua)";
            case "disco":
                return "Disco(Python)";
            case "discordrb":
                return "discordrb(Ruby)";
            case "serenity":
                return "serenity(Rust)";
            case "sword":
                return "Sword(Swift)";
            case "other":
                return "Другое";
            default:
                return library;
        }
    }
}
