package events.guild.channels.text;

import api.BasicEmbed;
import api.utils.GetLogChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.channel.text.update.TextChannelUpdateSlowmodeEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class TextChannelUpdateSlowmode extends ListenerAdapter {

    public void onTextChannelUpdateSlowmode(TextChannelUpdateSlowmodeEvent updateSlowmodeEvent) {
        TextChannel logChannel = GetLogChannel.getChannel(updateSlowmodeEvent.getGuild(), "channelUpdate");

        if (logChannel != null) {
            BasicEmbed logEmbed = new BasicEmbed("info");
            logEmbed.setTitle("Изменён слоумод канала");

            logEmbed.addField("Канал", String.format("%s(%s)",
                    updateSlowmodeEvent.getChannel().getAsMention(), updateSlowmodeEvent.getChannel().getName()));

            logEmbed.addField("Старый слоумод", updateSlowmodeEvent.getOldSlowmode() + " секунд");
            logEmbed.addField("Новый слоумод", updateSlowmodeEvent.getNewSlowmode() + " секунд");

            logChannel.sendMessage(logEmbed.build()).queue();
        }
    }
}
