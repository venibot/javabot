package commands.music;

import api.BasicEmbed;
import api.lavalink.AudioTrackInformation;
import api.lavalink.MusicManager;
import api.lavalink.PlayerManager;
import api.models.command.Command;
import api.models.command.CommandContext;
import api.models.command.DiscordCommand;
import api.utils.DataFormatter;
import api.utils.Functions;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import java.util.concurrent.BlockingQueue;

@DiscordCommand(name = "queue", description = "Просмотр очереди сервера", aliases = {"очередь"}, group = "Музыка")
public class QueueCommand implements Command {

    @Override
    public void doCommand(CommandContext context, String[] arguments) {
        if (context.getGuild().getSelfMember().getVoiceState().inVoiceChannel()) {
            MusicManager musicManager = PlayerManager.getInstance().getMusicManager(context.getGuild());
            BlockingQueue<AudioTrack> queue = musicManager.trackScheduler.queue;
            BasicEmbed infoEmbed = new BasicEmbed("info");
            infoEmbed.setTitle("Музыкальная очередь сервера " + context.getGuild().getName());
            if (queue.size() == 0) {
                infoEmbed.setDescription("Очередь пуста");
            } else {
                infoEmbed.setDescription("Всего треков в очереди: " + queue.size());
                for (AudioTrack track: queue) {
                    infoEmbed.addField(track.getPosition() + ". " + track.getInfo().title, track.getInfo().author + ", длина "
                            + (track.getInfo().isStream ? "∞" : DataFormatter.getTrackLength(track.getDuration())) + ". Трек добавлен "
                            + track.getUserData(AudioTrackInformation.class).adder.getAsMention());
                }
            }
            try {
                context.sendMessage(infoEmbed).queue();
            } catch (IllegalStateException e) {
                BasicEmbed errorEmbed = new BasicEmbed("error", "К сожалению очередь музыки слишком большая "
                        + "и я не могу её отобразить, но точно могу сказать вам, что в очереди " + queue.size() + " треков");
                context.sendMessage(errorEmbed).queue();
            }
        } else {
            BasicEmbed errorEmbed = new BasicEmbed("error", "Я не нахожусь в голосовом канале");
        }
    }

}
