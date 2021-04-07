package events.guild;

import api.BasicEmbed;
import api.utils.GetLogChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateIconEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildUpdateIcon extends ListenerAdapter {

    @Override
    public void onGuildUpdateIcon(GuildUpdateIconEvent updateIconEvent) {
        TextChannel logChannel = GetLogChannel.getChannel(updateIconEvent.getGuild(), "guildUpdate");

        if (logChannel != null) {
            BasicEmbed logEmbed = new BasicEmbed("info");
            logEmbed.setTitle("Изменена иконка сервера");
            logEmbed.addField("Старая иконка", updateIconEvent.getOldIconUrl());
            logEmbed.addField("Новая иконка", updateIconEvent.getNewIconUrl());

            logChannel.sendMessage(logEmbed.build()).queue();
        }
    }
}
