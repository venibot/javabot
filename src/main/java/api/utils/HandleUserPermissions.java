package api.utils;

import api.BasicEmbed;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public class HandleUserPermissions {

    public static boolean handlePermissions(MessageReceivedEvent msg_event, Permission permission) {
        if (msg_event.getMember().getPermissionsExplicit(msg_event.getTextChannel()).contains(permission) || msg_event.getMember().isOwner()) {
            return true;
        }
        else {
            BasicEmbed errorEmbed = new BasicEmbed("error");
            errorEmbed.setDescription("У вас недостаточно прав. Необходимые права: " + DataFormatter.getMissingPermissions(permission));
            msg_event.getChannel().sendMessage(errorEmbed.build()).queue();
            return false;
        }
    }

}
