package events.guild.roles;

import api.BasicEmbed;
import api.utils.GetLogChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.role.RoleCreateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class RoleCreate extends ListenerAdapter {

    @Override
    public void onRoleCreate(RoleCreateEvent createEvent) {
        TextChannel logChannel = GetLogChannel.getChannel(createEvent.getGuild(), "roleCreate");
        if (logChannel != null) {
            BasicEmbed logEmbed = new BasicEmbed("info");
            logEmbed.setTitle("Создана новая роль");
            logEmbed.setColor(createEvent.getRole().getColorRaw());
            logEmbed.addField("Имя", createEvent.getRole().getName());
            logEmbed.addField("Цвет", Integer.toHexString(createEvent.getRole().getColorRaw()));
            logChannel.sendMessage(logEmbed.build()).queue();
        }
    }

}
