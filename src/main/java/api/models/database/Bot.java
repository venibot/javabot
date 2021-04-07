package api.models.database;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.sharding.ShardManager;

import javax.enterprise.inject.Model;
import java.time.OffsetDateTime;
import java.util.Date;

@Model
public class Bot {

    private Integer id;

    private Long statTime;

    private Long commandsCount;

    private Integer guildsCount;

    private Integer usersCount;

    private Integer channelsCount;

    public Bot(Integer id, ShardManager bot, Long commandsCount) {
        this.id = id;
        this.statTime = new Date().getTime();
        this.guildsCount = bot.getGuilds().size();
        this.usersCount = bot.getUsers().size();
        this.channelsCount = bot.getTextChannels().size() + bot.getVoiceChannels().size();
        this.commandsCount = commandsCount;
    }

    public Bot(Integer id, Integer guildsCount, Integer usersCount, Integer channelsCount, Long commandsCount) {
        this.id = id;
        this.statTime = new Date().getTime();
        this.guildsCount = guildsCount;
        this.usersCount = usersCount;
        this.channelsCount = channelsCount;
        this.commandsCount = commandsCount;
    }

    public Bot(Integer id, Long statTime, Integer guildsCount, Integer usersCount,
               Integer channelsCount, Long commandsCount) {

        this.id = id;
        this.statTime = statTime;
        this.guildsCount = guildsCount;
        this.usersCount = usersCount;
        this.channelsCount = channelsCount;
        this.commandsCount = commandsCount;
    }

    public Integer getId() {
        return this.id;
    }

    public Long getStatTime() {
        return this.statTime;
    }

    public Long getCommandsCount() {
        return this.commandsCount;
    }

    public Integer getGuildsCount() {
        return this.guildsCount;
    }

    public Integer getUsersCount() {
        return this.usersCount;
    }

    public Integer getChannelsCount() {
        return this.channelsCount;
    }

    public BasicDBObject toDBObject() throws IllegalAccessException {

        BasicDBObject document = new BasicDBObject();
        document.put("id", this.id);
        document.put("statTime", this.statTime);
        document.put("commandsCount", this.commandsCount);
        document.put("guildsCount", this.guildsCount);
        document.put("usersCount", this.usersCount);
        document.put("channelsCount", this.channelsCount);
        return document;
    }

    public static Bot fromDBObject(DBObject document) throws IllegalAccessException {

        Bot botStat = new Bot((Integer) document.get("id"),
                (Long) document.get("statTime"),
                (Integer) document.get("guildsCount"),
                (Integer) document.get("usersCount"),
                (Integer) document.get("channelsCount"),
                (Long) document.get("commandsCount"));
        return botStat;
    }

    @Override
    public String toString() {
        return "Статистика бота от " + this.statTime + ", ID " + this.id;
    }
}
