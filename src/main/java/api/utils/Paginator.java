package api.utils;

import api.BasicEmbed;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Paginator extends ListenerAdapter {

    private User author;

    private final Message message;

    private final List<BasicEmbed> embeds;

    private final String backReaction;

    private final String forwardReaction;

    private Integer currentPage;

    public Paginator(Message message, List<BasicEmbed> embeds) {
        this.message = message;
        this.embeds = embeds;
        this.backReaction = "◀";
        this.forwardReaction = "▶";
        this.currentPage = 0;
        this.message.addReaction(this.backReaction).queue();
        this.message.addReaction(this.forwardReaction).queue();
        this.setEmbedsFooters();
        message.getJDA().addEventListener(this);
    }

    public Paginator(Message message, List<BasicEmbed> embeds, String backReaction, String forwardReaction) {
        this.message = message;
        this.embeds = embeds;
        this.backReaction = backReaction;
        this.forwardReaction = forwardReaction;
        this.currentPage = 0;
        this.message.addReaction(this.backReaction).queue();
        this.message.addReaction(this.forwardReaction).queue();
        this.setEmbedsFooters();
        message.getJDA().addEventListener(this);
    }

    public Paginator(User author, Message message, List<BasicEmbed> embeds) {
        this.author = author;
        this.message = message;
        this.embeds = embeds;
        this.backReaction = "◀";
        this.forwardReaction = "▶";
        this.currentPage = 0;
        this.message.addReaction(this.backReaction).queue();
        this.message.addReaction(this.forwardReaction).queue();
        this.setEmbedsFooters();
        message.getJDA().addEventListener(this);
    }

    public Paginator(User author, Message message, List<BasicEmbed> embeds, String backReaction, String forwardReaction) {
        this.author = author;
        this.message = message;
        this.embeds = embeds;
        this.backReaction = backReaction;
        this.forwardReaction = forwardReaction;
        this.currentPage = 0;
        this.message.addReaction(this.backReaction).queue();
        this.message.addReaction(this.forwardReaction).queue();
        this.setEmbedsFooters();
        message.getJDA().addEventListener(this);
    }

    private void setEmbedsFooters() {
        for (BasicEmbed embed: this.embeds) {
            this.embeds.set(this.embeds.indexOf(embed),
                    embed.setFooter("Страница " + (this.embeds.indexOf(embed) + 1) + "/" + this.embeds.size()));
        }
        this.message.editMessage(this.embeds.get(0).build()).queue();
    }

    @Override
    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event) {
        if (event.getMessageIdLong() == this.message.getIdLong()
                && !event.getUser().isBot()
                && event.getUser().getIdLong() == this.author.getIdLong()) {
            if (event.getReaction().getReactionEmote().isEmoji()) {
                try {
                    if (event.getReaction().getReactionEmote().getEmoji().equals(this.forwardReaction)) {
                        this.message.editMessage(this.embeds.get(this.currentPage + 1).build()).queue();
                        this.currentPage++;
                    } else if (event.getReaction().getReactionEmote().getEmoji().equals(this.backReaction)) {
                        this.message.editMessage(this.embeds.get(this.currentPage - 1).build()).queue();
                        this.currentPage--;
                    }
                } catch (IndexOutOfBoundsException ignored) {

                }
                if (event.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_MANAGE)) {
                    this.message.removeReaction(event.getReaction().getReactionEmote().getEmoji(),
                            event.getUser()).queue();
                }
            } else {
                if (event.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_MANAGE)) {
                    this.message.removeReaction(event.getReaction().getReactionEmote().getEmote(),
                            event.getUser()).queue();
                }
            }
        }
    }
}
