package events.bot;

import api.Database;
import api.models.database.Idea;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Date;

public class IdeaChecker extends ListenerAdapter {

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        if (event.getChannel().getIdLong() == 767158493433823253L && !event.getUser().isBot()) {
            try {
                if (event.getReaction().getReactionEmote().getEmote().getIdLong() == 835140564885569567L) {
                    // Идея принята
                    Database db = new Database();
                    Idea idea = db.getIdeaByPrivateMessage(event.getMessageIdLong());
                    idea.setStatus("accept");
                    idea.setAcceptTime(new Date().getTime());
                    TextChannel channel = event.getJDA().getTextChannelById(835189964462751755L);
                    channel.sendMessage("Идея #" + idea.getIdeaID()
                            + "\nАвтор идеи: " + event.getJDA().getUserById(idea.getUserID()).getAsTag()
                            + "\nТекст идеи: " + idea.getText()
                            + "\nСтатус: Принято :orange_circle:"
                            + "\nИдею принял " + event.getUser().getAsMention() + "(" + event.getUser().getAsTag() + ")"
                    ).queue(message -> {
                        idea.setPublicMessage(message.getIdLong());
                        db.updateIdea(idea);
                    });
                    event.getReaction().clearReactions().queue();
                    event.getTextChannel().retrieveMessageById(event.getMessageIdLong()).queue(message -> {
                        // Удаление реакции отклонения и бана
                        message.removeReaction(event.getGuild().getEmoteById(835141052876324904L)).queue();
                        message.removeReaction(event.getGuild().getEmoteById(835151191951409173L)).queue();
                    });
                } else if (event.getReaction().getReactionEmote().getEmote().getIdLong() == 835141052876324904L) {
                    // Идея отклонена
                    Database db = new Database();
                    Idea idea = db.getIdeaByPrivateMessage(event.getMessageIdLong());
                    idea.setStatus("disagree");
                    idea.setDisagreeTime(new Date().getTime());
                    event.getReaction().clearReactions().queue();
                    event.getTextChannel().retrieveMessageById(event.getMessageIdLong()).queue(message -> {
                        // Удаление реакции принятия
                        message.removeReaction(event.getGuild().getEmoteById(835140564885569567L)).queue();
                    });
                    db.updateIdea(idea);
                }
            } catch (IllegalStateException e) {
                if (event.getReaction().getReactionEmote().getEmoji().equals("\uD83D\uDFE2")) {
                    // Идея реализована
                    Database db = new Database();
                    Idea idea = db.getIdeaByPrivateMessage(event.getMessageIdLong());
                    idea.setStatus("did");
                    idea.setDidTime(new Date().getTime());
                    event.getTextChannel().retrieveMessageById(event.getMessageIdLong()).queue(message -> {
                        message.removeReaction("\uD83D\uDFE2").queue();
                    });
                    event.getGuild().getTextChannelById(835189964462751755L).retrieveMessageById(idea.getPublicMessage()).queue(message -> {
                        message.editMessage(message.getContentRaw()
                                .replace("Принято :orange_circle:", "Выполнено :green_circle:")).queue();
                    });
                    db.updateIdea(idea);
                }
            }
        }
    }
}
