package commands.moderation;

import api.BasicEmbed;
import api.models.command.Command;
import api.models.command.CommandContext;
import api.models.command.DiscordCommand;
import api.models.exceptions.UserNotFoundException;
import api.utils.Converters;
import api.utils.Functions;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@DiscordCommand(name = "clear", description = "Очистка чата", group = "Модерация", aliases = {"очистить", "purge"},
        usage = "<Количество сообщений> [Пользователь]", arguments = 2, permissions = {Permission.MESSAGE_MANAGE})
public class ClearCommand implements Command {

    @Override
    public void doCommand(CommandContext context, String[] arguments) {

        if (arguments.length != 0) {
            try {
                if (arguments.length == 1) {
                    context.getChannel().getHistory().retrievePast(Integer.parseInt(arguments[0]) + 1)
                            .queue(messages -> {
                                context.getChannel().purgeMessages(messages);
                                BasicEmbed successEmbed = new BasicEmbed("success");
                                successEmbed.setDescription("Чат успешно очищен на " + (messages.size() - 1) + " сообщений");
                                context.getChannel().sendMessage(successEmbed.build())
                                        .queue(message -> message.delete().queueAfter(10, TimeUnit.SECONDS));
                            });
                } else {
                    final User user = Converters.getUser(context.getJDA(), arguments[1]);
                    context.getChannel().getHistory().retrievePast(Integer.parseInt(arguments[0]) + 1)
                            .queue(allMessages -> {
                                List<Message> messages = new ArrayList<>();
                                for (Message message: allMessages) {
                                    if (message.getAuthor().getIdLong() == user.getIdLong()) {
                                        messages.add(message);
                                    }
                                }
                                context.getChannel().purgeMessages(messages);
                                BasicEmbed successEmbed = new BasicEmbed("success");
                                successEmbed.setDescription("Чат успешно очищен на " + (messages.size() - 1) + " сообщений");
                                context.getChannel().sendMessage(successEmbed.build())
                                        .queue(message -> message.delete().queueAfter(10, TimeUnit.SECONDS));
                            });
                }

            } catch (NumberFormatException e) {
                BasicEmbed errorEmbed = new BasicEmbed("error", "Я не умею очищать чат, если вы указали буквы вместо количества сообщений");
                context.sendMessage(errorEmbed).queue();
            } catch (UserNotFoundException e) {
                BasicEmbed errorEmbed = new BasicEmbed("error", "Указанный пользователь не найден");
                context.sendMessage(errorEmbed).queue();
            }
        } else {
            BasicEmbed errorEmbed = new BasicEmbed("error", "Укажите количество сообщений, которое надо очистить");
            context.sendMessage(errorEmbed).queue();
        }
    }
}
