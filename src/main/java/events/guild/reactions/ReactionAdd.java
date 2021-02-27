package events.guild.reactions;

import api.Database;
import api.models.database.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.HashMap;

public class ReactionAdd extends ListenerAdapter {

    @Override
    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent reactionAddEvent) {
        Database db = new Database();
        Guild DBGuild = db.getGuildByID(reactionAddEvent.getGuild().getIdLong());
        if (DBGuild.getRolesForReactions() != null) {
            HashMap<String, String> reactions = DBGuild.getRolesForReactions();
            String emoji = reactionAddEvent.getReaction().getReactionEmote().getEmoji();
            if (reactions.containsKey(emoji)
                    && Long.parseLong(reactions.get(emoji).split(";")[1]) == reactionAddEvent.getMessageIdLong()) {
                Role role = reactionAddEvent.getGuild()
                        .getRoleById(Long.parseLong(reactions.get(emoji).split(";")[0]));
                reactionAddEvent.getGuild().addRoleToMember(reactionAddEvent.getMember(), role).queue();
            }
        }
    }

}
