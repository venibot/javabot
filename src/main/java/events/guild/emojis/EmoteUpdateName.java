package events.guild.emojis;

import api.BasicEmbed;
import api.utils.GetLogChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.emote.update.EmoteUpdateNameEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class EmoteUpdateName extends ListenerAdapter {

    @Override
    public void onEmoteUpdateName(EmoteUpdateNameEvent updateNameEvent) {
        TextChannel logChannel = GetLogChannel.getChannel(updateNameEvent.getGuild(), "emoteUpdate");
        if (logChannel != null) {
            BasicEmbed logEmbed = new BasicEmbed("info");
            logEmbed.setTitle("Изменено имя эмодзи");
            logEmbed.addField("Эмодзи", String.format("%s(%s)",
                    updateNameEvent.getEmote().getAsMention(), updateNameEvent.getEmote().getName()));
            logEmbed.addField("Старое имя", updateNameEvent.getOldName());
            logEmbed.setThumbnail(updateNameEvent.getEmote().getImageUrl());
            logChannel.sendMessage(logEmbed.build()).queue();
        }
    }

}
