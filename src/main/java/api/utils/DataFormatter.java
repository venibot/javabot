package api.utils;

import net.dv8tion.jda.api.entities.User;

import java.time.OffsetDateTime;
import java.util.EnumSet;

public class DataFormatter {

    public static String datetimeToString(OffsetDateTime dateTime) {
        return String.format("%s %s %s года в %s:%s", dateTime.getDayOfMonth(), Config.MONTHS.get(dateTime.getMonthValue()), dateTime.getYear(), dateTime.getHour(), dateTime.getMinute()).toString();
    }

    public static String getUserFlags(EnumSet<User.UserFlag> flags) {
        String flagsString = "";
        for (User.UserFlag flag: flags) {
            flagsString += Config.USER_FLAGS.get(flag.toString()) + " ";
        }
        return flagsString;
    }

}
