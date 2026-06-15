package me.bananplayss.castlewars.core.kobalib;

import lombok.AccessLevel;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class KobaMessage {
    @Getter(AccessLevel.NONE) protected final String key;
    protected String message;
    protected List<String> messageList;

    public KobaMessage(String key, String message) {
        this.key = key;
        this.message = message;
        this.messageList = new ArrayList<String>();
    }

    public KobaMessage(String key, List<String> messageList) {
        this.key = key;
        this.message = null;
        this.messageList = messageList;
    }

    protected void load(KobaFile file) {
        // load
        if(file.getConfig().contains(this.key)) {
            this.message = file.getConfig().getString(this.key);
            this.messageList = file.getConfig().getStringList(this.key);

            if (!this.messageList.isEmpty()) {
                this.message = null;
            }
            return;
        }

        // save
        if (isSingle()) {
            this.message = file.getDefaultConfig().getOrSet(this.key, this.message);
            this.messageList = new ArrayList<>();
        } else {
            this.message = null;
            this.messageList = file.getDefaultConfig().getOrSet(this.key, this.messageList);
        }
    }

    protected boolean isSingle() {
        return (message != null && !message.isEmpty()) && (messageList == null || messageList.isEmpty());
    }

    protected boolean isSingleInConfig(KobaFile file) {
        Object value = file.getConfig().get(this.key);
        return value instanceof String;
    }

    public abstract KobaMessageBuilder builder();
}
