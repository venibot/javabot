package commands;

import api.BasicEmbed;
import api.models.command.Command;
import api.models.command.CommandContext;
import api.models.command.DiscordCommand;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

import java.util.concurrent.ExecutionException;

@DiscordCommand(name = "say", aliases = {"сказать", "скажи"}, arguments = 1, group = "Утилиты")
public class SayCommand implements Command {

    @Override
    public void doCommand(CommandContext context, String[] arguments) {
        if (arguments.length != 0) {

            if (context.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_MANAGE)) {
                context.getMessage().delete().queue();
            }

            MessageBuilder builder = new MessageBuilder(String.join(" ", arguments));

            if (!context.getAuthor().hasPermission(Permission.MESSAGE_MENTION_EVERYONE)) {
                builder.denyMentions(Message.MentionType.EVERYONE, Message.MentionType.HERE,
                        Message.MentionType.ROLE, Message.MentionType.USER);
            }

            if (context.getMessage().getAttachments().size() != 0) {
                MessageAction action = context.getChannel().sendMessage(builder.build().getContentRaw());
                for (Message.Attachment attachment: context.getMessage().getAttachments()) {
                    try {
                        action.addFile(attachment.retrieveInputStream().get(), attachment.getFileName());
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                }
                action.queue();
            } else {
                context.getChannel().sendMessage(builder.build().getContentRaw()).queue();
            }
        } else {
            if (context.getMessage().getAttachments().size() != 0) {
                if (context.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_MANAGE)) {
                    context.getMessage().delete().queue();
                }
                try {
                    Message.Attachment firstAttachment = context.getMessage().getAttachments().get(0);
                    MessageAction action = context.getChannel().sendFile(firstAttachment.retrieveInputStream().get(), firstAttachment.getFileName());
                    for (Message.Attachment attachment : context.getMessage().getAttachments()) {
                        if (attachment.getIdLong() != firstAttachment.getIdLong()) {
                            action.addFile(attachment.retrieveInputStream().get(), attachment.getFileName());
                        }
                    }
                    action.queue();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            } else {
                BasicEmbed errorEmbed = new BasicEmbed("error", "О, а можете меня научить ничего не говорить по команде?");
                context.sendMessage(errorEmbed).queue();
            }
        }
    }
}
