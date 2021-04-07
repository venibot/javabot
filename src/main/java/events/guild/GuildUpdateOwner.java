package events.guild;

import api.BasicEmbed;
import api.utils.GetLogChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateOwnerEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildUpdateOwner extends ListenerAdapter {

    @Override
    public void onGuildUpdateOwner(GuildUpdateOwnerEvent updateOwnerEvent) {
        TextChannel logChannel = GetLogChannel.getChannel(updateOwnerEvent.getGuild(), "guildUpdate");

        if (logChannel != null) {
            BasicEmbed logEmbed = new BasicEmbed("info");
            logEmbed.setTitle("Изменён владелец сервера");

            logEmbed.addField("Старый владелец", String.format("%s(%s)",
                    updateOwnerEvent.getOldOwner().getAsMention(),
                    updateOwnerEvent.getOldOwner().getUser().getAsTag()));

            logEmbed.addField("Новый владелец", String.format("%s(%s)",
                    updateOwnerEvent.getNewOwner().getAsMention(),
                        updateOwnerEvent.getNewOwner().getUser().getAsTag()));

            logChannel.sendMessage(logEmbed.build()).queue();
        }
    }
}
