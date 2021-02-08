package api;

import api.models.database.Guild;
import api.models.database.User;
import api.models.exceptions.AlreadyInDatabaseException;
import api.utils.Config;
import com.mongodb.*;

public class Database {

    private MongoClient client;
    private DB database;

    public Database() {
        client = new MongoClient(Config.DB_CONFIG.get("host"), Integer.parseInt(Config.DB_CONFIG.get("port")));
        database = client.getDB(Config.DB_CONFIG.get("db"));
    }

    public WriteResult addUser(User user) throws AlreadyInDatabaseException, IllegalAccessException {
        if (this.getUserByID(user.getUserID(), user.getGuildID()) != null) {
            throw new AlreadyInDatabaseException(user);
        }
        DBCollection users = this.database.getCollection("users");
        BasicDBObject document = user.toDBObject();
        return users.insert(document);
    }

    public User getUserByID(Long userID, Long guildID) throws IllegalAccessException {
        BasicDBObject query = new BasicDBObject();
        query.put("userID", userID);
        query.put("guildID", guildID);

        DBObject result = this.database.getCollection("users").findOne(query);
        if (result != null) {
            return User.fromDBObject(result);
        } else {
            return null;
        }
    }

    public WriteResult updateUser(User new_user) throws IllegalAccessException {
        BasicDBObject query = new BasicDBObject();
        query.put("userID", new_user.getUserID());
        query.put("guildID", new_user.getGuildID());

        WriteResult result = this.database.getCollection("users").update(query, new_user.toDBObject());
        return result;
    }

    public WriteResult addGuild(Guild guild) throws AlreadyInDatabaseException, IllegalAccessException {
        if (this.getGuildByID(guild.getGuildID()) != null) {
            throw new AlreadyInDatabaseException(guild);
        }
        DBCollection guilds = this.database.getCollection("guilds");
        BasicDBObject document = guild.toDBObject();
        return guilds.insert(document);
    }

    public Guild getGuildByID(Long guildID) throws IllegalAccessException {
        BasicDBObject query = new BasicDBObject();
        query.put("guildID", guildID);

        DBObject result = this.database.getCollection("guilds").findOne(query);
        if (result != null) {
            return Guild.fromDBObject(result);
        } else {
            return null;
        }
    }

    public WriteResult updateGuild(Guild new_guild) throws IllegalAccessException {
        BasicDBObject query = new BasicDBObject();
        query.put("guildID", new_guild.getGuildID());

        WriteResult result = this.database.getCollection("guilds").update(query, new_guild.toDBObject());
        return result;
    }

}
