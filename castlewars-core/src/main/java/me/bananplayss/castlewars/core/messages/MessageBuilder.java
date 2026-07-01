package me.bananplayss.castlewars.core.messages;

import me.bananplayss.castlewars.core.kobalib.KobaMessageBuilder;

import java.util.List;

public class MessageBuilder extends KobaMessageBuilder {
    public MessageBuilder(String message) {
        super(message);
    }

    public MessageBuilder(List<String> messages) {
        super(messages);
    }

    public MessageBuilder setPrefix() {
        this.replace("%prefix%", Message.PREFIX.getMessage());
        return this;
    }

    public MessageBuilder setSeconds(int seconds) {
        this.replace("%seconds%", seconds +"");
        return this;
    }

    public MessageBuilder setStartDate(String startDate) {
        this.replace("%start_date%", startDate);
        return this;
    }

    public MessageBuilder setRemaining(String remaining) {
        this.replace("%remaining_time%", remaining);
        return this;
    }

    public MessageBuilder setEndDate(String endDate) {
        this.replace("%end_date%", endDate);
        return this;
    }

    public MessageBuilder setTitle(String title) {
        this.replace("%title%", title);
        return this;
    }

    public MessageBuilder setTime(String time) {
        this.replace("%time%", time);
        return this;
    }
}
