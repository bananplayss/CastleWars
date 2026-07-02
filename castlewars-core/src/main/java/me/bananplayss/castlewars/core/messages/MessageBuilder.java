package me.bananplayss.castlewars.core.messages;

import me.bananplayss.castlewars.api.teams.game.AbstractGameTeam;
import me.bananplayss.castlewars.api.utils.ColorNormalizer;
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
        this.replace("%remaining%", remaining);
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

    public MessageBuilder setCurrentScore(int currentScore) {
        this.replace("%score%", String.valueOf(currentScore));
        return this;
    }
    public MessageBuilder setMaxScore(int maxScore) {
        this.replace("%max_score%", String.valueOf(maxScore));
        return this;
    }

    public MessageBuilder setTeam(AbstractGameTeam team) {
        this.replace("%team%", team.getTeam().getDisplayName());
        this.replace("%team_display_name%", team.getTeam().getDisplayName());
        this.replace("%team_display_name_stripped%", ColorNormalizer.strip(team.getTeam().getDisplayName()));
        this.replace("%team_name%", team.getTeam().getKey());
        this.replace("%team_prefix%", team.getTeam().getPrefix());
        this.replace("%team_color%", team.getTeam().getColor());
        return this;
    }
}
