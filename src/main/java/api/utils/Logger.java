package api.utils;

import api.BasicEmbed;
import api.models.database.Warn;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

public class Logger {

    public static void logWarnCreate(Warn createdWarn) {
        Guild guild = Config.BOT.getGuildById(createdWarn.getGuildID());
        TextChannel logChannel = GetLogChannel.getChannel(guild, "warnCreate");
        if (logChannel != null) {
            BasicEmbed infoEmbed = new BasicEmbed("info");
            infoEmbed.setTitle("Выдано новое предупреждение");
            infoEmbed.addField("ID выданного предупреждения", createdWarn.getWarnID().toString());
            Member intruder = guild.getMemberById(createdWarn.getIntruderID());
            Member punisher = guild.getMemberById(createdWarn.getPunisherID());
            infoEmbed.addField("Предупреждение выдал", punisher.getAsMention() + "(" + punisher.getUser().getAsTag() + ")");
            infoEmbed.addField("Предупреждение выдано", intruder.getAsMention() + "(" + intruder.getUser().getAsTag() + ")");
            infoEmbed.addField("Причина выдачи", createdWarn.getReason());
            infoEmbed.addField("Дата истечения", createdWarn.getEndTime() != 0
                    ? DataFormatter.datetimeToString(DataFormatter.unixToDateTime(createdWarn.getEndTime())) : "Перманентное предупреждение");
            logChannel.sendMessage(infoEmbed.build()).queue();
        }
    }

    public static void logWarnDelete(Warn deletedWarn) {
        Guild guild = Config.BOT.getGuildById(deletedWarn.getGuildID());
        TextChannel logChannel = GetLogChannel.getChannel(guild, "warnDelete");
        if (logChannel != null) {
            BasicEmbed infoEmbed = new BasicEmbed("info");
            infoEmbed.setTitle("Снято предупреждение");
            infoEmbed.addField("ID снятого предупреждения", deletedWarn.getWarnID().toString());
            infoEmbed.addField("Кто снял", Config.BOT.getShardById(0).getSelfUser().getAsMention());
            infoEmbed.addField("Причина снятия", "Истечение срока действия предупреждения");
            logChannel.sendMessage(infoEmbed.build()).queue();
        }
    }

    public static void logWarnDelete(Warn deletedWarn, Member unPunisher, String reason) {
        Guild guild = Config.BOT.getGuildById(deletedWarn.getGuildID());
        TextChannel logChannel = GetLogChannel.getChannel(guild, "warnDelete");
        if (logChannel != null) {
            BasicEmbed infoEmbed = new BasicEmbed("info");
            infoEmbed.setTitle("Снято предупреждение");
            infoEmbed.addField("ID снятого предупреждения", deletedWarn.getWarnID().toString());
            infoEmbed.addField("Кто снял", unPunisher.getAsMention() + "(" + unPunisher.getUser().getAsTag() + ")");
            infoEmbed.addField("Причина снятия", reason);
            logChannel.sendMessage(infoEmbed.build()).queue();
        }
    }

}
