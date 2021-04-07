package events.guild.members.ban;

import api.BasicEmbed;
import api.utils.GetLogChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.GuildBanEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildBan extends ListenerAdapter {

    @Override
    public void onGuildBan(GuildBanEvent banEvent) {
        TextChannel logChannel = GetLogChannel.getChannel(banEvent.getGuild(), "userBan");

        if (logChannel != null) {
            BasicEmbed logEmbed = new BasicEmbed("info");
            logEmbed.setTitle("Пользователь забанен");

            logEmbed.addField("Пользователь", String.format("%s (%s)", banEvent.getUser().getAsTag(), banEvent
                    .getUser().getId()));

            logChannel.sendMessage(logEmbed.build()).queue();
        }
    }
}
