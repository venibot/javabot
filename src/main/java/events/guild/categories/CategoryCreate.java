package events.guild.categories;

import api.BasicEmbed;
import api.utils.GetLogChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.channel.category.CategoryCreateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CategoryCreate extends ListenerAdapter {

    @Override
    public void onCategoryCreate(CategoryCreateEvent createEvent) {
        TextChannel logChannel = GetLogChannel.getChannel(createEvent.getGuild(), "categoryCreate");
        if (logChannel != null) {
            BasicEmbed logEmbed = new BasicEmbed("info");
            logEmbed.setTitle("Создана новая категория");
            logEmbed.addField("Название", createEvent.getCategory().getName());
            logChannel.sendMessage(logEmbed.build()).queue();
        }
    }

}
