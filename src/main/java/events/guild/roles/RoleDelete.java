package events.guild.roles;

import api.BasicEmbed;
import api.utils.GetLogChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.role.RoleDeleteEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class RoleDelete extends ListenerAdapter {

    @Override
    public void onRoleDelete(RoleDeleteEvent deleteEvent) {
        TextChannel logChannel = GetLogChannel.getChannel(deleteEvent.getGuild(), "roleDelete");
        if (logChannel != null) {
            BasicEmbed logEmbed = new BasicEmbed("info");
            logEmbed.setTitle("Удалена роль");
            logEmbed.addField("Имя", deleteEvent.getRole().getName());
            logEmbed.addField("Цвет", Integer.toHexString(deleteEvent.getRole().getColorRaw()));
            logChannel.sendMessage(logEmbed.build()).queue();
        }
    }

}
