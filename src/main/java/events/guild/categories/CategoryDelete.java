package events.guild.categories;

import api.BasicEmbed;
import api.utils.GetLogChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.channel.category.CategoryDeleteEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CategoryDelete extends ListenerAdapter {

    @Override
    public void onCategoryDelete(CategoryDeleteEvent deleteEvent) {
        TextChannel logChannel = GetLogChannel.getChannel(deleteEvent.getGuild(), "categoryDelete");

        if (logChannel != null) {
            BasicEmbed logEmbed = new BasicEmbed("info");
            logEmbed.setTitle("Удалена категория");

            logEmbed.addField("Название", deleteEvent.getCategory().getName());
            logChannel.sendMessage(logEmbed.build()).queue();
        }
    }
}
