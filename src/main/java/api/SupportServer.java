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

    private final JDA bot;

    public SupportServer(JDA bot) {
        this.bot = bot;
    }

    public Guild getGuild() {
        return this.bot.getGuildById(759796323569500160L);
    }

    public boolean isMember(User user) {
        Guild guild = getGuild();
        Member member = guild.getMember(user);
        return member != null;
    }

    public boolean isTester(User user) {
        Guild guild = getGuild();
        Member member = guild.getMember(user);
        try {
            return member.getRoles().contains(guild.getRoleById(759796893621551184L)) || user.getIdLong() == 453179077294161920L;
        }
        catch (NullPointerException e) {
            return false;
        }
    }

    public boolean isSupport(User user) {
        Guild guild = getGuild();
        Member member = guild.getMember(user);
        try {
            return member.getRoles().contains(guild.getRoleById(770374723900407888L)) || user.getIdLong() == 453179077294161920L;
        }
        catch (NullPointerException e) {
            return false;
        }
    }

    public boolean isStaff(User user) {
        Guild guild = getGuild();
        Member member = guild.getMember(user);
        try {
            return member.getRoles().contains(guild.getRoleById(759797091198435338L)) || user.getIdLong() == 453179077294161920L;
        }
        catch (NullPointerException e) {
            return false;
        }
    }

    public boolean isDeveloper(User user) {
        Guild guild = getGuild();
        Member member = guild.getMember(user);
        try {
            return member.getRoles().contains(guild.getRoleById(763296493138214915L)) || user.getIdLong() == 453179077294161920L;
        }
        catch (NullPointerException e) {
            return false;
        }
    }

    public boolean isOwner(User user) {
        Guild guild = getGuild();
        Member member = guild.getMember(user);
        try {
            return member.getRoles().contains(guild.getRoleById(759806607540111893L)) || user.getIdLong() == 453179077294161920L;
        }
        catch (NullPointerException e) {
            return false;
        }
    }

    public boolean isDonator(User user) {
        Guild guild = getGuild();
        Member member = guild.getMember(user);
        try {
            return member.getRoles().contains(guild.getRoleById(777201934763032626L)) || user.getIdLong() == 453179077294161920L;
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

    public void sendGulag(Guild guild, boolean left, User gulagger) {
        TextChannel channel = this.bot.getTextChannelById(812407745939505273L);
        channel.sendMessage(new BasicEmbed("info")
                .setTitle("Сервер " + guild.getName() + " отправлен в гулаг")
                .setDescription((left ? "Я ливнул с сервера." : "Я не ливал с сервера.") + "\n"
                + "Отправил в гулаг " + gulagger.getAsTag())
                .setFooter("ID " + guild.getId())
                .build()).queue();
    }

    public void sendGulagAttempt(Guild guild, User adder) {
        TextChannel channel = this.bot.getTextChannelById(812407745939505273L);
        channel.sendMessage(new BasicEmbed("info")
                .setTitle("Меня попытались добавить на сервер " + guild.getName() + ", но он в гулаге")
                .setDescription("Попытался добавить " + (adder != null ? adder.getAsTag() : "Пользователя определить не удалось"))
                .setFooter("ID " + guild.getId())
                .build()).queue();
    }

    public void sendUnGulag(Guild guild, User ungulagger) {
        TextChannel channel = this.bot.getTextChannelById(812407745939505273L);
        channel.sendMessage(new BasicEmbed("info")
                .setTitle("Сервер " + guild.getName() + " удалён из гулага")
                .setDescription("Удалил из гулага " + ungulagger.getAsTag())
                .setFooter("ID " + guild.getId())
                .build()).queue();
    }

    public void guildJoined(Guild guild) {
        TextChannel channel = this.bot.getTextChannelById(785146619825094736L);
        channel.sendMessage(new BasicEmbed("info")
                .setTitle("Я добавлен на сервер " + guild.getName())
                .setDescription("Теперь у бота " + this.bot.getGuilds().size() + " серверов")
                .setFooter("ID " + guild.getId())
                .build()).queue();
    }

    public void guildLeft(Guild guild) {
        TextChannel channel = this.bot.getTextChannelById(785146619825094736L);
        channel.sendMessage(new BasicEmbed("info")
                .setTitle("Я удалён с сервера " + guild.getName())
                .setDescription("Теперь у бота " + this.bot.getGuilds().size() + " серверов")
                .setFooter("ID " + guild.getId())
                .build()).queue();
    }

}
