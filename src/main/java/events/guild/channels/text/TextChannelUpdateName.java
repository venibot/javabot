package events.guild.channels.text;

import api.BasicEmbed;
import api.utils.GetLogChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.channel.text.update.TextChannelUpdateNameEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class TextChannelUpdateName extends ListenerAdapter {

    @Override
    public void onTextChannelUpdateName(TextChannelUpdateNameEvent updateNameEvent) {
        TextChannel logChannel = GetLogChannel.getChannel(updateNameEvent.getGuild(), "channelUpdate");
        if (logChannel != null) {
            BasicEmbed logEmbed = new BasicEmbed("info");
            logEmbed.setTitle("Изменено имя текстового канала");
            logEmbed.addField("Канал", String.format("%s(%s)",
                    updateNameEvent.getChannel().getAsMention(), updateNameEvent.getChannel().getName()));
            logEmbed.addField("Старое имя", updateNameEvent.getOldName());
            logChannel.sendMessage(logEmbed.build()).queue();
        }
    }

}
