package events.guild;

import api.BasicEmbed;
import api.utils.GetLogChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateDescriptionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildUpdateDescription extends ListenerAdapter {

    @Override
    public void onGuildUpdateDescription(GuildUpdateDescriptionEvent updateDescriptionEvent) {
        TextChannel logChannel = GetLogChannel.getChannel(updateDescriptionEvent.getGuild(), "guildUpdate");
        if (logChannel != null) {
            BasicEmbed logEmbed = new BasicEmbed("info");
            logEmbed.setTitle("Изменено описание сервера");
            logEmbed.addField("Старое описание",
                    updateDescriptionEvent.getOldDescription() != null
                            ? updateDescriptionEvent.getOldDescription()
                            : "Отсутствует");
            logEmbed.addField("Новое описание",
                    updateDescriptionEvent.getOldDescription() != null
                            ? updateDescriptionEvent.getOldDescription()
                            : "Отсутствует");
            logChannel.sendMessage(logEmbed.build()).queue();
        }
    }

}
