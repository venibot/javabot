package commands.moderation;

import api.BasicEmbed;
import api.models.command.Command;
import api.models.command.DiscordCommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.concurrent.TimeUnit;

@DiscordCommand(name = "clear", description = "Очистка чата", group = "Модерация", aliases = {"очистить", "purge"},
        arguments = 1, permissions = {Permission.MESSAGE_MANAGE})
public class ClearCommand implements Command {

    @Override
    public void doCommand(MessageReceivedEvent msg_event, String[] arguments) {

        if (arguments.length != 0) {
            try {

                msg_event.getChannel().getHistory().retrievePast(Integer.parseInt(arguments[0]) + 1)
                        .queue(messages -> {
                            msg_event.getChannel().purgeMessages(messages);
                            BasicEmbed successEmbed = new BasicEmbed("success");
                            successEmbed.setDescription("Чат успешно очищен на " + arguments[0] + " сообщений");
                            msg_event.getChannel().sendMessage(successEmbed.build())
                                    .queue(message -> message.delete().queueAfter(10, TimeUnit.SECONDS));
                        });

            } catch (NumberFormatException e) {
                BasicEmbed errorEmbed = new BasicEmbed("error");
                errorEmbed.setDescription("Я не умею очищать чат, если вы указали буквы вместо количества сообщений");
                msg_event.getChannel().sendMessage(errorEmbed.build()).queue();
            }
        } else {
            BasicEmbed errorEmbed = new BasicEmbed("error");
            errorEmbed.setDescription("Укажите количество сообщений, которое надо очистить");
            msg_event.getChannel().sendMessage(errorEmbed.build()).queue();
        }
    }
}
