package events.guild.emojis;

import api.BasicEmbed;
import api.utils.GetLogChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.emote.EmoteRemovedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class EmoteRemoved extends ListenerAdapter {

    @Override
    public void onEmoteRemoved(EmoteRemovedEvent removeEvent) {
        TextChannel logChannel = GetLogChannel.getChannel(removeEvent.getGuild(), "emoteRemove");
        if (logChannel != null) {
            BasicEmbed logEmbed = new BasicEmbed("info");
            logEmbed.setTitle("Удалено эмодзи");
            logEmbed.addField("Эмодзи", removeEvent.getEmote().getName());
            logEmbed.setThumbnail(removeEvent.getEmote().getImageUrl());
            logChannel.sendMessage(logEmbed.build()).queue();
        }
    }

}
