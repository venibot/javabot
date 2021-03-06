package api.utils;

import api.BasicEmbed;
import api.models.command.CommandContext;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildVoiceState;

import java.util.List;

public class Functions {

    public static void debug(Object[] array) {
        String output = "[";

        for (Object object: array) {
            output += object + ", ";
        }

        output = output.replaceAll(", $", "");
        output += "]";

        System.out.println(output);
    }

    public static void debug(List array) {
        String output = "[";
        for (Object object: array) {
            output += object + ", ";
        }
        output = output.replaceAll(", $", "");
        output += "]";
        System.out.println(output);
    }

    public static String debug(Object[] array, boolean toReturn) {
        String output = "[";
        for (Object object: array) {
            output += object + ", ";
        }
        output = output.replaceAll(", $", "");
        output += "]";
        if (toReturn) {
            return output;
        } else {
            System.out.println(output);
            return null;
        }
    }

    public static String debug(List array, boolean toReturn) {
        String output = "[";
        for (Object object: array) {
            output += object + ", ";
        }
        output = output.replaceAll(", $", "");
        output += "]";
        if (toReturn) {
            return output;
        } else {
            System.out.println(output);
            return null;
        }
    }

    public static boolean checkMusicCommandAuthor(CommandContext context) {
        GuildVoiceState botVoiceState = context.getGuild().getSelfMember().getVoiceState();
        GuildVoiceState userVoiceState = context.getAuthor().getVoiceState();
        if (!userVoiceState.inVoiceChannel()) {
            BasicEmbed errorEmbed = new BasicEmbed("error", "Для использования команды войдите в голосовой канал");
            context.sendMessage(errorEmbed).queue();
            return false;
        }
        if (!botVoiceState.inVoiceChannel()) {
            if (!context.getGuild().getSelfMember().hasPermission(userVoiceState.getChannel(),
                    Permission.VOICE_CONNECT, Permission.VOICE_SPEAK)) {
                BasicEmbed errorEmbed = new BasicEmbed("error", "У меня нет прав для входа в ваш канал");
                context.sendMessage(errorEmbed).queue();
                return false;
            }
            context.getGuild().getAudioManager().openAudioConnection(userVoiceState.getChannel());
            return true;
        }
        if (!userVoiceState.getChannel().equals(botVoiceState.getChannel())) {
            BasicEmbed errorEmbed = new BasicEmbed("error", "Вы должны быть в одном голосовом канале со мной");
            context.sendMessage(errorEmbed).queue();
            return false;
        }
        return true;
    }

}
