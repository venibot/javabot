package events.message;

import api.Database;
import api.models.command.Command;
import api.models.command.CommandContext;
import api.models.command.CommandHandler;
import api.models.database.Guild;
import api.utils.Config;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageReceived extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent msg_event) {
        if (msg_event.getAuthor().isBot()) {
            return;
        }

        if (msg_event.getMessage().getContentRaw().equals(msg_event.getJDA().getSelfUser().getAsMention())) {
            Database db = new Database();
            String prefix = db.getGuildByID(msg_event.getGuild().getIdLong()).getPrefix();

            msg_event.getChannel().sendMessage("Мой префикс на этом сервере: `" + prefix
                    + "`\nСписок команд можно получить при помощи `" + prefix + "хелп`").queue();
        } else {

            if (msg_event.getMessage().getContentRaw().startsWith(msg_event.getJDA().getSelfUser().getAsMention())) {
                String truncated = msg_event.getMessage().getContentRaw().replaceFirst(msg_event.getJDA()
                        .getSelfUser().getAsMention(), "").trim();

                String command_name = truncated.split(" ")[0];
                Command command = CommandHandler.findCommand(command_name);

                if (command != null) {
                    Database db = new Database();
                    CommandContext context = new CommandContext(msg_event, db.getGuildByID(msg_event.getGuild().getIdLong()),
                            msg_event.getJDA().getSelfUser().getAsMention());
                    CommandHandler.findAndRun(command_name, context,
                            truncated.replaceFirst(command_name, ""));
                    Config.COMMANDS_COMPLETED += 1;
                }
            } else {
                Database db = new Database();
                Guild guild = db.getGuildByID(msg_event.getGuild().getIdLong());

                if (msg_event.getMessage().getContentRaw().startsWith(guild.getPrefix()) && !msg_event.getAuthor().isBot()) {
                    String truncated = msg_event.getMessage().getContentRaw().replaceFirst(guild.getPrefix(), "")
                            .trim();
                    String command_name = truncated.split(" ")[0];
                    Command command = CommandHandler.findCommand(command_name);

                    if (command != null) {
                        CommandContext context = new CommandContext(msg_event, guild, guild.getPrefix());
                        CommandHandler.findAndRun(command_name, context,
                                truncated.replaceFirst(command_name, ""));
                        Config.COMMANDS_COMPLETED += 1;
                    }
                }
            }

            Config.MESSAGE_CACHE.put(msg_event.getMessageIdLong(), msg_event.getMessage());
        }
    }
}
