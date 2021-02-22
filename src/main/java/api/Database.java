package api;
import api.utils.database.models.*;
import api.models.exceptions.AlreadyInDatabaseException;
import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Objects;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

public class Database {

    private final MongoCollection<Document> prefixDB;
    private final MongoCollection<Document> userDB;
    private final MongoCollection<Document> guildDB;

    public Database() {
        MongoClient client = new MongoClient(new MongoClientURI("mongodb+srv://LavaBot:86WmKdsAkW4qAP1q@lava.hddhr.mongodb.net/LAVA?retryWrites=true&w=majority")); //Config.DB_CONFIG.get("host"), Integer.parseInt(Config.DB_CONFIG.get("port"))
        MongoDatabase database = client.getDatabase("lava");
        prefixDB = database.getCollection("prefixDB");
        userDB = database.getCollection("userDB");
        guildDB = database.getCollection("guildDB");
    }
    public void setPrefix(String prefix, Long GuildID) {
        if (this.prefixDB.find(eq("guildID", GuildID)).first() != null) {
            this.prefixDB.updateOne(eq("guildID", GuildID), set("prefix", prefix));
        } else {
            Document insert = new Document("_id", new ObjectId());
            insert.append("guildID", GuildID).append("prefix", prefix);
            this.prefixDB.insertOne(insert);
        }
    }

    public String getPrefix(Long GuildID) {
        if (this.prefixDB.find(eq("guildID", GuildID)).first() != null) {
            return Objects.requireNonNull(this.prefixDB.find(eq("guildID", GuildID)).first()).get("prefix").toString();
        } else {
            return null;
        }
    }

    public void addUser(User user) throws  AlreadyInDatabaseException {
        if (getUserByID(user.getUserID(), user.getGuildID()) != null) {
            throw new AlreadyInDatabaseException(user);
        }
        Document document = user.toDocument();
        this.userDB.insertOne(document);
    }

    public User getUserByID(Long userID, Long guildID) {
        if (this.userDB.find(new Document("userId", userID).append("guildID", guildID)).first() != null) {
            return User.fromDocument(Objects.requireNonNull(this.userDB.find(new Document("userId", userID).append("guildID", guildID)).first()));
        } else {
            return null;
        }
    }

    public void updateUser(User user) {
        try {
            this.userDB.replaceOne(new Document("userId", user.getUserID()).append("guildID", user.getGuildID()), user.toDocument());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addGuild(Guild guild) throws AlreadyInDatabaseException {
        if (this.getGuildByID(guild.getGuildId()) != null) {
            throw new AlreadyInDatabaseException(guild);
        }

        Document document = guild.toDocument();
        this.guildDB.insertOne(document);
    }

    public Guild getGuildByID(Long guildID) {
        if (this.guildDB.find(new Document("guildId", guildID)).first() != null) {
            return Guild.fromDocument(Objects.requireNonNull(this.guildDB.find(eq("guildId", guildID)).first()));
        } else {
            return null;
        }
    }

    public void updateGuild(Guild guild) {
        try {
            this.guildDB.replaceOne(new Document("guildId", guild.getGuildId()), guild.toDocument());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
