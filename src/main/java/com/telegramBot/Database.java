package com.telegramBot;

import com.google.gson.*;
import com.mongodb.*;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import io.github.cdimascio.dotenv.Dotenv;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {
    MongoClient mongo;
    MongoCollection<Document> settingsCollection;

    Database() {
        Logger.getLogger("org.mongodb.driver").setLevel(Level.OFF);
        Dotenv dotenv = Dotenv.configure().directory("F:\\Java\\MFPBot\\src\\main").load();
        mongo = new MongoClient(new MongoClientURI(Objects.requireNonNull(dotenv.get("DB_CONNECT"))));
        MongoDatabase database = mongo.getDatabase("TGBotSettings");
        settingsCollection = database.getCollection("Settings");
    }
    public boolean checkExisting(String chat_id)
    {
        return settingsCollection.find(new Document("chat_id", chat_id)).first() != null;
    }

    public Settings setSettings(String new_chat_id)
    {
        Settings settings = new Settings(new_chat_id);
        settingsCollection.insertOne(Document.parse(new Gson().toJson(settings)));
        return settings;
    }
    public Settings getSettings(String chat_id)
    {
        Settings settings = new Settings(chat_id);
        String new_doc = Objects.requireNonNull(settingsCollection.find(new Document("chat_id", chat_id)).first()).toJson();
        JsonObject json_el = JsonParser.parseString(new_doc).getAsJsonObject();
        settings.bank = json_el.get("bank").getAsString();
        settings.after_comma_symbol = json_el.get("after_comma_symbol").getAsInt();
        settings.currency.clear();
        JsonArray jsonArray = json_el.getAsJsonObject().get("currency").getAsJsonArray();
        for (int i = 0; i < jsonArray.size(); i++)
        {
            settings.currency.add(jsonArray.get(i).getAsString());
        }
        settings.notification = json_el.get("notification").getAsString();
        return settings;
    }
    public void updateSettings(String chat_id, String updatedField, String updatedValue)
    {
        Document query = new Document().append("chat_id",  chat_id);
        Bson updates;
        updates = Updates.combine(Updates.set(updatedField, updatedValue));
        UpdateOptions options = new UpdateOptions().upsert(true);
        settingsCollection.updateOne(query, updates, options);
    }
    public void updateIntSettings(String chat_id, String updatedField, int updatedValue)
    {
        Document query = new Document().append("chat_id",  chat_id);
        Bson updates;
        updates = Updates.combine(Updates.set(updatedField, updatedValue));
        UpdateOptions options = new UpdateOptions().upsert(true);
        settingsCollection.updateOne(query, updates, options);
    }

    public void updateCurrencySettings(String chat_id, List<String> currencies)
    {
        Document query = new Document().append("chat_id",  chat_id);
        Bson updates = Updates.combine(Updates.set("currency", currencies));
        UpdateOptions options = new UpdateOptions().upsert(true);
        settingsCollection.updateOne(query, updates, options);
    }
}
