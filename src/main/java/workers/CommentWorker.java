package workers;

import api.SupportServer;
import api.models.workers.Worker;
import api.models.workers.WorkerInfo;
import api.utils.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;

@WorkerInfo(name = "Comment", description = "Проверка на наличие новых комментариев на SD.C", active = false)
public class CommentWorker implements Worker {

    String oldComment = "";

    @Override
    public void execute() {
        try {
            Document document = Jsoup.connect("https://bots.server-discord.com/728030884179083354").get();
            if (!oldComment.equals(document.select("span.comment").select(".post").select(".text").first().wholeText())) {
                oldComment = document.select("span.comment").select(".post").select(".text").first().wholeText();
                String comment = document.select("span.comment").select(".post").select(".text").first().wholeText();
                String imageUrl = document.select("img.comment").select(".post").select(".img").first().attr("src");
                String author = document.select("span.comment").select(".post").select(".nick").first().text();
                String date = document.select("span.comment").select(".post").select(".date").first().text();
                author = author.replace(date, "");
                TextChannel channel = new SupportServer(Config.BOT.getShardById(0)).getGuild().getTextChannelById(842103959752671293L);
                EmbedBuilder builder = new EmbedBuilder();
                builder.setTitle("Новый отзыв!");
                builder.setDescription(String.format("Пользователь %s оставил новый отзыв на сайте https://bots.server-discord.com/", author));
                builder.setThumbnail(imageUrl);
                builder.setThumbnail(imageUrl);
                builder.addField("Отзыв:", comment, false);
                assert channel != null;
                channel.sendMessage(builder.build()).queue();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
