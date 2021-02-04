package api.utils;

import api.models.exceptions.MemberNotFoundException;
import api.models.exceptions.UserNotFoundException;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;

import java.util.HashMap;

public class Converters {

    public static User getUser(JDA bot, String user) throws UserNotFoundException {
        try {
            User bot_user = bot.getUserById(user);
            if (bot_user == null) {
                throw new NumberFormatException();
            }
            return bot_user;
        } catch (NumberFormatException e) {
            HashMap<Integer, User> users = new HashMap<>();
            for (User bot_user: bot.getUsers()) {
                users.put(FuzzySearch.ratio(user.toLowerCase(), bot_user.getAsTag().toLowerCase()), bot_user);
            }
            Integer maxKey = getMax(users);
            if (users.get(maxKey) != null && maxKey >= 30) {
                return users.get(maxKey);
            }
            throw new UserNotFoundException(user);
        }
    }

    public static Member getMember(Guild guild, String member) throws MemberNotFoundException {
        try {
            Member guild_member = guild.getMemberById(member.replaceAll("[<@!>]", ""));
            if (guild_member == null) {
                throw new NumberFormatException();
            }
            return guild_member;
        } catch (NumberFormatException e) {
            HashMap<Integer, Member> members = new HashMap<>();
            for (Member guild_member: guild.getMembers()) {
                if (guild_member.getNickname() != null) {
                    members.put(FuzzySearch.ratio(member.toLowerCase(), guild_member.getNickname().toLowerCase()), guild_member);
                }
            }
            Integer maxKey = getMax(members);
            if (members.get(maxKey) != null && maxKey >= 50) {
                return members.get(maxKey);
            } else {
                members.clear();
                for (Member guild_member: guild.getMembers()) {
                    members.put(FuzzySearch.ratio(member.toLowerCase(), guild_member.getUser().getAsTag().toLowerCase()), guild_member);
                }
                maxKey = getMax(members);
                if (members.get(maxKey) != null && maxKey >= 40) {
                    return members.get(maxKey);
                }
                throw new MemberNotFoundException(member, guild.getId());
            }
        }
    }

    private static int getMax(HashMap map) {
        int maxKey = 0;
        for (Object key: map.keySet()) {
            if (((Integer) key) > maxKey) {
                maxKey = (Integer) key;
            }
        }
        return maxKey;
    }

}
