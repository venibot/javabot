package commands.info;

import api.BasicEmbed;
import api.models.command.Command;
import api.models.command.DiscordCommand;
import api.models.exceptions.MemberNotFoundException;
import api.models.exceptions.RoleNotFoundException;
import api.utils.Converters;
import api.utils.DataFormatter;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@DiscordCommand(name = "permissions", description = "Узнать права пользователя или роли", usage = "[пользователь | Роль]",
                aliases = {"perms", "права"}, group = "Информация", arguments = 1)
public class PermissionsCommand implements Command {

    @Override
    public void doCommand(MessageReceivedEvent msg_event, String[] arguments) {
        Member member = null;
        Role role = null;
        if (arguments.length == 0) {
            member = msg_event.getMember();
        } else {
            try {
                member = Converters.getMember(msg_event.getGuild(), arguments[0]);
            } catch (MemberNotFoundException e) {
                try {
                    role = Converters.getRole(msg_event.getGuild(), arguments[0]);
                } catch (RoleNotFoundException exception) {
                    BasicEmbed errorEmbed = new BasicEmbed("error", "Указанный поьзователь/роль не найдена");
                    msg_event.getChannel().sendMessage(errorEmbed.build()).queue();
                    return;
                }
            }
        }

        BasicEmbed infoEmbed = new BasicEmbed("info");
        if (member != null) {
            infoEmbed.setTitle("Права, которыми обладает пользователь " + member.getEffectiveName());
            infoEmbed.setDescription(DataFormatter.getMissingPermissions(member.getPermissions().toArray(new Permission[0])));
        } else {
            infoEmbed.setTitle("Права, которыми обладает роль " + role.getName());
            infoEmbed.setDescription(DataFormatter.getMissingPermissions(role.getPermissions().toArray(new Permission[0])));
        }

        msg_event.getChannel().sendMessage(infoEmbed.build()).queue();
    }
}
