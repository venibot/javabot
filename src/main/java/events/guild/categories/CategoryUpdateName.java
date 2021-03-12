package events.guild.categories;

import api.BasicEmbed;
import api.utils.GetLogChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.channel.category.update.CategoryUpdateNameEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CategoryUpdateName extends ListenerAdapter {

    @Override
    public void onCategoryUpdateName(CategoryUpdateNameEvent updateNameEvent) {
        TextChannel logChannel = GetLogChannel.getChannel(updateNameEvent.getGuild(), "categoryUpdate");
        if (logChannel != null) {
            BasicEmbed logEmbed = new BasicEmbed("info");
            logEmbed.setTitle("Изменено имя категории");
            logEmbed.addField("Старое название", updateNameEvent.getOldName());
            logEmbed.addField("Новое название", updateNameEvent.getNewName());
            logChannel.sendMessage(logEmbed.build()).queue();
        }
    }

}
