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

import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class FlyCommand implements TabExecutor {

    private final TechJet plugin;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player player)) {
            sender.sendMessage(plugin.getMessage("console-cant-execute"));
            return false;
        }

        Player target = player;
        if(args.length >= 1) {
            if(!sender.hasPermission(plugin.getPermission("fly-others"))) {
                sender.sendMessage(plugin.getMessage("no-permission"));
                return false;
            }
            target = Bukkit.getPlayer(args[0]);
            if(target == null) {
                player.sendMessage(plugin.getMessage("player-not-found"));
                return false;
            }
        }

        if(!target.getAllowFlight()) {
            // On
            target.setAllowFlight(true);
            target.sendMessage(Utils.formatMessage(plugin.getMessage("fly-enabled"), target));
        } else {
            // Off
            target.setAllowFlight(false);
            target.sendMessage(Utils.formatMessage(plugin.getMessage("fly-disabled"), target));

        }

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
