package events.guild.roles;

import api.BasicEmbed;
import api.utils.GetLogChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.role.update.RoleUpdateColorEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class RoleUpdateColor extends ListenerAdapter {

    @Override
    public void onRoleUpdateColor(RoleUpdateColorEvent updateColorEvent) {
        TextChannel logChannel = GetLogChannel.getChannel(updateColorEvent.getGuild(), "roleUpdate");

        if (logChannel != null) {
            BasicEmbed logEmbed = new BasicEmbed("info");
            logEmbed.setTitle("Изменён цвет роли");
            logEmbed.setColor(updateColorEvent.getNewColor());

            logEmbed.addField("Роль", updateColorEvent.getRole().getName()
                    + "(" + updateColorEvent.getRole().getAsMention() + ")");

            logEmbed.addField("Старый цвет", Integer.toHexString(updateColorEvent.getOldColorRaw()));
            logEmbed.addField("Новый цвет", Integer.toHexString(updateColorEvent.getNewColorRaw()));

            logChannel.sendMessage(logEmbed.build()).queue();
        }
    }
}
