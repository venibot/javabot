package api.utils;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.User;

import java.time.OffsetDateTime;
import java.util.EnumSet;
import java.util.List;

public class DataFormatter {

    public static String datetimeToString(OffsetDateTime dateTime) {
        return String.format("%s %s %s года в %s:%s", dateTime.getDayOfMonth(), Config.MONTHS.get(dateTime.getMonthValue()), dateTime.getYear(), dateTime.getHour(), ((dateTime.getMinute() + "").length() == 1 ? "0" + dateTime.getMinute(): dateTime.getMinute())).toString();
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

}
