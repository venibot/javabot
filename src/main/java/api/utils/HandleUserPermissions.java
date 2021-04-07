package api.utils;

import api.BasicEmbed;
import api.SupportServer;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class HandleUserPermissions {

    public static boolean handlePermissions(MessageReceivedEvent msg_event, Permission permission) {

        if (msg_event.getMember().getPermissionsExplicit(msg_event.getTextChannel()).contains(permission) || msg_event
                .getMember().isOwner()) {
            return true;
        }
        else {
            BasicEmbed errorEmbed = new BasicEmbed("error");
            errorEmbed.setDescription("У вас недостаточно прав. Необходимые права: "
                    + DataFormatter.getMissingPermissions(permission));
            msg_event.getChannel().sendMessage(errorEmbed.build()).queue();
            return false;
        }
    }

    public static boolean handleAccessLevel(MessageReceivedEvent msg_event, short needAccessLevel, User user)
            throws Exception {

        SupportServer supportServer = new SupportServer(msg_event.getJDA());
        // уровень доступа 0 - команда доступна всем(не учитываются права на сервере, для них отдельная проверка)
        // уровень доступа 1 - необходимо быть на сервере поддержки
        // уровень доступа 2 - нужно быть тестером бота
        // уровень доступа 3 - нужно иметь роль поддержки бота
        // уровень доступа 4 - нужно быть разработчиком бота
        // уровень доступа 5 - нужно быть создаетелем бота

        if (needAccessLevel == 0) {
            return true;
        } else if (needAccessLevel == 1) {
            return supportServer.isMember(user);
        } else if (needAccessLevel == 2) {
            return supportServer.isTester(user);
        } else if (needAccessLevel == 3) {
            return supportServer.isSupport(user);
        } else if (needAccessLevel == 4) {
            return supportServer.isDeveloper(user);
        } else if (needAccessLevel == 5) {
            return supportServer.isOwner(user);
        } else {
            throw new Exception("Несуществующий уровень доступа");
        }
    }
}
