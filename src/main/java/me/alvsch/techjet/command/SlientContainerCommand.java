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

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class SlientContainerCommand implements TabExecutor {

    private final TechJet plugin;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player player)) {
            sender.sendMessage(plugin.getMessage("console-cant-execute"));
            return false;
        }

        UUID uuid = player.getUniqueId();
        if(!plugin.getRegistry().getSilentContainer().contains(uuid)) {
            // Turn on silent container
            player.sendMessage(plugin.getMessage("silentcontainer-on"));
            plugin.getRegistry().getSilentContainer().add(uuid);
        } else {
            // Turn off silent container
            player.sendMessage(plugin.getMessage("silentcontainer-off"));
            plugin.getRegistry().getSilentContainer().remove(uuid);
        }

        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return null;
    }

}
