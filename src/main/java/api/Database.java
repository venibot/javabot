package api;

import api.models.database.Bot;
import api.models.database.Guild;
import api.models.database.User;
import api.models.exceptions.AlreadyInDatabaseException;
import api.utils.Config;
import com.mongodb.*;

import java.util.List;

public class Database {

    private MongoClient client;
    private DB database;

    public Database() {
        MongoClientURI uri = new MongoClientURI(Config.DB_CONFIG.get("uri"));
        client = new MongoClient(uri);
        database = client.getDB(Config.DB_CONFIG.get("db"));
    }

    public WriteResult addUser(User user) throws AlreadyInDatabaseException {
        if (this.getUserByID(user.getUserID(), user.getGuildID()) != null) {
            throw new AlreadyInDatabaseException(user);
        }
        DBCollection users = this.database.getCollection("users");
        try {
            BasicDBObject document = user.toDBObject();
            return users.insert(document);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public User getUserByID(Long userID, Long guildID) {
        BasicDBObject query = new BasicDBObject();
        query.put("userID", userID);
        query.put("guildID", guildID);

        DBObject result = this.database.getCollection("users").findOne(query);
        if (result != null) {
            try {
                return User.fromDBObject(result);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    public WriteResult updateUser(User new_user) {
        BasicDBObject query = new BasicDBObject();
        query.put("userID", new_user.getUserID());
        query.put("guildID", new_user.getGuildID());

        try {
            WriteResult result = this.database.getCollection("users").update(query, new_user.toDBObject());
            return result;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public WriteResult addGuild(Guild guild) throws AlreadyInDatabaseException {
        if (this.getGuildByID(guild.getGuildID()) != null) {
            throw new AlreadyInDatabaseException(guild);
        }
        DBCollection guilds = this.database.getCollection("guilds");
        try {
            BasicDBObject document = guild.toDBObject();
            return guilds.insert(document);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Guild getGuildByID(Long guildID) {
        BasicDBObject query = new BasicDBObject();
        query.put("guildID", guildID);

        DBObject result = this.database.getCollection("guilds").findOne(query);
        if (result != null) {
            try {
                return Guild.fromDBObject(result);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    public WriteResult updateGuild(Guild new_guild) {
        BasicDBObject query = new BasicDBObject();
        query.put("guildID", new_guild.getGuildID());

        try {
            WriteResult result = this.database.getCollection("guilds").update(query, new_guild.toDBObject());
            return result;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public WriteResult addBotStat(Bot botStat) throws AlreadyInDatabaseException {
        if (this.getBotStatByID(botStat.getId()) != null) {
            throw new AlreadyInDatabaseException(botStat);
        }
        DBCollection stats = this.database.getCollection("stats");
        try {
            BasicDBObject document = botStat.toDBObject();
            return stats.insert(document);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Bot getBotStatByID(Integer ID) {
        BasicDBObject query = new BasicDBObject();
        query.put("id", ID);

        DBObject result = this.database.getCollection("stats").findOne(query);
        if (result != null) {
            try {
                return Bot.fromDBObject(result);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    public Integer getLastStatID() {
        List<DBObject> documents = this.database.getCollection("stats").find().toArray();
        DBObject needDocument = null;
        int maxKey = 0;
        for (DBObject document: documents) {
            if ((Integer) document.get("id") > maxKey) {
                needDocument = document;
                maxKey = (Integer) document.get("id");
            }
        }
        try {
            return needDocument != null ? Bot.fromDBObject(needDocument).getId() : 0;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public void finalize() {
        this.client.close();
    }

}
