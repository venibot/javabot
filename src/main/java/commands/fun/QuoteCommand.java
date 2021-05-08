package commands.fun;

import api.BasicEmbed;
import api.models.command.Command;
import api.models.command.CommandContext;
import api.models.command.DiscordCommand;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.List;
import java.util.Random;

@DiscordCommand(name = "quote", description = "Вывод случайного сообщения из закреплённых", aliases = {"citation", "цитата"},
        group = "Фановое")
public class QuoteCommand implements Command {

    @Override
    public void doCommand(CommandContext context, String[] arguments) {
        List<Message> pinnedMessages = context.getChannel().retrievePinnedMessages().complete();
        if (pinnedMessages.size() != 0) {
            Message message = pinnedMessages.get(new Random().nextInt(pinnedMessages.size()));
            BasicEmbed infoEmbed = new BasicEmbed("info");
            infoEmbed.setTitle(":copyright: Цитаты великих людей");
            infoEmbed.setDescription(message.getContentRaw());
            infoEmbed.setAuthor(message.getAuthor().getAsTag());
            if (message.getAttachments().size() != 0) {
                infoEmbed.setImage(message.getAttachments().get(0).getUrl());
                if (message.getAttachments().size() > 1) {
                    infoEmbed.appendDescription("\n\nP.S. К [сообщению](" + message.getJumpUrl() + ") прилагается ещё " + (message.getAttachments().size() - 1) + " изображений");
                }
            }
            context.sendMessage(infoEmbed).queue();
        } else {
            for (TextChannel channel: context.getGuild().getTextChannels()) {
                pinnedMessages = channel.retrievePinnedMessages().complete();
                if (pinnedMessages.size() != 0) {
                    Message message = pinnedMessages.get(new Random().nextInt(pinnedMessages.size()));
                    BasicEmbed infoEmbed = new BasicEmbed("info");
                    infoEmbed.setTitle(":copyright: Цитаты великих людей");
                    infoEmbed.setDescription(message.getContentRaw());
                    infoEmbed.setAuthor(message.getAuthor().getAsTag());
                    if (message.getAttachments().size() != 0) {
                        infoEmbed.setImage(message.getAttachments().get(0).getUrl());
                        if (message.getAttachments().size() > 1) {
                            infoEmbed.appendDescription("\n\nP.S. К [сообщению](" + message.getJumpUrl() + ") прилагается ещё " + (message.getAttachments().size() - 1) + " изображений");
                        }
                    }
                    context.sendMessage(infoEmbed).queue();
                    return;
                }
            }
            BasicEmbed errorEmbed = new BasicEmbed("error", "На этом сервере нет великих людей");
            context.sendMessage(errorEmbed).queue();
        }
    }
}
