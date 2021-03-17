package events.guild;

import api.BasicEmbed;
import api.utils.GetLogChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateAfkTimeoutEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildUpdateAfkTimeout extends ListenerAdapter {

    @Override
    public void onGuildUpdateAfkTimeout(GuildUpdateAfkTimeoutEvent updateAfkTimeoutEvent) {
        TextChannel logChannel = GetLogChannel.getChannel(updateAfkTimeoutEvent.getGuild(), "guildUpdate");
        if (logChannel != null) {
            BasicEmbed logEmbed = new BasicEmbed("info");
            logEmbed.setTitle("Изменён АФК таймаут сервера");
            logEmbed.addField("Старый таймаут",
                    updateAfkTimeoutEvent.getOldAfkTimeout().getSeconds() != 0
                            ? updateAfkTimeoutEvent.getOldAfkTimeout().getSeconds() + " секунд"
                            : "Отсутствует");
            logEmbed.addField("Новый таймаут",
                    updateAfkTimeoutEvent.getNewAfkTimeout().getSeconds() != 0
                            ? updateAfkTimeoutEvent.getNewAfkTimeout().getSeconds() + " секунд"
                            : "Отсутствует");
            logChannel.sendMessage(logEmbed.build()).queue();
        }
    }

}
