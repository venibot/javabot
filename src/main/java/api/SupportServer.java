package api;

import api.utils.Config;
import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

public class SupportServer {

    public Guild getGuild(JDA bot) {
        return bot.getGuildById(759796323569500160L);
    }

    public boolean isTester(JDA bot, User user) {
        Guild guild = getGuild(bot);
        Member member = guild.getMember(user);
        try {
            return member != null && member.getRoles().contains(guild.getRoleById(770374723900407888L));
        }
        catch (NullPointerException e) {
            return false;
        }
    }

    public boolean isSupport(JDA bot, User user) {
        Guild guild = getGuild(bot);
        Member member = guild.getMember(user);
        try {
            return member != null && member.getRoles().contains(guild.getRoleById(770374723900407888L));
        }
        catch (NullPointerException e) {
            return false;
        }
    }

    public boolean isStaff(JDA bot, User user) {
        Guild guild = getGuild(bot);
        Member member = guild.getMember(user);
        try {
            return member != null && member.getRoles().contains(guild.getRoleById(770374723900407888L));
        }
        catch (NullPointerException e) {
            return false;
        }
    }

    public boolean isDeveloper(JDA bot, User user) {
        Guild guild = getGuild(bot);
        Member member = guild.getMember(user);
        try {
            return member != null && member.getRoles().contains(guild.getRoleById(770374723900407888L));
        }
        catch (NullPointerException e) {
            return false;
        }
    }

    public boolean isDonater(JDA bot, User user) {
        Guild guild = getGuild(bot);
        Member member = guild.getMember(user);
        try {
            return member != null && member.getRoles().contains(guild.getRoleById(770374723900407888L));
        }
        catch (NullPointerException e) {
            return false;
        }
    }

    public void sendError(Exception error) {
        WebhookClient errorWebhook = WebhookClient.withUrl(Config.BOT_CONFIG.get("errorWebhookUrl"));
        WebhookEmbed.EmbedTitle title = new WebhookEmbed.EmbedTitle("Непредвиденная ошибка!", null);
        WebhookEmbed errorEmbed = new WebhookEmbedBuilder()
            .setTitle(title)
            .setDescription(error.getMessage() + "\n" + "Файл: " + error.getStackTrace()[0].getFileName() + "\n" + "Метод: " + error.getStackTrace()[0].getMethodName() + "\n" + "Строка: " + error.getStackTrace()[0].getLineNumber())
            .setColor(0xff0000)
            .build();
        errorWebhook.send(errorEmbed);
    }

    public void sendGulag(JDA bot, Guild guild, boolean left, User gulagUser) {
        TextChannel channel = bot.getTextChannelById(812407745939505273L);
        if (channel != null) {
            channel.sendMessage(new BasicEmbed("info")
                    .setTitle("Сервер " + guild.getName() + " отправлен в гулаг")
                    .setDescription((left ? "Я ливнул с сервера." : "Я не ливал с сервера.") + "\n"
                    + "Отправил в гулаг " + gulagUser.getAsTag())
                    .setFooter("ID " + guild.getId())
                    .build()).queue();
        }
    }

    public void sendGulagAttempt(JDA bot, Guild guild, User adder) {
        TextChannel channel = bot.getTextChannelById(812407745939505273L);
        if (channel != null) {
            channel.sendMessage(new BasicEmbed("info")
                    .setTitle("Меня попытались добавить на сервер " + guild.getName() + ", но он в гулаге")
                    .setDescription("Попытался добавить " + (adder != null ? adder.getAsTag() : "Пользователя определить не удалось"))
                    .setFooter("ID " + guild.getId())
                    .build()).queue();
        }
    }

}
