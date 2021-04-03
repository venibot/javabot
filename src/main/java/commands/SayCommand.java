package commands;

import api.BasicEmbed;
import api.models.command.Command;
import api.models.command.DiscordCommand;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@DiscordCommand(name = "say", aliases = {"сказать", "скажи"}, arguments = 1, group = "Утилиты")
public class SayCommand implements Command {

    @Override
    public void doCommand(MessageReceivedEvent msg_event, String[] arguments) {
        if (arguments.length != 0) {
            if (msg_event.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_MANAGE)) {
                msg_event.getMessage().delete().queue();
            }
            MessageBuilder builder = new MessageBuilder(String.join(" ", arguments));
            if (!msg_event.getMember().hasPermission(Permission.MESSAGE_MENTION_EVERYONE)) {
                builder.denyMentions(Message.MentionType.EVERYONE, Message.MentionType.HERE, Message.MentionType.ROLE, Message.MentionType.USER);
            }
            msg_event.getChannel().sendMessage(builder.build()).queue();
        } else {
            BasicEmbed errorEmbed = new BasicEmbed("error");
            errorEmbed.setDescription("О, а можете меня научить ничего не говорить по команде?");
            msg_event.getChannel().sendMessage(errorEmbed.build()).queue();
        }
    }

}
