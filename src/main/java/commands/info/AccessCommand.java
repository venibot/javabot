package commands.info;

import api.BasicEmbed;
import api.models.command.Command;
import api.models.command.DiscordCommand;
import api.models.exceptions.MemberNotFoundException;
import api.models.exceptions.RoleNotFoundException;
import api.utils.Converters;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@DiscordCommand(name = "access", description = "Узнать какие каналы может видеть пользователь или роль", usage = "[пользователь | Роль]",
        aliases = {"доступ"}, group = "Информация", arguments = 1)
public class AccessCommand implements Command {

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
            infoEmbed.setTitle("Каналы, которые видит пользователь " + member.getEffectiveName());
            String channels = "";
            for (GuildChannel channel: msg_event.getGuild().getChannels()) {
                if (member.hasPermission(channel, Permission.VIEW_CHANNEL)) {
                    if (channel.getType() == ChannelType.TEXT) {
                        channels += "<#" + channel.getId() + ">\n";
                    } else if (channel.getType() == ChannelType.CATEGORY) {
                        channels += "Категория " + channel.getName() + "\n";
                    } else {
                        channels += channel.getName() + "\n";
                    }
                }
            }
            infoEmbed.setDescription(channels);
        } else {
            infoEmbed.setTitle("Каналы, которые видит роль " + role.getName());
            String channels = "";
            for (GuildChannel channel: msg_event.getGuild().getChannels()) {
                if (role.hasPermission(channel, Permission.VIEW_CHANNEL)) {
                    if (channel.getType() == ChannelType.TEXT) {
                        channels += "<#" + channel.getId() + ">\n";
                    } else if (channel.getType() == ChannelType.CATEGORY) {
                        channels += "Категория " + channel.getName() + "\n";
                    } else {
                        channels += channel.getName() + "\n";
                    }
                }
            }
            infoEmbed.setDescription(channels);
        }

        msg_event.getChannel().sendMessage(infoEmbed.build()).queue();
    }

}
