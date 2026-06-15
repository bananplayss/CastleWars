package me.bananplayss.castlewars.core.kobalib;

import lombok.Getter;
import lombok.Setter;
import me.bananplayss.castlewars.core.kobalib.colors.ColorParser;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.util.Ticks;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class KobaMessageBuilder {

    private String message;
    private List<String> messages;

    public KobaMessageBuilder(String message) {
        this.message = message;
    }

    public KobaMessageBuilder(List<String> messages) {
        this.messages = messages;
    }

    public List<String> getMessages() {
        if (this.messages.isEmpty() && this.message != null) {
            return List.of(this.message);
        }
        return this.messages;
    }

    public KobaMessageBuilder replace(String target, String replacement) {
        if (this.message != null) {
            this.message = this.message.replace(target, replacement);
            return this;
        }
        this.messages = replaceList(target, replacement);
        return this;
    }

    private List<String> replaceList(String target, String replacement) {
        List<String> str = new ArrayList<>();
        for (String s : this.messages) {
            str.add(s.replace(target, replacement));
        }
        return str;
    }

    public KobaMessageBuilder setPlayer(Player player) {
        this.replace("%player%", player.getName());
        return this;
    }

    public KobaMessageBuilder setError(String error) {
        this.replace("%error%", error);
        return this;
    }

    public KobaMessageBuilder setTarget(Player target) {
        this.replace("%target%", target.getName());
        return this;
    }

    public KobaMessageBuilder setUsage(String usage) {
        this.replace("%usage%", usage);
        return this;
    }

    public KobaMessageBuilder setSeconds(int seconds) {
        this.replace("%seconds%", seconds + "");
        return this;
    }

    public void broadcast() {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            send(onlinePlayer);
        }
    }

    public void send(Player player) {
        if (this.message != null) {
            player.sendMessage(ColorParser.parse(this.message, player));
        } else {
            for (String s : this.messages) {
                player.sendMessage(ColorParser.parse(s, player));
            }
        }
    }

    public void send(List<Player> players) {
        players.forEach(this::send);
    }

    public void send(CommandSender sender) {
        if (sender instanceof Player p) {
            this.send(p);
            return;
        }

        if (this.message != null) {
            sender.sendMessage(ColorParser.parse(this.message));
        } else {
            for (String s : this.messages) {
                sender.sendMessage(ColorParser.parse(s));
            }
        }
    }

    public void sendTitle(Player player, KobaMessage subtitle, int duration) {
        if (player == null) return;
        if (this.message != null && subtitle.message != null) {
            Title title = Title.title(
                    ColorParser.parse(this.message, player),
                    ColorParser.parse(subtitle.builder().message, player),
                    Title.Times.times(Ticks.duration(10), Ticks.duration(duration - 30), Ticks.duration(20))
            );
            player.showTitle(title);
        }
    }

    public void sendActionBar(Player player) {
        if (player == null) return;
        if (this.message != null) {
            player.sendActionBar(ColorParser.parse(this.message, player));
        } else {
            for (String s : this.messages) {
                player.sendActionBar(ColorParser.parse(s, player));
            }
        }
    }
}
