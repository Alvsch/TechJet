package me.alvsch.techjet.command;

import com.google.common.base.Ticker;
import lombok.RequiredArgsConstructor;
import me.alvsch.techjet.TechJet;
import me.alvsch.techjet.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class PlaytimeCommand implements TabExecutor {

    private final TechJet plugin;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player player)) {
            sender.sendMessage(plugin.getMessage("console-cant-execute"));
            return false;
        }

        Player target = player;
        if(args.length >= 1) {
            if(!sender.hasPermission(plugin.getPermission("playtime-others"))) {
                sender.sendMessage(plugin.getMessage("no-permission"));
                return false;
            }
            target = Bukkit.getPlayer(args[0]);
            if(target == null) {
                player.sendMessage(plugin.getMessage("player-not-found"));
                return false;
            }
        }

        // Ticks
        long playtime = target.getStatistic(Statistic.PLAY_ONE_MINUTE);
        Duration duration = Duration.ofSeconds(playtime / 20);
        String playtimeFormat = duration.toHours() + "h " + duration.toMinutesPart() + "m " + duration.toSecondsPart() + "s";

        player.sendMessage(Utils.formatMessage(plugin.getMessage("playtime"), target).replaceAll("\\{playtime}", playtimeFormat));
        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        ArrayList<String> list = new ArrayList<>();

        if(args.length == 1) {
            list.addAll(Bukkit.getOnlinePlayers().stream().map(Player::getName)
                    .filter(entry -> entry.startsWith(args[0]))
                    .toList());
        }

        return list;
    }

}
