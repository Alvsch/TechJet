package me.alvsch.techjet.command;

import lombok.RequiredArgsConstructor;
import me.alvsch.techjet.TechJet;
import me.alvsch.techjet.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class FlySpeedCommand implements TabExecutor {

    private final TechJet plugin;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player player)) {
            sender.sendMessage(plugin.getMessage("console-cant-execute"));
            return false;
        }
        if(args.length < 1) {
            return false;
        }
        float max = plugin.getConfig().getInt("max-flyspeed");
        if(Double.isNaN(Double.parseDouble(args[0]))) {
            return false;
        }
        float speed = Float.parseFloat(args[0]);

        Player target = player;
        if(args.length >= 2) {
            if(!sender.hasPermission(plugin.getPermission("flyspeed-others"))) {
                sender.sendMessage(plugin.getMessage("no-permission"));
                return false;
            }
            target = Bukkit.getPlayer(args[1]);
            if(target == null) {
                player.sendMessage(plugin.getMessage("player-not-found"));
                return false;
            }
        }

        speed = Math.max(0, speed);
        speed = Math.min(max, speed);

        target.setFlySpeed(speed / 10);
        target.sendMessage(Utils.formatMessage(plugin.getMessage("flyspeed"), target).replace("{speed}", String.valueOf(speed)));
        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        ArrayList<String> list = new ArrayList<>();

        if(args.length == 2) {
            list.addAll(Bukkit.getOnlinePlayers().stream().map(Player::getName)
                    .filter(entry -> entry.startsWith(args[1]))
                    .toList());
        }

        return list;
    }

}
