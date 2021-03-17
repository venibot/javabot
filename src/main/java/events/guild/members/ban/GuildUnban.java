package events.guild.members.ban;

import api.BasicEmbed;
import api.utils.GetLogChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.GuildUnbanEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildUnban extends ListenerAdapter {

    @Override
    public void onGuildUnban(GuildUnbanEvent unbanEvent) {
        TextChannel logChannel = GetLogChannel.getChannel(unbanEvent.getGuild(), "userUnban");
        if (logChannel != null) {
            BasicEmbed logEmbed = new BasicEmbed("info");
            logEmbed.setTitle("Пользователь разбанен");
            logEmbed.addField("Пользователь", String.format("%s (%s)", unbanEvent.getUser().getAsTag(), unbanEvent.getUser().getId()));
            logChannel.sendMessage(logEmbed.build()).queue();
        }
    }

}
