package api;

import api.models.database.*;
import api.models.exceptions.AlreadyInDatabaseException;
import api.utils.Config;
import com.mongodb.*;

import java.util.ArrayList;
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


    public Integer getLastReminderID() {
        List<DBObject> documents = this.database.getCollection("reminders").find().toArray();
        DBObject needDocument = null;
        int maxKey = 0;
        for (DBObject document: documents) {
            if ((Integer) document.get("ID") > maxKey) {
                needDocument = document;
                maxKey = (Integer) document.get("ID");
            }
        }
        try {
            return needDocument != null ? Reminder.fromDBObject(needDocument).getID() : 0;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public WriteResult addReminder(Reminder reminder) {
        DBCollection reminders = this.database.getCollection("reminders");
        try {
            BasicDBObject document = reminder.toDBObject();
            return reminders.insert(document);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Reminder> getUserReminders(Long userID, Long guildID) {
        BasicDBObject query = new BasicDBObject();
        query.put("userID", userID);
        query.put("guildID", guildID);

        List<DBObject> result = this.database.getCollection("reminders").find(query).toArray();
        if (result.size() > 0) {
            try {
                List<Reminder> reminders = new ArrayList<>();
                for (DBObject reminder: result) {
                    reminders.add(Reminder.fromDBObject(reminder));
                }
                return reminders;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    public List<Reminder> getReminders() {
        List<DBObject> result = this.database.getCollection("reminders").find().toArray();
        if (result.size() > 0) {
            try {
                List<Reminder> reminders = new ArrayList<>();
                for (DBObject reminder: result) {
                    reminders.add(Reminder.fromDBObject(reminder));
                }
                return reminders;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    public WriteResult deleteReminder(Integer ID) {
        DBCollection reminders = this.database.getCollection("reminders");
        BasicDBObject query = new BasicDBObject();
        query.put("ID", ID);

        return reminders.remove(query);
    }


    public WriteResult addWarn(Warn warn) {
        DBCollection warns = this.database.getCollection("warns");
        BasicDBObject document = warn.toDBObject();
        return warns.insert(document);
    }

    public Integer getLastWarnID(Long guildID) {
        BasicDBObject query = new BasicDBObject();
        query.put("guildID", guildID);
        List<DBObject> documents = this.database.getCollection("warns").find(query).toArray();
        DBObject needDocument = null;
        int maxKey = 0;
        for (DBObject document: documents) {
            if ((Integer) document.get("warnID") > maxKey) {
                needDocument = document;
                maxKey = (Integer) document.get("warnID");
            }
        }
        return needDocument != null ? Warn.fromDBObject(needDocument).getWarnID() : 0;
    }

    public List<Warn> getGuildWarns(Long guildID) {
        BasicDBObject query = new BasicDBObject();
        query.put("guildID", guildID);
        List<DBObject> documents = this.database.getCollection("warns").find(query).toArray();
        List<Warn> warns = new ArrayList<>();
        for (DBObject document: documents) {
            warns.add(Warn.fromDBObject(document));
        }
        return warns;
    }

    public List<Warn> getMemberWarns(Long guildID, Long memberID) {
        BasicDBObject query = new BasicDBObject();
        query.put("guildID", guildID);
        query.put("intruderID", memberID);
        List<DBObject> documents = this.database.getCollection("warns").find(query).toArray();
        List<Warn> warns = new ArrayList<>();
        for (DBObject document: documents) {
            warns.add(Warn.fromDBObject(document));
        }
        return warns;
    }

    public List<Warn> getWarns() {
        List<DBObject> result = this.database.getCollection("warns").find().toArray();
        if (result.size() > 0) {
            List<Warn> warns = new ArrayList<>();
            for (DBObject warn: result) {
                warns.add(Warn.fromDBObject(warn));
            }
            return warns;
        } else {
            return null;
        }
    }

    public Warn getWarnByID(Long guildID, Integer warnID) {
        BasicDBObject query = new BasicDBObject();
        query.put("guildID", guildID);
        query.put("warnID", warnID);
        DBObject document = this.database.getCollection("warns").findOne(query);
        if (document != null) {
            return Warn.fromDBObject(document);
        }
        return null;
    }

    public WriteResult deleteWarn(Long guildID, Integer warnID) {
        DBCollection warns = this.database.getCollection("warns");
        BasicDBObject query = new BasicDBObject();
        query.put("guildID", guildID);
        query.put("warnID", warnID);

        return warns.remove(query);
    }


    @Override
    public void finalize() {
        this.client.close();
    }

}
