package commands.owner;

import api.models.command.Command;
import api.models.command.DiscordCommand;
import api.utils.Config;
import api.utils.Functions;
import com.google.gson.Gson;
import com.mongodb.*;
import com.mongodb.client.model.DBCollectionUpdateOptions;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

@DiscordCommand(name = "database", description = "Работа с базой данных", aliases = {"db", "database", "датабазе"}, group = "Владельцу",
                hidden = true, accessLevel = 4, arguments = 5)
public class DatabaseCommand implements Command {

    @Override
    public void doCommand(MessageReceivedEvent msg_event, String[] arguments) {
        if (arguments[0].equals("get")) {
            String collection = arguments[1];
            BasicDBObject query = new Gson().fromJson((arguments.length > 3 ? arguments[2] + arguments[3] : arguments[2]), BasicDBObject.class);
            MongoClient client = new MongoClient(new MongoClientURI(Config.DB_CONFIG.get("uri")));
            DB database = client.getDB(Config.DB_CONFIG.get("db"));
            List<DBObject> result = database.getCollection(collection).find(query).toArray();
            String resultToSend = "```" + Functions.debug(result, true) + "```";
            if (resultToSend.length() > 2000) {
                msg_event.getChannel().sendMessage("Результат длиннее 2000 символов, вот его укороченный вариант").queue(response -> {
                    response.editMessage(resultToSend.substring(0, 1996) + "```").queue();
                });
            } else {
                msg_event.getChannel().sendMessage(resultToSend).queue();
            }
            client.close();
        }
        if (arguments[0].equals("delete")) {
            String collection = arguments[1];
            BasicDBObject query = new Gson().fromJson((arguments.length > 3 ? arguments[2] + arguments[3] : arguments[2]), BasicDBObject.class);
            MongoClient client = new MongoClient(new MongoClientURI(Config.DB_CONFIG.get("uri")));
            DB database = client.getDB(Config.DB_CONFIG.get("db"));
            database.getCollection(collection).remove(query);
            msg_event.getMessage().addReaction("✅").queue();
            client.close();
        }
        if (arguments[0].equals("update")) {
            String collection = arguments[1];
            BasicDBObject query = new Gson().fromJson((arguments[2]), BasicDBObject.class);
            BasicDBObject update = new Gson().fromJson((arguments[3]), BasicDBObject.class);
            DBCollectionUpdateOptions options = new Gson().fromJson(arguments[4], DBCollectionUpdateOptions.class);
            MongoClient client = new MongoClient(new MongoClientURI(Config.DB_CONFIG.get("uri")));
            DB database = client.getDB(Config.DB_CONFIG.get("db"));
            database.getCollection(collection).update(query, update, options);
            msg_event.getMessage().addReaction("✅").queue();
            client.close();
        }
    }
}
