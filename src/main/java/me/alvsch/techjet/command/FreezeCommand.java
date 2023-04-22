package me.alvsch.techjet.command;

import lombok.RequiredArgsConstructor;
import me.alvsch.techjet.TechJet;
import me.alvsch.techjet.TechJetRegistry;
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
import java.util.UUID;

@RequiredArgsConstructor
public class FreezeCommand implements TabExecutor {

    private final TechJet plugin;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length < 1) {
            return false;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if(target == null) {
            sender.sendMessage(plugin.getMessage("player-not-found"));
            return false;
        }
        if(target.hasPermission(plugin.getPermission("freeze-bypass"))) {
            sender.sendMessage(plugin.getMessage("target-bypass"));
            return false;
        }

        UUID uuid = target.getUniqueId();
        if(!plugin.getRegistry().getFreeze().contains(uuid)) {
            // Freeze
            plugin.getRegistry().getFreeze().add(uuid);
            sender.sendMessage(Utils.formatMessage(plugin.getMessage("freeze-freeze-other"), target));
            target.sendMessage(Utils.formatMessage(plugin.getMessage("freeze-freeze-you"), sender));
        } else {
            // Unfreeze
            plugin.getRegistry().getFreeze().remove(uuid);
            sender.sendMessage(Utils.formatMessage(plugin.getMessage("freeze-unfreeze-other"), target));
            target.sendMessage(Utils.formatMessage(plugin.getMessage("freeze-unfreeze-you"), sender));
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
