package api;

import api.models.database.User;
import api.utils.Config;
import com.mongodb.*;

public class Database {

    private MongoClient client;
    private DB database;

    public Database() {
        client = new MongoClient(Config.DB_CONFIG.get("host"), Integer.parseInt(Config.DB_CONFIG.get("port")));
        database = client.getDB(Config.DB_CONFIG.get("db"));
    }

    public WriteResult addUser(User user) throws Exception {
        if (this.getUserByID(user.getUserID(), user.getGuildID()) != null) {
            throw new Exception("Попытка занести пользователя " + user + " в базу данных, хотя он уже там есть");
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

}
