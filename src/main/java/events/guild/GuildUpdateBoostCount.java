package events.guild;

import api.BasicEmbed;
import api.utils.GetLogChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateBoostCountEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildUpdateBoostCount extends ListenerAdapter {

    @Override
    public void onGuildUpdateBoostCount(GuildUpdateBoostCountEvent updateBoostCountEvent) {
        TextChannel logChannel = GetLogChannel.getChannel(updateBoostCountEvent.getGuild(), "guildUpdate");

        if (logChannel != null) {
            BasicEmbed logEmbed = new BasicEmbed("info");
            logEmbed.setTitle("Изменено количество бустов сервера");

            logEmbed.addField("Старое количество бустов",
                    String.valueOf(updateBoostCountEvent.getOldBoostCount()));

            logEmbed.addField("Новое количество бустов",
                    String.valueOf(updateBoostCountEvent.getNewBoostCount()));

            logChannel.sendMessage(logEmbed.build()).queue();
        }
    }
}
