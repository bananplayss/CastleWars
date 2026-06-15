package me.bananplayss.castlewars.core.messages;


import me.bananplayss.castlewars.core.Main;
import me.bananplayss.castlewars.core.kobalib.KobaMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Message extends KobaMessage {

    private static final Map<String, Message> MESSAGES = new HashMap<>();

    public static final Message PREFIX = new Message("prefix", "&8[&aCastleWars&8]");
    public static final Message NO_PERMISSION = new Message("no_permission", "%prefix% &cEhhez nincs jogod!");
    public static final Message COMMAND_DOES_NOT_EXISTS = new Message("command_does_not_exists", "%prefix% &cNem található ilyen parancs. Használd a /sumo parancsot a további segítségért.");
    public static final Message COMMAND_USAGE = new Message("command_usage", "%prefix% &cRossz parancs használat. Használd: %usage%");
    public static final Message PLAYER_ONLY = new Message("player_only", "%prefix% &cThis command only executeable as player!");
    public static final Message CONSOLE_ONLY = new Message("console_only", "%prefix% &cThis command only executeable from console!");
    public static final Message NOT_ENOUGH_ARGUMENTS = new Message("not_enough_arguments", "%prefix% &cInvalid command usage.");
    public static final Message RELOADED = new Message("reloaded", "%prefix% &aConfiguration files successfully reloaded!.");
    public static final Message PLAYER_NOT_FOUND = new Message("player_not_found", "%prefix% &cA játékos nem található.");



    public Message(String key, String message) {
        super(key, message);
        this.load(Main.getInstance().getFileManager().getMessages());

        if (MESSAGES.containsKey(key)) {
            throw new IllegalStateException("The message already exists! " + key);
        }
        MESSAGES.put(key, this);
    }

    public Message(String key, List<String> messageList) {
        super(key, messageList);
        this.load(Main.getInstance().getFileManager().getMessages());

        if (MESSAGES.containsKey(key)) {
            throw new IllegalStateException("The message already exists!");
        }
        MESSAGES.put(key, this);
    }

    @Override
    public MessageBuilder builder() {
        MessageBuilder b = new MessageBuilder(this.messageList);
        if (this.message != null) {
            b = new MessageBuilder(this.message);
        }
        try {
            return b.setPrefix();
        } catch (NullPointerException e) {
            return b;
        }
    }

    public static void reloadAll() {
        for (Message value : MESSAGES.values()) {
            value.load(Main.getInstance().getFileManager().getMessages());
        }
    }

    public static Message[] values() {
        return MESSAGES.values().toArray(new Message[0]);
    }
}