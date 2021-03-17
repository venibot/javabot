package events.guild;

import api.BasicEmbed;
import api.utils.GetLogChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateNameEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildUpdateName extends ListenerAdapter {

    @Override
    public void onGuildUpdateName(GuildUpdateNameEvent updateNameEvent) {
        TextChannel logChannel = GetLogChannel.getChannel(updateNameEvent.getGuild(), "guildUpdate");
        if (logChannel != null) {
            BasicEmbed logEmbed = new BasicEmbed("info");
            logEmbed.setTitle("Изменено название сервера");
            logEmbed.addField("Старое название", updateNameEvent.getOldName());
            logEmbed.addField("Новое название", updateNameEvent.getNewName());
            logChannel.sendMessage(logEmbed.build()).queue();
        }
    }

}
