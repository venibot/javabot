package events.message;

import api.BasicEmbed;
import api.Database;
import api.models.command.Command;
import api.models.command.CommandContext;
import api.models.command.CommandHandler;
import api.models.database.Guild;
import api.utils.*;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageUpdate extends ListenerAdapter {

    @Override
    public void onGuildMessageUpdate(GuildMessageUpdateEvent updateEvent) {
        if (updateEvent.getAuthor().isBot()) {
            return;
        }

        if (updateEvent.getMessage().getContentRaw().equals(updateEvent.getJDA().getSelfUser().getAsMention())) {
            Database db = new Database();

            String prefix = db.getGuildByID(updateEvent.getGuild().getIdLong()).getPrefix();
            updateEvent.getChannel().sendMessage("Мой префикс на этом сервере: `" + prefix
                    + "`\nСписок команд можно получить при помощи `" + prefix + "хелп`").queue();
        } else {

            if (updateEvent.getMessage().getContentRaw().startsWith(updateEvent.getJDA().getSelfUser().getAsMention())) {
                String truncated = updateEvent.getMessage().getContentRaw().replaceFirst(updateEvent.getJDA()
                        .getSelfUser().getAsMention(), "").trim();

                String command_name = truncated.split(" ")[0];
                Command command = CommandHandler.findCommand(command_name);

                if (command != null) {
                    Database db = new Database();
                    MessageReceivedEvent msg_event = new MessageReceivedEvent(updateEvent.getJDA(),
                            updateEvent.getResponseNumber(), updateEvent.getMessage());
                    CommandContext context = new CommandContext(msg_event, db.getGuildByID(updateEvent.getGuild().getIdLong()),
                            updateEvent.getJDA().getSelfUser().getAsMention());
                    CommandHandler.findAndRun(command_name, context,
                            truncated.replaceFirst(command_name, ""));
                    Config.COMMANDS_COMPLETED += 1;
                }
            } else {
                Database db = new Database();
                Guild guild = db.getGuildByID(updateEvent.getGuild().getIdLong());

                if (updateEvent.getMessage().getContentRaw().startsWith(guild.getPrefix()) && !updateEvent.getAuthor().isBot()) {
                    String truncated = updateEvent.getMessage().getContentRaw().replaceFirst(guild.getPrefix(), "")
                            .trim();
                    String command_name = truncated.split(" ")[0];
                    Command command = CommandHandler.findCommand(command_name);

                    if (command != null) {
                        MessageReceivedEvent msg_event = new MessageReceivedEvent(updateEvent.getJDA(),
                                updateEvent.getResponseNumber(), updateEvent.getMessage());
                        CommandContext context = new CommandContext(msg_event, guild, guild.getPrefix());
                        CommandHandler.findAndRun(command_name, context,
                                truncated.replaceFirst(command_name, ""));
                        Config.COMMANDS_COMPLETED += 1;
                    }
                }
            }

            TextChannel logChannel = GetLogChannel.getChannel(updateEvent.getGuild(), "messageEdit");

            if (logChannel != null && Config.MESSAGE_CACHE.containsKey(updateEvent.getMessageIdLong())) {
                BasicEmbed logEmbed = new BasicEmbed("info");
                logEmbed.setTitle("Сообщение изменено");

                logEmbed.addField("Сообщение до",
                        Config.MESSAGE_CACHE.get(updateEvent.getMessageIdLong()).getContentRaw());

                logEmbed.addField("Сообщение после", updateEvent.getMessage().getContentRaw());
                logEmbed.addField("Автор сообщения", updateEvent.getMessage().getAuthor().getAsTag());

                logEmbed.addField("Ссылка на сообщение", "[Перейти]("
                        + updateEvent.getMessage().getJumpUrl() + ")");

                Config.MESSAGE_CACHE.remove(updateEvent.getMessageIdLong());
                Config.MESSAGE_CACHE.put(updateEvent.getMessageIdLong(), updateEvent.getMessage());

                logChannel.sendMessage(logEmbed.build()).queue();
            }
        }
    }
}
