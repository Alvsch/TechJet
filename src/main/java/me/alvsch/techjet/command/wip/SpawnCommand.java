package me.alvsch.techjet.command.wip;

import lombok.RequiredArgsConstructor;
import me.alvsch.techjet.TechJet;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@RequiredArgsConstructor
public class SpawnCommand implements TabExecutor {

    private final TechJet plugin;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player player)) {
            sender.sendMessage(plugin.getMessage("console-cant-execute"));
            return false;
        }

        Location spawn = plugin.getConfig().getLocation("spawn");
        if(spawn == null) {
            player.sendMessage(plugin.getMessage("spawn-not-set"));
            return false;
        }

        player.teleport(spawn);
        player.sendMessage(plugin.getMessage("spawn"));
        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return null;
    }

}
