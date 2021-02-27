package events.guild.reactions;

import api.Database;
import api.models.database.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.HashMap;

public class ReactionRemove extends ListenerAdapter {

    @Override
    public void onGuildMessageReactionRemove(GuildMessageReactionRemoveEvent reactionRemoveEvent) {
        Database db = new Database();
        Guild DBGuild = db.getGuildByID(reactionRemoveEvent.getGuild().getIdLong());
        if (DBGuild.getRolesForReactions() != null) {
            HashMap<String, String> reactions = DBGuild.getRolesForReactions();
            String emoji = reactionRemoveEvent.getReaction().getReactionEmote().getEmoji();
            if (reactions.containsKey(emoji)
                    && Long.parseLong(reactions.get(emoji).split(";")[1]) == reactionRemoveEvent.getMessageIdLong()) {
                Role role = reactionRemoveEvent.getGuild()
                        .getRoleById(Long.parseLong(reactions.get(emoji).split(";")[0]));
                reactionRemoveEvent.getGuild().removeRoleFromMember(reactionRemoveEvent.getUserId(), role).queue();
            }
        }
    }

}
