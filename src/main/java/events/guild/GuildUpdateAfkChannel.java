package events.guild;

import api.BasicEmbed;
import api.utils.GetLogChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateAfkChannelEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildUpdateAfkChannel extends ListenerAdapter {

    @Override
    public void onGuildUpdateAfkChannel(GuildUpdateAfkChannelEvent updateAfkChannelEvent) {
        TextChannel logChannel = GetLogChannel.getChannel(updateAfkChannelEvent.getGuild(), "guildUpdate");

        if (logChannel != null) {
            BasicEmbed logEmbed = new BasicEmbed("info");
            logEmbed.setTitle("Изменён АФК канал сервера");

            logEmbed.addField("Старый канал",
                    updateAfkChannelEvent.getOldAfkChannel() != null
                            ? updateAfkChannelEvent.getOldAfkChannel().getName()
                            : "Отсутствует");

            logEmbed.addField("Новый канал",
                    updateAfkChannelEvent.getNewAfkChannel() != null
                            ? updateAfkChannelEvent.getNewAfkChannel().getName()
                            : "Отсутствует");

            logChannel.sendMessage(logEmbed.build()).queue();
        }
    }
}
