package commands.moderation;

import api.BasicEmbed;
import api.models.command.Command;
import api.models.command.CommandContext;
import api.models.command.DiscordCommand;
import net.dv8tion.jda.api.Permission;

import java.util.concurrent.TimeUnit;

@DiscordCommand(name = "clear", description = "Очистка чата", group = "Модерация", aliases = {"очистить", "purge"},
        arguments = 1, permissions = {Permission.MESSAGE_MANAGE})
public class ClearCommand implements Command {

    @Override
    public void doCommand(CommandContext context, String[] arguments) {

        if (arguments.length != 0) {
            try {

                context.getChannel().getHistory().retrievePast(Integer.parseInt(arguments[0]) + 1)
                        .queue(messages -> {
                            context.getChannel().purgeMessages(messages);
                            BasicEmbed successEmbed = new BasicEmbed("success");
                            successEmbed.setDescription("Чат успешно очищен на " + arguments[0] + " сообщений");
                            context.sendMessage(successEmbed)
                                    .queue(message -> message.delete().queueAfter(10, TimeUnit.SECONDS));
                        });

            } catch (NumberFormatException e) {
                BasicEmbed errorEmbed = new BasicEmbed("error", "Я не умею очищать чат, если вы указали буквы вместо количества сообщений");
                context.sendMessage(errorEmbed).queue();
            }
        } else {
            BasicEmbed errorEmbed = new BasicEmbed("error", "Укажите количество сообщений, которое надо очистить");
            context.sendMessage(errorEmbed).queue();
        }
    }
}
