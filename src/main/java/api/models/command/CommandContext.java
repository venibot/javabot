package api.models.command;

import api.Database;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandContext {

    private final MessageReceivedEvent msg_event;

    private final String usedPrefix;

    private api.models.database.Guild guild;

    private Command command;

    public CommandContext(MessageReceivedEvent msg_event, api.models.database.Guild dbGuild, String usedPrefix) {
        this.msg_event = msg_event;
        this.guild = dbGuild;
        this.usedPrefix = usedPrefix;
    }

    public Message getMessage() {
        return msg_event.getMessage();
    }

    public MessageChannel getChannel() {
        return msg_event.getChannel();
    }

    public Guild getGuild() {
        return msg_event.getGuild();
    }

    public api.models.database.Guild getDatabaseGuild() {
        return guild;
    }

    public Member getAuthor() {
        return msg_event.getMember();
    }

    public JDA getJDA() {
        return msg_event.getJDA();
    }

    public Database getDatabase() {
        return new Database();
    }

    public String getUsedPrefix() {
        return usedPrefix;
    }

    public String getLocale() {
        return "ru";
        // заделка на будущее
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public net.dv8tion.jda.api.requests.restaction.MessageAction sendMessage(EmbedBuilder embedBuilder) {
        return this.getMessage().reply(embedBuilder.build());
    }

    public net.dv8tion.jda.api.requests.restaction.MessageAction sendMessage(CharSequence text) {
        return this.getMessage().reply(text);
    }
}
