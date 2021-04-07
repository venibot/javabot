package events.guild.roles;

import api.BasicEmbed;
import api.utils.GetLogChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.role.update.RoleUpdateNameEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class RoleUpdateName extends ListenerAdapter {

    @Override
    public void onRoleUpdateName(RoleUpdateNameEvent updateNameEvent) {
        TextChannel logChannel = GetLogChannel.getChannel(updateNameEvent.getGuild(), "roleUpdate");

        if (logChannel != null) {
            BasicEmbed logEmbed = new BasicEmbed("info");
            logEmbed.setTitle("Изменено имя роли");
            logEmbed.setColor(updateNameEvent.getRole().getColor());

            logEmbed.addField("Роль", updateNameEvent.getRole().getName()
                    + "(" + updateNameEvent.getRole().getAsMention() + ")");

            logEmbed.addField("Старое имя", updateNameEvent.getOldName());

            logChannel.sendMessage(logEmbed.build()).queue();
        }
    }
}
