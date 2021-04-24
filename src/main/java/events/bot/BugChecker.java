package events.bot;

import api.Database;
import api.models.database.Bug;
import api.models.database.Idea;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Date;

public class BugChecker extends ListenerAdapter {

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        if (event.getChannel().getIdLong() == 767158388139360326L && !event.getUser().isBot()) {
            try {
                if (event.getReaction().getReactionEmote().getEmote().getIdLong() == 835140564885569567L) {
                    // Баг принят
                    Database db = new Database();
                    Bug bug = db.getBugByPrivateMessage(event.getMessageIdLong());
                    bug.setStatus("accept");
                    bug.setAcceptTime(new Date().getTime());
                    TextChannel channel = event.getJDA().getTextChannelById(835471464345632808L);
                    channel.sendMessage("Баг #" + bug.getBugID()
                            + "\nПользователь, обнаруживший баг: " + event.getJDA().getUserById(bug.getUserID()).getAsTag()
                            + "\nОписание бага: " + bug.getText()
                            + "\nСтатус: Обнаружен :orange_circle:"
                            + "\nБаг подтвердил " + event.getUser().getAsMention() + "(" + event.getUser().getAsTag() + ")"
                    ).queue(message -> {
                        bug.setPublicMessage(message.getIdLong());
                        db.updateBug(bug);
                    });
                    event.getReaction().clearReactions().queue();
                    event.getTextChannel().retrieveMessageById(event.getMessageIdLong()).queue(message -> {
                        // Удаление реакции отклонения и бана
                        message.removeReaction(event.getGuild().getEmoteById(835141052876324904L)).queue();
                        message.removeReaction(event.getGuild().getEmoteById(835151191951409173L)).queue();
                    });
                } else if (event.getReaction().getReactionEmote().getEmote().getIdLong() == 835141052876324904L) {
                    // Баг отклонён
                    Database db = new Database();
                    Bug bug = db.getBugByPrivateMessage(event.getMessageIdLong());
                    bug.setStatus("disagree");
                    bug.setDisagreeTime(new Date().getTime());
                    event.getReaction().clearReactions().queue();
                    event.getTextChannel().retrieveMessageById(event.getMessageIdLong()).queue(message -> {
                        // Удаление реакции принятия
                        message.removeReaction(event.getGuild().getEmoteById(835140564885569567L)).queue();
                    });
                    db.updateBug(bug);
                }
            } catch (IllegalStateException e) {
                if (event.getReaction().getReactionEmote().getEmoji().equals("\uD83D\uDFE2")) {
                    // Баг исправлен
                    Database db = new Database();
                    Bug bug = db.getBugByPrivateMessage(event.getMessageIdLong());
                    bug.setStatus("did");
                    bug.setDidTime(new Date().getTime());
                    event.getTextChannel().retrieveMessageById(event.getMessageIdLong()).queue(message -> {
                        message.removeReaction("\uD83D\uDFE2").queue();
                    });
                    event.getGuild().getTextChannelById(835471464345632808L).retrieveMessageById(bug.getPublicMessage()).queue(message -> {
                        message.editMessage(message.getContentRaw()
                                .replace("Обнаружен :orange_circle:", "Исправлен :green_circle:")).queue();
                    });
                    db.updateBug(bug);
                }
            }
        }
    }
}
