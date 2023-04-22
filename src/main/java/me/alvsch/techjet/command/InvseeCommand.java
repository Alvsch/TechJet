package me.alvsch.techjet.command;

import lombok.RequiredArgsConstructor;
import me.alvsch.techjet.TechJet;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class InvseeCommand implements TabExecutor {

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
        Player target = Bukkit.getPlayer(args[0]);
        if(target == null) {
            player.sendMessage(plugin.getMessage("player-not-found"));
            return false;
        }

        player.openInventory(target.getInventory());

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
