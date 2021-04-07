package events.guild.emojis;

import api.BasicEmbed;
import api.utils.GetLogChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.emote.EmoteAddedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class EmoteAdded extends ListenerAdapter {

    @Override
    public void onEmoteAdded(EmoteAddedEvent addEvent) {
        TextChannel logChannel = GetLogChannel.getChannel(addEvent.getGuild(), "emoteAdd");

        if (logChannel != null) {
            BasicEmbed logEmbed = new BasicEmbed("info");
            logEmbed.setTitle("Создано новое эмодзи");

            logEmbed.addField("Эмодзи", String.format("%s(%s)",
                    addEvent.getEmote().getAsMention(), addEvent.getEmote().getName()));

            logEmbed.setThumbnail(addEvent.getEmote().getImageUrl());
            logChannel.sendMessage(logEmbed.build()).queue();
        }
    }
}
